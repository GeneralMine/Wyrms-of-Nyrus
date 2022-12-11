package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.creepTheLands.creepTheLands;
import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityCreepwyrm extends EntityWyrm implements IAnimatable, IAnimationTickable {

    public static final ResourceLocation CREEPWYRM_LOOT_TABLE = new ResourceLocation("wyrmsofnyrus", "entities/creepwyrm");

    private AnimationFactory factory = new AnimationFactory(this);
    private int timeUntilNextCreep;
    public EntityCreepwyrm(World world) {
        super(world);
        this.casteType = 0;
        setSize(1f, 5f);
        experienceValue = 30;
        enablePersistence();
        setNoAI(false);
        this.timeUntilNextCreep = Invasion.normCreepwyrmCreepSpeed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // Broken at the moment, will fix soon. See #22
        /*createWaypoint((new EntityCreepwyrmWaypoint(this.world)),
                (int) ((getPosition().getX()) + RNG.PMRange(10)),
                (int) ((getPosition().getY()) + RNG.PMRange(10)),
                (int) ((getPosition().getZ()) + RNG.PMRange(10)) );
        */
        if (source == DamageSource.FALL)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
            return super.attackEntityFrom(source, (amount/2));
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void initEntityAI() {
        afterPlayers();
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5D, false));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (evoPoints.getLevel() >= Evo.minEvoCreepwyrm)){
            this.setStatsEvo((wyrmStats.creepwyrmHP),(wyrmStats.creepwyrmDEF),(wyrmStats.creepwyrmATK), 0.0F,1.0F, Evo.minEvoCreepwyrm);
        }
        else {
            this.setStats(wyrmStats.creepwyrmHP,wyrmStats.creepwyrmDEF,wyrmStats.creepwyrmATK, 0.0F,1.0F);
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return CREEPWYRM_LOOT_TABLE;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        /*int x = (int) this.posX;
        int y = (int) this.posY;
        int z = (int) this.posZ;
        Entity entity = this;
        {
            Map<String, Object> $_d = new HashMap<>();
            $_d.put("entity", entity);
            $_d.put("x", x);
            $_d.put("y", y);
            $_d.put("z", z);
            $_d.put("world", world);
            staysStill($_d);
        }*/

        if (!this.world.isRemote && --this.timeUntilNextCreep <= 0)
        {
            creepTheLands(getPosition(),this.world, Radiogenetics.creepwyrmInfestRange);
            this.timeUntilNextCreep = Invasion.normCreepwyrmCreepSpeed;
        }
    }

    @Override
    public boolean canBePushed()
    {
        super.canBePushed();
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.creepSpread;}

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,21);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepwyrm.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

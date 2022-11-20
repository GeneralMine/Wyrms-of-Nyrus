package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.wyrmArmorFragment;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.vetpetmon.wyrmsofnyrus.config.Invasion.isEXCANON;
import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityWyrmSoldierInfectoid extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityWyrmSoldierInfectoid(World world) {
        super(world);
        this.casteType = 3;
        setSize(2.5f, 1.5f);
        experienceValue = 12;
        enablePersistence();
        setNoAI(false);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        //this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
        afterPlayers();
        hivemindFollow();
        if (getAttackVillagers()) afterVillagers();
        if (isEXCANON()) {afterMobs();afterAnimals();}
    }

    @Override
    protected void applyEntityAttributes() {
        float difficulty = (float) (getInvasionDifficulty() + evoPoints.evoMilestone(world));
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(wyrmStats.infectoidSoldierDEF,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.52D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(wyrmStats.infectoidSoldierHP,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(wyrmStats.infectoidSoldierATK,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(wyrmArmorFragment.block, 3).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.deepwyrmclicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds)  {
        return SoundRegistry.wyrmHissTwo;
    }
    /*@Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }*/

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.slowwyrmsteps, 1.0F, 1.0F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isMagicDamage()) amount *= 0.5F;
        if (source == DamageSource.FALL && Radiogenetics.immuneToFalling)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,4);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.soldierwyrm.moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.soldierwyrm.idle"));
        }
        return PlayState.CONTINUE;
    }
    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

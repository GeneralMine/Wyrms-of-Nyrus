package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.CreepedEvents;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmKillBonuses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.nbt.NBTTagCompound;
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

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityCreepling extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);
    public static final ResourceLocation BITER_LOOT_TABLE = new ResourceLocation("wyrmsofnyrus", "entities/biter");
    private int timer;
    public EntityCreepling(World worldIn) {
        super(worldIn);
        this.casteType = 0;
        setSize(0.4f, 0.6f);
        experienceValue = 5;
        enablePersistence();
        setNoAI(false);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,1);
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        wyrmKillBonuses.pointIncrease(world);
        CreepedEvents.convertKill(entity,this);
    }
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.timer <= 0 && isGrounded()){
            CreepedEvents.creepBlockBelow(this);
            this.setDead();
        }
    }


    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return BITER_LOOT_TABLE;
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.biter;}

    @Override
    protected void initEntityAI() {
        afterPlayers();
        afterAnimals();
        afterVillagers();
        afterMobs();
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.95D, false));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.75D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(wyrmStats.creeplingHP,wyrmStats.creeplingDEF,wyrmStats.creeplingATK, wyrmStats.creeplingSPD,wyrmStats.creeplingKBR);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
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
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("timer")) this.timer = compound.getInteger("timer");
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("timer", this.timer);
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepling.move"));
        }
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepling.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

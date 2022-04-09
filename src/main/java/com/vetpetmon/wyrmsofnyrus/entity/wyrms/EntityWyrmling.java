package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityWyrmling extends EntityMob implements IAnimatable{
    private AnimationFactory factory = new AnimationFactory(this);
    public EntityWyrmling(World world) {
        super(world);
        setSize(0.5f, 0.5f);
        experienceValue = 1;
        this.isImmuneToFire = true;
        setNoAI(false);
        enablePersistence();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityPlayerMP.class, (float) 3, 1, 1.2));
        this.tasks.addTask(2, new EntityAIFollow(this, (float) 1, 10, 5));
        this.tasks.addTask(3, new EntityAIWander(this, 0.25));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayerMP.class, (float) 12));
        this.tasks.addTask(5, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.2));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.1D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0D);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.wyrmclicks3"));
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("wyrmsofnyrus:hiss2"));
    }
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20F, (AnimationController.IAnimationPredicate) this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmlingmodel.move"));}
        else {event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmlingmodel.idle"));}

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}

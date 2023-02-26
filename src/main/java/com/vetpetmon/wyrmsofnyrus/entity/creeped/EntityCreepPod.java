package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.DroppodContents;
import net.minecraft.entity.SharedMonsterAttributes;
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

public class EntityCreepPod extends EntityWyrm implements IAnimatable{
    private AnimationFactory factory = new AnimationFactory(this);
    public EntityCreepPod(World world) {
        super(world);
        this.casteType = 9;
        setSize(1f, 1f);
        experienceValue = 0;
        enablePersistence();
        setNoAI(false);
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.005D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1D);
    }
    @Override
    protected boolean canEnrage(){return true;}
    @Override
    public SoundEvent getAmbientSound(){return null;}

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }

    @Override
    public void onDeath(DamageSource source) {
        DroppodContents.DropPodEventSequence(this.getPosition(),4,3,this.world);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20F, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (getHealth() < 1) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeppod.open"));
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeppod.idle"));
        return PlayState.CONTINUE;
    }
}

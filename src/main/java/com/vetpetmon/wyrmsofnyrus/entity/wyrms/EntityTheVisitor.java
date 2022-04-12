package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityTheVisitor extends EntityMob implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityTheVisitor(World world) {
        super(world);
        setSize(0.5f, 0.5f);
        experienceValue = 0;
        this.isImmuneToFire = false;
        setNoAI(false);
        this.setNoGravity(true);
    }
    
    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        // No AI
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0D);
        }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
    }

    /*@Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        IEntityLivingData retval = super.onInitialSpawn(difficulty, livingdata);
        Entity entity = this;
        {
            Map<String, Object> $_dependencies = new HashMap<>();
            $_dependencies.put("entity", entity);
            EntityDeathTimer.executeProcedure($_dependencies);
        }
        return retval;
    }*/

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return null;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {return null;}
    @Override
    public SoundEvent getDeathSound() {return null;}

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)    //Basically creates immunity from SRP's COTH effects and poison/wither, as wyrms are NOT organic beings
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20F, (AnimationController.IAnimationPredicate) this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.NyrusVisitor.idle"));

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}

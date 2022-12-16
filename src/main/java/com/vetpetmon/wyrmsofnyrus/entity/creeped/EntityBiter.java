package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ai.RollAttackAI;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.util.DamageSource;
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

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityBiter extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);


    public EntityBiter(World worldIn) {
        super(worldIn);
        this.casteType = 0;
        setSize(0.75f, 0.85f);
        experienceValue = 5;
        enablePersistence();
        setNoAI(false);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,21);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.biter;}

    @Override
    protected void initEntityAI() {
        afterPlayers();
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.75D));
        this.tasks.addTask(1, new RollAttackAI(this, 1.0, true, wyrmStats.biterRollSPD, wyrmStats.biterRollDMG, SoundRegistry.bitercharge));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(wyrmStats.biterHP,wyrmStats.biterDEF,wyrmStats.biterATK, wyrmStats.biterSPD,wyrmStats.biterKBR);
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (getAttack() == 3) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.biterwyrm.roll"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.biterwyrm.move"));
        }
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.biterwyrm.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

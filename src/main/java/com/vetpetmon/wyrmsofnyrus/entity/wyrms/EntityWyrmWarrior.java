package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Client;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrmFlying;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.synapselib.ai.EntityAIFlierMob;
import com.vetpetmon.wyrmsofnyrus.synapselib.ai.EntityAIFlierMoveRandom;
import com.vetpetmon.wyrmsofnyrus.synapselib.ai.moveHelpers.flierMoveHelperGhastlike;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateFlying;
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

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;


public class EntityWyrmWarrior extends EntityWyrmFlying implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);
    //private boolean isCharging;
    private String animationName = "animation.warriorwyrm";
    public EntityWyrmWarrior(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.9f, 2.0f);
        experienceValue = 5;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new flierMoveHelperGhastlike(this, 30, 1.0, 0.8);
        //this.moveHelper = new EntityWyrmWarrior.WyrmWarriorMoveHelper(this);
        enablePersistence();
        setNoAI(false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (evoPoints.getLevel() >= Evo.minEvoWarrior)) this.setStatsEvo(wyrmStats.warriorHP,wyrmStats.warriorDEF,wyrmStats.warriorATK,wyrmStats.warriorSPD,wyrmStats.warriorKBR, Evo.minEvoWarrior);
        else this.setStats(wyrmStats.warriorHP,wyrmStats.warriorDEF,wyrmStats.warriorATK,wyrmStats.warriorSPD,wyrmStats.warriorKBR);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.05D, false));
        this.tasks.addTask(4, new EntityAIFlierMob(this, 3.05, 200));
        this.tasks.addTask(8, new EntityAIFlierMoveRandom(this));
        //this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.afterPlayers();
        this.afterVillagers();
        this.afterAnimals();
        this.afterMobs();
        hivemindFollow();
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,7);
    }
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(AllItems.wyrmarmorfrag, 2).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmroars;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 3F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (isInWater() && Client.fancyAnimations) event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName + ".swim"));
            else if (isGrounded()) event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName + ".groundedRun"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation((animationName + ".moving")));
        }
        else {
            if (isInWater() && Client.fancyAnimations) event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName + ".inWater"));
            else if (isGrounded()) event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName + ".groundedIdle"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName + ".idle"));
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

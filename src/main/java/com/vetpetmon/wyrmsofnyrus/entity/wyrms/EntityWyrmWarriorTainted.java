package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.hbm.interfaces.IRadiationImmune;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrmFlying;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.wyrmArmorFragment;
import com.vetpetmon.wyrmsofnyrus.synapselib.ai.*;
import com.vetpetmon.wyrmsofnyrus.synapselib.ai.moveHelpers.flierMoveHelperGhastlike;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
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


public class EntityWyrmWarriorTainted extends EntityWyrmFlying implements IAnimatable, IAnimationTickable, IRadiationImmune {
    private final AnimationFactory factory = new AnimationFactory(this);
    //private boolean isCharging;
    public EntityWyrmWarriorTainted(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.9f, 2.0f);
        experienceValue = 30;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new flierMoveHelperGhastlike(this, 20, 1.0, 0.8);
        enablePersistence();
        setNoAI(false);
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        float difficulty = (float) (getInvasionDifficulty() * evoPoints.evoMilestone(world));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(wyrmStats.taintedWarriorATK,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(wyrmStats.taintedWarriorDEF,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(wyrmStats.taintedWarriorHP,difficulty));
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.05D, false));
        this.tasks.addTask(4, new EntityAIFlierMob(this, 3.05, 200));
        this.tasks.addTask(8, new EntityAIFlierMoveRandom(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.afterPlayers(true);
        this.afterVillagers();
        this.afterAnimals();
        this.afterMobs();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean result = super.attackEntityAsMob(entityIn);
        if (result) {
            difficultyStats.applyPotionEffect(entityIn, HbmPotion.taint, 60, 1);
        }
        return result;
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
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(wyrmArmorFragment.block, 4).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmroars;
    }
    @Override
    protected SoundEvent getFallSound(int heightIn) {return null;}
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isMagicDamage()) amount = 0.0F;
        if (source.isProjectile()) amount *= 0.5F;
        if (source.isExplosion()) amount *= 0.5F;
        if (source == ModDamageSource.radiation || source == ModDamageSource.mudPoisoning) {
            this.heal(amount * 1.25F);
            return false;
        }
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        return super.attackEntityFrom(source, amount);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (isGrounded()) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.warriorwyrm.groundedRun"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.warriorwyrm.moving"));
        }
        else {
            if (isGrounded()) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.warriorwyrm.groundedIdle"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.warriorwyrm.idle"));
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

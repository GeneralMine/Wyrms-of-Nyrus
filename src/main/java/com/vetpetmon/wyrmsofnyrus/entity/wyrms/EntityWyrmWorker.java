package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.ItemMetalcombArray;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import com.vetpetmon.wyrmsofnyrus.synapselib.rangeCheck;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityWyrmWorker extends EntityWyrm {
    public int timeUntilNextProduct;
    public boolean unionizing;

    public EntityWyrmWorker(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.85f, 1.5f);
        experienceValue = 1;
        enablePersistence();
        setNoAI(false);
        this.setCanPickUpLoot(true);
        this.timeUntilNextProduct = (this.rand.nextInt(6000) + (Radiogenetics.workerProductivity));
        this.unionizing = false;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        if (((getInvasionDifficulty() >= 3.0 && AI.savageAIMode) || (this.unionizing))
                || (Evo.evoEnabled && evoPoints.get(world) >= 150)) {
            afterPlayers();
            this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
            simpleAI();
        }
        else {
            simpleAI();
            this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
        }
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(1, new EntityAISwimming(this));
    }

    @Override
    protected void applyEntityAttributes() {
        float difficulty = (float) (getInvasionDifficulty() * evoPoints.evoMilestone(world));
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(2.0d,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1D);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
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
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmClicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundRegistry.wyrmHissTwo;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.wyrmSteps, 1.0F, 1.0F);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,2);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.timeUntilNextProduct <= 0)
        {
            // Check if there's a hopper in range. If there is at least one in range, go to the "else" condition and just make funny noise instead.
            if (!rangeCheck.blocks(world,getPosition(),4,"minecraft:hopper")) {
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 0.25F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.dropItem(ItemMetalcombArray.block, 1);
            }
            else {
                this.playSound(SoundRegistry.wyrmHissTwo, 1.0F, 0.25F);
                this.unionizing = true;
            } // They're unionizing. (just means they turn hostile, regardless of Savage AI settings)
            this.timeUntilNextProduct = (this.rand.nextInt(6000) + (Radiogenetics.workerProductivity));
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("ProductionTime"))
        {
            this.timeUntilNextProduct = compound.getInteger("ProductionTime");
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("ProductionTime", this.timeUntilNextProduct);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1F, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.moving"));
            return PlayState.CONTINUE;
        }
        else {
            if (getInvasionDifficulty() >= 3.0 && AI.savageAIMode){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.idleAwakened"));
            }
            else{event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.idle"));}
        }

        return PlayState.CONTINUE;
    }

}

package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.item.ItemMetalcombArray;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
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

public class EntityWyrmWorker extends EntityWyrm {
    public int timeUntilNextProduct;

    public EntityWyrmWorker(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.85f, 1.5f);
        experienceValue = 1;
        enablePersistence();
        setNoAI(false);
        this.timeUntilNextProduct = (int) (this.rand.nextInt(6000) + (2000 / (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty)));
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        if ((wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty) >= 3.0){
            afterPlayers();
            this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
            this.tasks.addTask(2, new EntityAIWander(this, 1.0));
            this.tasks.addTask(3, new EntityAILookIdle(this));
            this.tasks.addTask(1, new EntityAISwimming(this));
        }
        else {
            this.tasks.addTask(2, new EntityAIWander(this, 1.0));
            this.tasks.addTask(3, new EntityAILookIdle(this));
            this.tasks.addTask(1, new EntityAISwimming(this));
            this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((2.0D) * (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1D);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
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
    /*@Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }*/

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.wyrmSteps, 1.0F, 1.0F);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.timeUntilNextProduct <= 0)
        {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 0.25F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ItemMetalcombArray.block, 1);
            this.timeUntilNextProduct = (int) (this.rand.nextInt(6000) + (2000 / (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty)));
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
            if ((wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty) >= 3.0){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.idleAwakened"));
            }
            else{event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.idle"));}
        }

        return PlayState.CONTINUE;
    }

}

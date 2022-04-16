package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.item.ItemMetalcombArray;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class EntityWyrmWorker extends EntityWyrm {
    public int timeUntilNextProduct;

    public EntityWyrmWorker(World world) {
        super(world);
        this.casteType = 2;
        setSize(1.0f, 1.5f);
        experienceValue = 1;
        this.timeUntilNextProduct = this.rand.nextInt(6000) + 2000;
        setNoAI(false);
        enablePersistence();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityAIFollow(this, (float) 1, 10, 5));
        this.tasks.addTask(2, new EntityAIWander(this, 0.25));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayerMP.class, (float) 12));
        this.tasks.addTask(5, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.2));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0D);
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

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.timeUntilNextProduct <= 0)
        {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 0.25F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ItemMetalcombArray.block, 1);
            this.timeUntilNextProduct = this.rand.nextInt(6000) + 2000;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("ProductionTime"))
        {
            this.timeUntilNextProduct = compound.getInteger("ProductionTime");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("EggLayTime", this.timeUntilNextProduct);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.moving"));
            return PlayState.CONTINUE;
        }
        else {event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmworker.idle"));}

        return PlayState.CONTINUE;
    }

}

package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.item.ItemMetalcombArray;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.WyrmlingGrowUp.growUp;

public class EntityWyrmling extends EntityWyrm {
    private int timeUntilGrowth;

    public EntityWyrmling(World world) {
        super(world);
        this.casteType = 0;
        setSize(0.5f, 0.5f);
        experienceValue = 1;
        this.isImmuneToFire = false;
        enablePersistence();
        setNoAI(false);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityPlayerMP.class, (float) 3, 1, 1.2));
        this.tasks.addTask(2, new EntityAIFollow(this, (float) 1, 10, 5));
        this.tasks.addTask(3, new EntityAIWander(this, 0.8));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayerMP.class, (float) 12));
        this.tasks.addTask(5, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5));
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
        if (!this.world.isRemote && --this.timeUntilGrowth <= 0)
        {
            World world = this.world;
            int i = (int) this.posX;
            int j = (int) this.posY;
            int k = (int) this.posZ;
            java.util.HashMap<String, Object> d = new java.util.HashMap<>();
            d.put("x", i);
            d.put("y", j);
            d.put("z", k);
            d.put("world", world);
            growUp(d, this);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        this.srpcothimmunity = true;

        if (compound.hasKey("ProductionTime"))
        {
            this.timeUntilGrowth = compound.getInteger("GrowthTime");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setBoolean("srpcothimmunity", this.srpcothimmunity);
        compound.setInteger("GrowthTime", this.timeUntilGrowth);
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmlingmodel.move"));
            return PlayState.CONTINUE;
        }
        else {event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmlingmodel.idle"));}

        return PlayState.CONTINUE;
    }



    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        return super.attackEntityFrom(source, amount);
    }

}

package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
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
import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityWyrmling extends EntityWyrm {
    private int timeUntilGrowth;
    private boolean hasGrown;

    public EntityWyrmling(World world) {
        super(world);
        this.casteType = 0;
        this.hasGrown = false;
        setSize(0.5f, 0.5f);
        experienceValue = 1;
        this.isImmuneToFire = false;
        enablePersistence();
        setNoAI(false);
        this.timeUntilGrowth = this.rand.nextInt(6000) + 2000;
    }

    @Override
    protected void initEntityAI() {
        hivemindAvoid();
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 3, 1, 1.2));
        this.tasks.addTask(2, new EntityAIFollow(this, (float) 1, 10, 5));
        this.tasks.addTask(3, new EntityAIWander(this, 0.8));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 12));
        this.tasks.addTask(5, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5));
        simpleAI();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (evoPoints.getLevel() >= Evo.minEvoWyrmling)) this.setStatsEvo(wyrmStats.wyrmlingHP,wyrmStats.wyrmlingDEF,0F,wyrmStats.wyrmlingSPD,0F, Evo.minEvoWyrmling);
        else this.setStats(wyrmStats.wyrmlingHP,wyrmStats.wyrmlingDEF,0F,wyrmStats.wyrmlingSPD,0F);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmlingclicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundRegistry.wyrmHissTwo;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.timeUntilGrowth <= 0 && !hasGrown)
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
            this.hasGrown = true;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("GrowthTime"))
        {
            this.timeUntilGrowth = compound.getInteger("GrowthTime");
        }

        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        this.srpcothimmunity = 0;

        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
        compound.setInteger("GrowthTime", this.timeUntilGrowth);
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmling.moving"));
            return PlayState.CONTINUE;
        }
        else {event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmling.idle"));}

        return PlayState.CONTINUE;
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,1);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL && Radiogenetics.immuneToFalling)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        return super.attackEntityFrom(source, amount);
    }

}

package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.evo.EvoPoints;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.WyrmlingGrowUp.growUp;

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
        setPotency(1.5);
        this.setAnimationNames(new String[]{"wyrmling.idle","wyrmling.moving"});
        this.timeUntilGrowth = this.rand.nextInt(6000) + 2000;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 3, 1, 1.2));
        this.tasks.addTask(2, new EntityAIFollow(this, (float) 1, 10, 5));
        this.tasks.addTask(3, new EntityAIWander(this, 0.8));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 12));
        this.tasks.addTask(5, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.5));
        simpleAI();
    }
    @Override
    protected boolean canEnrage(){return false;}
    @Override
    protected boolean partakesInGestalt(){return false;}
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (EvoPoints.getLevel() >= Evo.minEvoWyrmling)) this.setStatsEvo(WyrmStats.wyrmlingHP, WyrmStats.wyrmlingDEF,0F, WyrmStats.wyrmlingSPD,0F, Evo.minEvoWyrmling);
        else this.setStats(WyrmStats.wyrmlingHP, WyrmStats.wyrmlingDEF,0F, WyrmStats.wyrmlingSPD,0F);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmlingclicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundRegistry.wyrmlinghurt;
    }
    @Override
    public SoundEvent getDeathSound() {
        return SoundRegistry.wyrmlingdeath;
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
            event.getController().setAnimation(getAnimation(1));
            return PlayState.CONTINUE;
        }
        else {event.getController().setAnimation(getAnimation(0));}

        return PlayState.CONTINUE;
    }
}

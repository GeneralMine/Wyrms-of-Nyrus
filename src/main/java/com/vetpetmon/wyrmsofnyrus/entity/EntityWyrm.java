package com.vetpetmon.wyrmsofnyrus.entity;

import com.google.common.base.Predicate;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.concurrent.ThreadLocalRandom;

public abstract class EntityWyrm extends EntityMob implements IAnimatable {
    public static EntityMoveHelper moveHelper;

    //  LIST OF CASTE TYPES:
    // 0 - Other
    // 1 - Ignoble (workers)
    // 2 - Scouters
    // 3 - Warriors
    // 4 - Archives
    // 5 - Royal
    // 6 - Imperial
    // 7 - Hexe pods (no AI but gravity)
    // 8 - Visitor (No AI + vanishes)
    // 9 - Event (vanishes)

    public int casteType;
    private final AnimationFactory factory = new AnimationFactory(this);
    public ThreadLocalRandom rand;
    protected int srpcothimmunity;
    public double difficulty;
    protected static final DataParameter<Byte> WYRM_FLAGS = EntityDataManager.createKey(EntityWyrm.class, DataSerializers.BYTE);

    public EntityWyrm(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
        this.dataManager.register(WYRM_FLAGS, (byte) 0);
    }

    protected boolean canDespawn() {return false;}

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(WYRM_FLAGS, (byte) 0);
    }

    protected static boolean getSimpleAI() {return AI.performanceAIMode;}
    protected static boolean getAttackMobs() {return AI.attackMobs;}
    protected static boolean getAttackAnimals() {return AI.attackAnimals;}
    protected static boolean getWillAttackCrepers() {return AI.suicidalWyrms;}

    protected void simpleAI() {
        if (!getSimpleAI()) this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    protected void makeAllTargets(boolean notSeeThru) {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, notSeeThru, false));
        if(getAttackAnimals()){this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, true, false));}
        if(getAttackMobs()) {
            this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, true, false, new Predicate<EntityMob>() {
                public boolean apply(EntityMob target) {
                    if (getWillAttackCrepers()) return !(target instanceof EntityWyrm);
                    else return !((target instanceof EntityCreeper) || (target instanceof EntityWyrm));
                }
            }));
        }
    }
    protected void afterPlayers() {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));

        if(Radiogenetics.seeThruWalls) this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));
        else this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        switch(casteType) {
            case 1:
            case 7:
            case 8:
                return false;
            default:
                return true;
        }
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {

        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
    }

    private boolean getWyrmFlag(int mask)
    {
        int i = this.dataManager.get(WYRM_FLAGS);
        return (i & mask) != 0;
    }

    private void setWyrmFlag(int mask, boolean value)
    {
        int i = this.dataManager.get(WYRM_FLAGS);

        if (value)
        {
            i = i | mask;
        }
        else
        {
            i = i & ~mask;
        }

        this.dataManager.set(WYRM_FLAGS, (byte) (i & 255));
    }

    public boolean isCharging()
    {
        return this.getWyrmFlag(1);
    }

    public void setCharging(boolean charging)
    {
        this.setWyrmFlag(1, charging);
    }

    /*public void setCharging(boolean b) {
        this.dataManager.register(WYRM_FLAGS, Byte.valueOf((byte)0));
    }

    public boolean isCharging() {
        int i = this.dataManager.get(WYRM_FLAGS).byteValue();
        return (i) != 0;
    }*/
}

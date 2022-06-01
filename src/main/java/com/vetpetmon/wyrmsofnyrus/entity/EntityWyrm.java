package com.vetpetmon.wyrmsofnyrus.entity;

import com.google.common.base.Predicate;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class EntityWyrm extends EntityMob implements IAnimatable {

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
    protected int srpcothimmunity;

    public EntityWyrm(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
    }

    protected boolean canDespawn() {return false;}

    protected static boolean getSimpleAI() {return AI.performanceAIMode;}
    protected static boolean getAttackMobs() {return AI.attackMobs;}
    protected static boolean getAttackAnimals() {return AI.attackAnimals;}
    protected static boolean getWillAttackCreepers() {return AI.suicidalWyrms;}
    public double getInvasionDifficulty() {return wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty;}

    protected void simpleAI() {
        if (!getSimpleAI()) this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    protected void makeAllTargets() {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));
        if(getAttackAnimals()){this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, false, false));}
        if(getAttackMobs()) {
            this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, false, false, new Predicate<EntityMob>() {
                public boolean apply(EntityMob target) {
                    if (getWillAttackCreepers()) return !(target instanceof EntityWyrm);
                    else return !((target instanceof EntityCreeper) || (target instanceof EntityWyrm));
                }
            }));
        }
    }
    protected void afterPlayers() {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));
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
}

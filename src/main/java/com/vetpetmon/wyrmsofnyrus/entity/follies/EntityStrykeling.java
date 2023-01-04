package com.vetpetmon.wyrmsofnyrus.entity.follies;

import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;

public class EntityStrykeling extends EntityWyrmfolly implements IAnimatable, IAnimationTickable {
    protected int age; // currently unused
    public EntityStrykeling(World worldIn) {
        super(worldIn);
        this.age = 0;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(wyrmStats.strykelingfollyHP,wyrmStats.strykelingfollyDEF,wyrmStats.strykelingfollyATK, wyrmStats.strykelingfollySPD,wyrmStats.strykelingfollyKBR);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("age")) this.age = compound.getInteger("age");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("age", this.age);
    }

    @Override
    public int tickTimer() {return ticksExisted;}

    @Override
    public void tick() {super.onUpdate();}
}

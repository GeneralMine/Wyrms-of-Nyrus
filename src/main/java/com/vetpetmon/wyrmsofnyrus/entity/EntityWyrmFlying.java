package com.vetpetmon.wyrmsofnyrus.entity;

import net.minecraft.entity.monster.IMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Abstract class for flying wyrms, adds a lot of code and methods for fliers.
 */
public abstract class EntityWyrmFlying extends EntityWyrm implements IAnimatable, IMob {

    protected static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(EntityWyrmFlying.class, DataSerializers.BOOLEAN);

    // Shared GeckoLib method for all wyrms. Saves some spaghetti in the non-abstract wyrm classes.
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityWyrmFlying(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
    }

    // GeckoLib thing so that way all wyrms share this code automatically. Saves some time.
    public AnimationFactory getFactory() {return this.factory;}

    public boolean isGrounded(){
        return (world.isBlockFullCube(new BlockPos(getPosition().getX(), getPosition().getY()-1,getPosition().getZ())));
    }
}

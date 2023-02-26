package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.IMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Abstract class for flying wyrms, adds a lot of code and methods for fliers.
 */
public abstract class EntityWyrmFlying extends EntityWyrm implements IAnimatable, IMob {

    protected static DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(EntityWyrmFlying.class, DataSerializers.BOOLEAN);

    // Shared GeckoLib method for all wyrms. Saves some spaghetti in the non-abstract wyrm classes.
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityWyrmFlying(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 1;
    }

    // GeckoLib thing so that way all wyrms share this code automatically. Saves some time.
    public AnimationFactory getFactory() {return this.factory;}

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isProjectile())
            return super.attackEntityFrom(source, (float) (amount * Radiogenetics.flyingWyrmProjWeakness));
        if (source == DamageSource.FALL)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    // Shared by all flying entities.
    @Override
    protected SoundEvent getFallSound(int heightIn) {return null;}

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public void fall(float distance, float damageMultiplier)
    {
    }
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

}

package com.vetpetmon.wyrmsofnyrus.block.generic;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import com.vetpetmon.synapselib.rendering.IHasModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGasRadioactive extends BlockGas implements IHasModel {

    public BlockGasRadioactive(String s) {
        super(s);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityWyrm)) {
            difficultyStats.applyPotionEffect(entityIn, MobEffects.WITHER, 30, 1);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
        if(!world.isRemote && rand.nextInt(100) == 1) world.setBlockToAir(pos); //make block decay over time.
        super.updateTick(world, pos, state, rand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand){
        super.randomDisplayTick(stateIn, world, pos, rand);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
    }
}

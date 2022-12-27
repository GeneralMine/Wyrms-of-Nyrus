package com.vetpetmon.wyrmsofnyrus.block.vile;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.generic.BlockBase;
import com.vetpetmon.wyrmsofnyrus.compat.srp;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.WorldConfig;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import com.vetpetmon.wyrmsofnyrus.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.synapselib.util.RNG;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockCorium extends BlockBase implements IHasModel {

    public static PropertyInteger AGE = PropertyInteger.create("age", 0, WorldConfig.coriumDecayRate);
    public BlockCorium(Material m, String s, SoundType st, float hardness, float blastresist, boolean hastooltip) {
        super(m, s, st, hardness, blastresist, hastooltip);
        this.setLightLevel(0.2F);
        this.setTickRandomly(true);
        this.setCanCreatureSpawn(false);
    }
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.NETHERRACK;
    }

    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase)
        {
            entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 2.0F);
            entityIn.setFire(10);
        }
        difficultyStats.applyPotionEffect(entityIn, MobEffects.WITHER, 600, 2);

        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, meta);
    }
    public int getMetaFromState(IBlockState state){return state.getValue(AGE);}
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        int age = state.getValue(AGE) + 1;
        if (age > WorldConfig.coriumDecayRate) {
            world.setBlockState(pos, AllBlocks.inertcorium.getDefaultState(), 3);
        }
        else {
            int Range = 1;
            int x = (int) ((pos.getX()) + RNG.PMRange(Range));
            int y = (int) ((pos.getY()) + RNG.PMRange(Range));
            int z = (int) ((pos.getZ()) + RNG.PMRange(Range));
            BlockPos posi = new BlockPos(x, y, z);
            if (srp.isEnabled() && WorldConfig.vileEnabled && ((RNG.getIntRangeInclu(0, Invasion.creepSpreadRate)) == Invasion.creepSpreadRate)) {
                if (world.getBlockState(posi).isFullCube() && !(world.getBlockState(posi).getBlock() == (Block.getBlockFromName("wyrmsofnyrus:corium")))) {
                    world.setBlockState(posi, AllBlocks.corium.getDefaultState().withProperty(AGE, age), 3);
                    HiveCreepSpreadFurther.addPoints(world);
                }
                else if (world.getBlockState(posi).getBlock() == Blocks.AIR) {
                    world.setBlockState(posi, AllBlocks.radioactivegas.getDefaultState(), 3);
                }
                world.setBlockState(pos, state.withProperty(AGE, age), 2);
            }
        }
    }
}

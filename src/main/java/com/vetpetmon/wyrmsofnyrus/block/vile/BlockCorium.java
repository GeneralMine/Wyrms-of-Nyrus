package com.vetpetmon.wyrmsofnyrus.block.vile;

import com.vetpetmon.wyrmsofnyrus.block.generic.BlockBase;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCorium extends BlockBase {
    public BlockCorium(Material m, String s, SoundType st, float hardness, float blastresist, boolean hastooltip) {
        super(m, s, st, hardness, blastresist, hastooltip);
        this.setLightLevel(0.2F);
        this.setTickRandomly(true);
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
}

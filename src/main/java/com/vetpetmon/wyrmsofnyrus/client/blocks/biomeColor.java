package com.vetpetmon.wyrmsofnyrus.client.blocks;

import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepedGrass;
import com.vetpetmon.wyrmsofnyrus.block.IWyrmBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepedGrass.block;

@SideOnly(Side.CLIENT)
public class biomeColor {
    public static final IBlockColor CREEPGRASSCOLORING;

    static {
        CREEPGRASSCOLORING = new IBlockColor() {
            @Override
            @SideOnly(Side.CLIENT)
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                return worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
            }
        };
    }

    public static final IItemColor BLOCKITEMCOLOR = (stack, tintIndex) -> {
        IBlockState state = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
        IBlockColor blockColor = ((IWyrmBlocks)state.getBlock()).getBlockColor();
        return blockColor == null ? 0xFFFFFF : blockColor.colorMultiplier(state, null, null, tintIndex);
    };

    public static BlockColors blockColorInt() {
        final BlockColors blockColor = new BlockColors();
        blockColor.registerBlockColorHandler(new IBlockColor()
        {
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
            {
                return worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
            }
        }, BlockHiveCreepedGrass.block);

        return blockColor;
    }
}

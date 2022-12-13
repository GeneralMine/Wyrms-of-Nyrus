package com.vetpetmon.wyrmsofnyrus.block.generic;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockPillar extends BlockRotatedPillar implements IHasModel {
    private boolean hastooltip = false;
    private boolean stopSpawns = false;
    public BlockPillar(Material m, String s, SoundType st, float hardness, float blastresist){
        super(m);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);
        this.setSoundType(st);
        this.setHardness(hardness);
        this.setResistance(blastresist);
        setCreativeTab(wyrmsofnyrus.wyrmTabs);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public BlockPillar(Material m, String s, SoundType st, float hardness, float blastresist, boolean hastooltip){
        super(m);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);
        this.setSoundType(st);
        this.setHardness(hardness);
        this.setResistance(blastresist);
        this.hastooltip = hastooltip;
        setCreativeTab(wyrmsofnyrus.wyrmTabs);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    @Override
    public void registerModels() {
        wyrmsofnyrus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public Block setCanCreatureSpawn(boolean flag) {
        this.stopSpawns = flag;
        return this;
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return (this.stopSpawns && isSideSolid(state, world, pos, EnumFacing.UP));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (hastooltip) tooltip.add(I18n.format("tooltip.won." + this.getUnlocalizedName(), new Object[0]));
    }
}

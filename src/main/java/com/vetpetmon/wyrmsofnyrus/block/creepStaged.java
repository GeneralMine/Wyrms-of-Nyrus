package com.vetpetmon.wyrmsofnyrus.block;

import com.vetpetmon.wyrmsofnyrus.creativetab.TabWyrms;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.item.IHasModel;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.Block;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class creepStaged extends Block implements IHasModel {

    public static PropertyInteger STAGE = PropertyInteger.create("stage", 0, 9);
    protected Block convertTo;

    public creepStaged(String name, float hardness, float blastResistance, Block convertBlockTo) {
        super(BlockMaterials.CREEP);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHardness(hardness);
        this.setResistance(blastResistance);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.SLIME);
        this.setConvert(convertBlockTo);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
        setCreativeTab(TabWyrms.tab);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    @Override
    public void registerModels() {
        wyrmsofnyrus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    public void setConvert(Block convertBlockTo){
        this.convertTo = convertBlockTo;
    }
    public Block getConvert(){
        return this.convertTo;
    }

    @Override
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(itemstack, world, list, flag);
        list.add("Gradually takes over blocks before converting them. Can not spread further until the block is fully converted.");
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(STAGE, meta);
    }
    public int getMetaFromState(IBlockState state){return state.getValue(STAGE);}
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {STAGE});
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Converts a block into another block. OVERRIDE THIS IN EXTENDED CLASSES!
     * @param world World
     * @param pos BlockPos
     * @param convertTo BlockState
     */
    public void convert(World world, BlockPos pos, Block convertTo, int activeFlag) {
        world.setBlockState(pos, convertTo.getDefaultState().withProperty(BlockHivecreepBase.ACTIVE, activeFlag), 3);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        int stage = state.getValue(STAGE) + 1;
        switch (stage) {
            case(4):
            case(5):
                assert false;
                convert(world, pos, getConvert(), 1);
                break;
            case(10):
                assert false;
                convert(world, pos, getConvert(), 0);
                break;
            default:
                world.setBlockState(pos, state.withProperty(STAGE, stage), 2);
        }
    }
}

package com.vetpetmon.wyrmsofnyrus.block.hivecreep;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.BlockMaterials;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.WorldConfig;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class BlockHivecreepPillar extends BlockRotatedPillar implements IHasModel {
    public static final PropertyInteger ACTIVE = PropertyInteger.create("active", 0, 1);

    public BlockHivecreepPillar(String name, float hardness, float blastresist) {
        super(BlockMaterials.CREEP);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHardness(hardness);
        this.setResistance(blastresist);
        this.setSoundType(SoundType.SLIME);
        setCreativeTab(wyrmsofnyrus.wyrmTabs);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public int tickRate(World world) {
        return Invasion.creepTickRate;
    }


    @Override
    public void registerModels() {
        wyrmsofnyrus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Override
    protected BlockStateContainer createBlockState() {return new BlockStateContainer(this, AXIS, ACTIVE);}

    // Oh, this is hacky as all hell. It works, however!
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[(meta % 3)]).withProperty(ACTIVE, ((meta >= 3) ? 1 : 0));
    }
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AXIS).ordinal() * 4 + ((state.getValue(ACTIVE) == 1) ? 1 : 0);
    }
    
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return WorldConfig.creepBlocksStopSpawns;
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        int active = state.getValue(ACTIVE);
        if(active == 1) {HiveCreepSpreadFurther.executescript(pos, world);}
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

}

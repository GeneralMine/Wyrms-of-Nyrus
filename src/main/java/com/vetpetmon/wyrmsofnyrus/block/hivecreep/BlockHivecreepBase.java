package com.vetpetmon.wyrmsofnyrus.block.hivecreep;

import com.vetpetmon.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.BlockMaterials;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.WorldConfig;
import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

public class BlockHivecreepBase extends Block implements IHasModel {
    public static PropertyInteger ACTIVE = PropertyInteger.create("active", 0, 1);
    public BlockHivecreepBase(String name, float hardness, float blastresist) {
        super(BlockMaterials.CREEP);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setHardness(hardness);
        this.setResistance(blastresist);
        this.setSoundType(SoundType.SLIME);
        setCreativeTab(WyrmsOfNyrus.wyrmTabs);
        AllBlocks.ALL_BLOCKS.add(this);
        AllItems.ALL_ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public int tickRate(World world) {
        return Invasion.creepTickRate;
    }


    @Override
    public void registerModels() {
        WyrmsOfNyrus.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ACTIVE, meta);
    }

    public int getMetaFromState(IBlockState state){return state.getValue(ACTIVE);}
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ACTIVE});
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
        if (world.isAirBlock(new BlockPos(pos.getX(), pos.getY()+1,pos.getZ())) && (Objects.equals(this.getRegistryName(), new ResourceLocation("wyrmsofnyrus:hivecreepblock"))) ){
            if (active == 0) world.setBlockState((pos), AllBlocks.hivecreeptop.getDefaultState().withProperty(ACTIVE, active), 3);
            else world.setBlockState((pos), AllBlocks.hivecreeptop.getDefaultState().withProperty(ACTIVE, 1), 3);
        }
        if(active == 1) {
            HiveCreepSpreadFurther.executescript(pos, world);
        }
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }
}

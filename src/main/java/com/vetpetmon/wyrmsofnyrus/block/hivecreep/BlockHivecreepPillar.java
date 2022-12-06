package com.vetpetmon.wyrmsofnyrus.block.hivecreep;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.BlockMaterials;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import com.vetpetmon.wyrmsofnyrus.item.IHasModel;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.addPoints;
import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.creepspreadRules;

public class BlockHivecreepPillar extends BlockRotatedPillar implements IHasModel {

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
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        //HiveCreepSpreadFurther.executescript(pos, world);
        BlockPos posi = new BlockPos(pos.getX(), pos.getY()+1, pos.getZ());
        Block blockLooking = (world.getBlockState(posi)).getBlock();
        if ((creepspreadRules(posi, world, pos))) if ((blockLooking instanceof BlockLog) || (blockLooking instanceof BlockOldLog)) {world.setBlockState(posi, AllBlocks.creeplog.getDefaultState(), 3);addPoints(world);}
        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

}

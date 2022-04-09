package com.vetpetmon.wyrmsofnyrus.blocks;

import com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepBlockUpdateTick;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionBlockSpread;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockHiveCreepBlockTop extends Block{
    public static final Block block = null;

    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation("wyrmsofnyrus:hivecreepblocktop", "inventory"));
    }

    public BlockHiveCreepBlockTop(String s) {
        super(Material.CRAFTED_SNOW);
        setUnlocalizedName("hivecreepblock");
        setSoundType(SoundType.SLIME);
        setHarvestLevel("shovel", 3);
        setHardness(1.5F);
        setResistance(10F);
        setLightLevel(0F);
        setLightOpacity(255);
        //setCreativeTab(TabWyrms.tab);
    }
    @Override
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
        super.addInformation(itemstack, world, list, flag);
        list.add("Spreads!");
    }

    @Override
    public int tickRate(World world) {
        return 400;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
        {
            Map<String, Object> $_dependencies = new HashMap<>();
            $_dependencies.put("world", world);
            InvasionBlockSpread.run($_dependencies);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        {
            Map<String, Object> $_dependencies = new HashMap<>();
            $_dependencies.put("x", x);
            $_dependencies.put("y", y);
            $_dependencies.put("z", z);
            $_dependencies.put("world", world);
            HiveCreepBlockUpdateTick.executescript($_dependencies);
        }
        world.scheduleUpdate(new BlockPos(x, y, z), this, this.tickRate(world));
    }
}

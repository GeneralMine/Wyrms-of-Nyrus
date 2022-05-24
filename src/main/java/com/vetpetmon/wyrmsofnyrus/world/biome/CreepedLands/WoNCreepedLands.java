package com.vetpetmon.wyrmsofnyrus.world.biome.CreepedLands;

import com.vetpetmon.wyrmsofnyrus.block.BlockCreepstoneInactive;
import com.vetpetmon.wyrmsofnyrus.block.BlockHiveCreepBlock;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityDobber;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;
import com.vetpetmon.wyrmsofnyrus.world.biome.WoNBiome;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WoNCreepedLands extends WoNBiome {
    public WoNCreepedLands(final BiomeProperties properties, final ResourceLocation registryName) {
        super(new Biome.BiomeProperties("Creeped Lands").setRainfall(0.5F).setBaseHeight(0.1F).setWaterColor(-6193834).setHeightVariation(0.2F)
                .setTemperature(0.5F), wyrmsofnyrus.getResource("creeped_lands"));
        topBlock = BlockHiveCreepBlock.block.getDefaultState();
        fillerBlock = BlockCreepstoneInactive.block.getDefaultState();
        decorator.treesPerChunk = 0;
        decorator.flowersPerChunk = 0;
        decorator.grassPerChunk = 0;
        decorator.mushroomsPerChunk = 0;
        decorator.bigMushroomsPerChunk = 0;
        decorator.reedsPerChunk = 0;
        decorator.cactiPerChunk = 0;
        decorator.sandPatchesPerChunk = 0;
        decorator.gravelPatchesPerChunk = 0;
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWyrmling.class, 20, 1, 4));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityDobber.class, 20, 5, 6));
    }


    @SideOnly(Side.CLIENT)
    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return -10079488;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return -10079488;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getSkyColorByTemp(float currentTemperature) {
        return -13159;
    }
}

package com.vetpetmon.wyrmsofnyrus.world.biome;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

public abstract class WoNBiome extends Biome {

    public WoNBiome(final BiomeProperties properties, final ResourceLocation registryName) {
        super(properties);

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();

        this.setRegistryName(registryName);
    }
}

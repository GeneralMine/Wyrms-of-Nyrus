package com.vetpetmon.wyrmsofnyrus.world.biome;

import com.google.common.collect.Lists;
import com.vetpetmon.wyrmsofnyrus.world.biome.CreepedLands.WoNCreepedLands;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;

@Mod.EventBusSubscriber()
@GameRegistry.ObjectHolder(wyrmsofnyrus.MODID)
public class biomeReg {
    private static final WoNBiome CREEPLANDS = new WoNCreepedLands(
            new Biome.BiomeProperties("Creeped Lands").setRainfall(0.5F).setBaseHeight(0.1F).setWaterColor(-6193834).setHeightVariation(0.2F).setTemperature(0.5F), wyrmsofnyrus.getResource("creeped_lands"));

    private static final ArrayList POSSIBLE = Lists.newArrayList();

    @SubscribeEvent
    public static void registerBiome(final RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(CREEPLANDS);

        BiomeDictionary.addTypes(CREEPLANDS, BiomeDictionary.Type.PLAINS);

        registerPossibleBiome(CREEPLANDS);
    }

    public static void registerPossibleBiome(final WoNBiome... biomes)
    {
        POSSIBLE.addAll(Arrays.asList(biomes));
    }
}

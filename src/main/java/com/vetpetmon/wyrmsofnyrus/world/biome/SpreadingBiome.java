package com.vetpetmon.wyrmsofnyrus.world.biome;

import com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages.INewChunk;
import com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages.biomeChange;
import com.vetpetmon.wyrmsofnyrus.world.biome.CreepedLands.WoNCreepedLands;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class SpreadingBiome {
    public static WoNCreepedLands biomeCreeped;
    /**
     * Changes the biome of an area.
     * @param worldIn The world
     * @param pos The position to set the biome at
     */
    public SpreadingBiome(World worldIn, BlockPos pos, Biome biome) {
        World world = worldIn;
        //INewChunk chunk = (INewChunk) world.getChunkFromBlockCoords(pos);
        //Chunk chunk = world.getChunkFromBlockCoords(pos);

        if (!world.isBlockLoaded(pos)) return; //Check if world is loaded. This is so that we dont accidentally change something in an unloaded chunk, causing memleaks and tick lag from chunkloading.
        //((INewChunk) chunk).getBiomeIDList()[(pos.getZ() & 15) << 4 | pos.getX() & 15] = Biome.getIdForBiome(biome);
        if (!(Biome.REGISTRY.getNameForObject(world.getBiome(pos))).equals(new ResourceLocation("wyrmsofnyrus:creeped_lands"))) {
            if (!world.isRemote) {
                new biomeChange(pos.getX(),pos.getZ(),Biome.getIdForBiome(biome));
            }
        }
    }
}

package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmProber;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;

// This is the loader for all wyrm entities

public final class WyrmRegister {
    public static void register() {
        int id = 0;

        // I'm tired of switching windows evey 5 seconds because I don't have photographic memory. I'm pasting the class def here from the docs. Thank me later.
        //registerModEntity(java.lang.Class<? extends Entity> entityClass, java.lang.String entityName, int id, java.lang.Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)

        EntityRegistry.registerModEntity(new ResourceLocation("wyrmsofnyrus", "hexe_pod"),EntityHexePod.class,"hexe_pod",id++, wyrmsofnyrus.instance, 128, 1, true, -7981824, -154);
        EntityRegistry.registerModEntity(new ResourceLocation("wyrmsofnyrus", "wyrmling"),EntityWyrmling.class,"wyrmling",id++, wyrmsofnyrus.instance, 32, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation("wyrmsofnyrus", "wyrmprober"), EntityWyrmProber.class,"wyrmprober",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
    }
}

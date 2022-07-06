package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;

import static com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus.MODID;

// This is the loader for all wyrm entities

public final class WyrmRegister {
    public static void register() {
        int id = 0;

        // I'm tired of switching windows evey 5 seconds because I don't have photographic memory. I'm pasting the class def here from the docs. Thank me later.
        //registerModEntity(java.lang.Class<? extends Entity> entityClass, java.lang.String entityName, int id, java.lang.Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)

        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "hexe_pod"),EntityHexePod.class,"hexe_pod",id++, wyrmsofnyrus.instance, 128, 1, true, -7981824, -154);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmling"),EntityWyrmling.class,"wyrmling",id++, wyrmsofnyrus.instance, 32, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmprober"), EntityWyrmProber.class,"wyrmprober",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "thevisitor"), EntityTheVisitor.class,"thevisitor",id++, wyrmsofnyrus.instance, 128, 10, false, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmworker"), EntityWyrmWorker.class,"wyrmworker",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmrover"), EntityWyrmRover.class,"wyrmrover",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmroverevo"), EntityWyrmRoverUranium.class,"wyrmroverevo",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmdobber"), EntityDobber.class,"wyrmdobber",id++, wyrmsofnyrus.instance, 32, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "callouspod"), EntityCallousPod.class,"callouspod",id++, wyrmsofnyrus.instance, 128, 1, true, -7981824, -154);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmsoldier"), EntityWyrmSoldier.class,"wyrmsoldier",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
        if (Invasion.isCreepEnabled()) EntityRegistry.registerModEntity(new ResourceLocation(MODID, "creepwyrm"), EntityCreepwyrm.class,"creepwyrm",id++, wyrmsofnyrus.instance, 128, 1, true, -7981824, -154);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmmyrmur"), EntityMyrmur.class,"wyrmmyrmur",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "wyrmsoldierevo"), EntityWyrmSoldierInfectoid.class,"wyrmsoldierevo",id++, wyrmsofnyrus.instance, 64, 1, true, -26317, -52);
    }
}

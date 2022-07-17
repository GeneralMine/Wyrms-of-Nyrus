package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;

import com.vetpetmon.wyrmsofnyrus.synapselib.RegHelper;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;

import static com.vetpetmon.wyrmsofnyrus.synapselib.RegHelper.resName;

// This is the loader for all wyrm entities

public final class WyrmRegister {
    public static void register() {
        int id = 0;

        // I'm tired of switching windows evey 5 seconds because I don't have photographic memory. I'm pasting the class def here from the docs. Thank me later.
        //registerModEntity(java.lang.Class<? extends Entity> entityClass, java.lang.String entityName, int id, java.lang.Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)

        RegHelper.RegEntity("hexe_pod", EntityHexePod.class, id++, 128, 1);
        RegHelper.RegEntity("wyrmling", EntityWyrmling.class, id++, 32, 2);
        RegHelper.RegEntity("wyrmprober", EntityWyrmProber.class, id++, 64, 2);
        RegHelper.RegEntity("thevisitor", EntityTheVisitor.class, id++, 128, 2);
        RegHelper.RegEntity("wyrmworker", EntityWyrmWorker.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmrover", EntityWyrmRover.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmroverevo", EntityWyrmRoverUranium.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmdobber", EntityDobber.class, id++, 64, 2);
        RegHelper.RegEntity("callouspod", EntityCallousPod.class, id++, 128, 1);
        RegHelper.RegEntity("wyrmsoldier", EntityWyrmSoldier.class, id++, 64, 2);
        if (Invasion.isCreepEnabled()) RegHelper.RegEntity("creepwyrm", EntityCreepwyrm.class, id++, 128, 1);
        RegHelper.RegEntity("wyrmmyrmur", EntityMyrmur.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmsoldierevo", EntityWyrmSoldierInfectoid.class, id++, 64, 2);
        
    }
}

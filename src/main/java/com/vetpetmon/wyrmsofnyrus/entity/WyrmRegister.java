package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.hivemind.*;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;

import com.vetpetmon.wyrmsofnyrus.synapselib.RegHelper;

// This is the loader for all wyrm entities

public final class WyrmRegister {
    public static void register() {
        int id = 0;

        // Thankfully, with synapseLib, I'll never have to look at the direct classes ever again.
        RegHelper.RegEntity("hexe_pod", EntityHexePod.class, id++, 256, 1);
        RegHelper.RegEntity("wyrmling", EntityWyrmling.class, id++, 32, 2);
        RegHelper.RegEntity("wyrmprober", EntityWyrmProber.class, id++, 64, 2);
        RegHelper.RegEntity("thevisitor", EntityTheVisitor.class, id++, 512, 2);
        RegHelper.RegEntity("wyrmworker", EntityWyrmWorker.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmrover", EntityWyrmRover.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmroverevo", EntityWyrmRoverUranium.class, id++, 64, 2);
        RegHelper.RegEntity("wyrmdobber", EntityDobber.class, id++, 32, 2);
        RegHelper.RegEntity("callouspod", EntityCallousPod.class, id++, 256, 1);
        RegHelper.RegEntity("wyrmsoldier", EntityWyrmSoldier.class, id++, 64, 2);
        if (Invasion.isCreepEnabled()) RegHelper.RegEntity("creepwyrm", EntityCreepwyrm.class, id++, 128, 1);
        RegHelper.RegEntity("wyrmmyrmur", EntityMyrmur.class, id++, 32, 2);
        RegHelper.RegEntity("wyrmsoldierevo", EntityWyrmSoldierInfectoid.class, id++, 46, 2);
        RegHelper.RegEntity("wyrmwarrior", EntityWyrmWarrior.class, id++, 64, 2);
        if (Evo.evoHBMVariantsEnabled && hbm.isEnabled()) {RegHelper.RegEntity("wyrmwarriortainted", EntityWyrmWarriorTainted.class, id++, 64, 2);}

        id += 100;

        // Register Waypoints
        RegHelper.RegEntity("creepwyrmwaypoint", EntityCreepwyrmWaypoint.class, id++, 64);
        RegHelper.RegEntity("overseerwaypoint", EntityOverseerWaypoint.class, id++, 64);

    }
}

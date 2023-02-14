package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.*;
import com.vetpetmon.wyrmsofnyrus.entity.follies.EntityStrykeling;
import com.vetpetmon.wyrmsofnyrus.entity.hivemind.EntityCreepwyrmWaypoint;
import com.vetpetmon.wyrmsofnyrus.entity.hivemind.EntityOverseerWaypoint;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import com.vetpetmon.wyrmsofnyrus.synapselib.RegHelper;

import java.util.Objects;

// This is the loader for all wyrm entities

public final class WyrmRegister {
    public static String[][] wyrmIDs = {
            {"hexe_pod","true","256"}, {"wyrmling","true","32"}, {"wyrmprober","true","64"}, {"thevisitor","true","512"}, {"wyrmrover","true","64"}, {"wyrmroverevo","true","64"}, {"callouspod","true","256"}, {"wyrmsoldier","true","64"}, {"creepwyrm",Boolean.toString(Invasion.isCreepEnabled()),"128"}, {"wyrmmyrmur","true","32"}, {"wyrmsoldierevo","true","64"}, {"wyrmwarrior","true","128"}, {"wyrmwarriortainted",Boolean.toString(Evo.evoHBMVariantsEnabled && hbm.isEnabled()),"128"}, {"creepedbiter","true","64"}, {"creepedhumanoid","true","64"}, {"creeppod","true","256"}, {"wyrmsoldierfrost","true","64"}, {"creepling","true","32"}, {"strykeling","true","64"},
    };
    public static Class[] wyrmClasses = {
            EntityHexePod.class, EntityWyrmling.class, EntityWyrmProber.class, EntityTheVisitor.class, EntityWyrmWorker.class, EntityWyrmRover.class, EntityWyrmRoverUranium.class, EntityCallousPod.class, EntityWyrmSoldier.class, EntityCreepwyrm.class, EntityMyrmur.class, EntityWyrmSoldierInfectoid.class, EntityWyrmWarrior.class, EntityWyrmWarriorTainted.class, EntityBiter.class, EntityCreepedHumanoid.class, EntityCreepPod.class, EntityWyrmSoldierfrost.class, EntityCreepling.class, EntityStrykeling.class
    };
    public static void register() {
        int id = 0;

        //Replace the wall of code with a single loop AND make the spawn item in the same go! Awesome! ☆ Smiles.png .x. ☆
        for (int i = 0; i < wyrmIDs.length; i++) {
            // To deal with the fact some wyrms can be skipped from registry, we must include a checker
            if(Objects.equals(wyrmIDs[i][1], "true")) RegHelper.RegEntity(wyrmIDs[i][0], wyrmClasses[i], i, Integer.parseInt(wyrmIDs[i][2]), 1);
        }

        id = 130;
        // Register Waypoints
        RegHelper.RegEntity("creepwyrmwaypoint", EntityCreepwyrmWaypoint.class, id++, 64);
        RegHelper.RegEntity("overseerwaypoint", EntityOverseerWaypoint.class, id++, 64);

    }
}

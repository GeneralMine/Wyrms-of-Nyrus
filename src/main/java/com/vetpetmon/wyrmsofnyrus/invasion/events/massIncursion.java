package com.vetpetmon.wyrmsofnyrus.invasion.events;

import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;

import java.util.Map;

public class massIncursion {
    public static void call(Map<String, Object> e, int level) {
        int raidSize = (RNG.getIntRangeInclu((4 + level),(6 + (level*2))));
        for (int i = 0; i < raidSize; i++) {
            scoutingPodRaid.call(e);
        }
        for (int i = 0; i < raidSize; i = i+2) {
            smallPodRaid.Do(e);
        }
    }
}

package com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt;

import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;

/* Collective Consiousness Host Mind
 *
 * Hosts the main host mind for Californium.
 * Shares target with Wyrms, so wyrms from 500 blocks away
 * know where the player lives.
 */
public class GestaltHostMind {
    private static EntityLivingBase kollectiveTarget;
    private static int lastSeen;
    public static EntityLivingBase getKollectiveTarget() {
        return kollectiveTarget;
    }

    public static int getLastSeen() {
        return lastSeen;
    }

    public static void addToLastSeen() {
        GestaltHostMind.lastSeen++;
    }

    public static void setKollectiveTarget(@Nullable EntityLivingBase target) {
        kollectiveTarget = target;
        lastSeen = 0;
    }
}

package com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.wyrmsofnyrus.handlers.WoNHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/* Collective Consiousness Host Mind
 *
 * Hosts the main host mind for Californium.
 * Shares target with Wyrms, so wyrms from 500 blocks away
 * know where the player lives.
 */
public class GestaltHostMind {
    private static EntityLivingBase kollectiveTarget;
    public static boolean isGestaltActive = false, infamyIsMaxed = false;
    public static EntityLivingBase getKollectiveTarget() {
        return kollectiveTarget;
    }
    public static int getAttentionLevel(World world) {
        int infamy = WyrmVariables.WorldVariables.get(world).infamy;
        if(WyrmVariables.WorldVariables.get(world).infamy == 100 && !infamyIsMaxed) {
            //WyrmsOfNyrus.logger.info("Infamy capped!");
            infamyIsMaxed = true;
        }
        return infamy;
    }

    public static void setAttentionLevel(int attentionLevel, World world) {
        WyrmVariables.WorldVariables.get(world).infamy = attentionLevel;
        WyrmVariables.WorldVariables.get(world).syncData(world);
    }
    public static void increaseAttentionLevel(World world) {
        if (!isGestaltActive) isGestaltActive = true;
        if(WyrmVariables.WorldVariables.get(world).infamy < 100) WyrmVariables.WorldVariables.get(world).infamy++;
        if(WyrmVariables.WorldVariables.get(world).infamy == 100 && !world.playerEntities.isEmpty() && !infamyIsMaxed) {
            for (EntityPlayer player: world.playerEntities) {
                world.playSound(null, player.getPosition(), SoundRegistry.maxinfamy, SoundCategory.HOSTILE, 4,1);
                //WyrmsOfNyrus.logger.info("Infamy capped!");
            }
            infamyIsMaxed = true;
        }
        WyrmVariables.WorldVariables.get(world).syncData(world);
    }
    public static void decreaseAttentionLevel(World world) {

            if (getAttentionLevel(world) > 0) WyrmVariables.WorldVariables.get(world).infamy--;
            if (getAttentionLevel(world) < 100 && infamyIsMaxed) {
                infamyIsMaxed = false;
                //WyrmsOfNyrus.logger.info("Infamy uncapped!");
                WoNHandler.infamyDecay = 5;
            }
            WyrmVariables.WorldVariables.get(world).syncData(world);
    }

    public static void setKollectiveTarget(@Nullable EntityLivingBase target) {
        kollectiveTarget = target;
    }
}

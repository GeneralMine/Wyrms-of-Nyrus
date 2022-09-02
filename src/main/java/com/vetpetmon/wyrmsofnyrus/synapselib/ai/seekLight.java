package com.vetpetmon.wyrmsofnyrus.synapselib.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;

public class seekLight {

    /**
     * Entity looks for the light levels at its position and the destination goal it is given.
     *
     * There are two modes:
     * 1 (default) checks if the tested position is above the threshold AND it's above the entity's current position's light level.
     * Any other value checks if the tested position's light level is above the entity's local light level.
     *
     * @param entityIn Entity
     * @param pos Block position that is being tested
     * @param lightThreshold Minimum light level to check for
     * @param mode 1 is the default
     * @return boolean value
     */
    public static boolean findLightSource(EntityLiving entityIn, BlockPos pos, int lightThreshold, int mode) {
        if (entityIn.world.rand.nextInt(6) == 0) {
            int localLightVal = entityIn.world.getLightFromNeighbors(entityIn.getPosition());

            // Check if pos is actually loaded or not, avoid encouraging mobs to cross and possibly load unloaded chunks.
            if (!entityIn.world.isBlockLoaded(pos)) {
                int checkedLightValue = entityIn.world.getLightFromNeighbors(pos);
                if (mode == 1)
                    return checkedLightValue > lightThreshold && checkedLightValue > localLightVal;
                else
                    return checkedLightValue > localLightVal;
            }

        }
        return false;
    }
    public static boolean findLightSource(EntityLiving entityIn, BlockPos pos, int lightThreshold) {
        return findLightSource(entityIn, pos, lightThreshold, 1);
    }
}

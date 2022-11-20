package com.vetpetmon.wyrmsofnyrus.synapselib;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegHelper extends libVars {
    public static ResourceLocation resName(String NAME) {
        return new ResourceLocation(ModID, NAME);
    }

    /**
     * Shorthand to ragister an entity into the game. Handles resource location and various repeated parameters.
     * Often reduces character count by 40% compared to directly using EntityRegistry.registerModEntity().
     * @param entityName
     * @param entityClass
     * @param id
     * @param trackRange
     */
    public static void RegEntity(String entityName, java.lang.Class<? extends Entity> entityClass, int id, int trackRange){
        EntityRegistry.registerModEntity(
                resName(entityName),
                entityClass,
                entityName,
                id,
                libVars.modInstance,
                trackRange,
                1,
                true
        );
    }
    // Registers with a spawn egg.
    public static void RegEntity(String entityName, java.lang.Class<? extends Entity> entityClass, int id, int trackRange, int eggType){
        int egg1;
        int egg2;
        switch (eggType) {
            case (2):
                egg1 = -26317;
                egg2 = -52;
                break;
            default:
                egg1 = -7981824;
                egg2 = -154;
                break;
        }
        EntityRegistry.registerModEntity(
                resName(entityName),
                entityClass,
                entityName,
                id,
                libVars.modInstance,
                trackRange,
                1,
                true,
                egg1,
                egg2
        );
    }
}

package com.vetpetmon.wyrmsofnyrus.synapselib;

import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegHelper extends libVars {
    public static ResourceLocation resName(String NAME) {
        return new ResourceLocation(ModID, NAME);
    }

    public static void RegEntity(String entityName, java.lang.Class<? extends Entity> entityClass, int id, int trackRange){
        EntityRegistry.registerModEntity(
                resName(entityName),
                entityClass,
                entityName,
                id,
                wyrmsofnyrus.instance,
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
                wyrmsofnyrus.instance,
                trackRange,
                1,
                true,
                egg1,
                egg2
        );
    }
}

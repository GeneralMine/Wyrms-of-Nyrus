package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class HBMwyrms {
    public static Entity getWyrm(String wyrm, World world) {
        Entity EntityType;
        switch(wyrm) {
            case("warrior"):
                EntityType = new EntityWyrmWarriorTainted(world);
                break;
            default:
                EntityType = new EntityWyrmWarriorTainted(world);
                break;
        }
        return EntityType;
    }
}

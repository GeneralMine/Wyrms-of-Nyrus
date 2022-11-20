package com.vetpetmon.wyrmsofnyrus.entity.hivemind;

import net.minecraft.world.World;

public class EntityCreepwyrmWaypoint extends EntityHivemind {
    public EntityCreepwyrmWaypoint(World worldIn) {
        super(worldIn);
        this.setAlertStrength(60);
        this.setSetGoal(waypointGoal.BECKON);
    }
}

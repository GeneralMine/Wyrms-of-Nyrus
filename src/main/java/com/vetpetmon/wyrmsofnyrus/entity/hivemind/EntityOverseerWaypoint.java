package com.vetpetmon.wyrmsofnyrus.entity.hivemind;

import net.minecraft.world.World;

public class EntityOverseerWaypoint extends EntityHivemind {
    public EntityOverseerWaypoint(World worldIn) {
        super(worldIn);
        this.setAlertStrength(4);
        this.setSetGoal(waypointGoal.BECKON);
    }
}

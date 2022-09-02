package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.synapselib.synMath;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.PathNavigateFlying;

//  This is a custom AI for flight-capable mobs.
//  They will try to avoid flying too high, and try to move in the air towards targets

public class FlyingMobAI extends EntityAIBase
{
    private final EntityMob parentEntity;
    private final double FlySpeed;
    private final int FlightLimitMax;
    private final int FlightLimitMin;

    public FlyingMobAI(EntityMob entity, double FlySpeed, int FlightMax, int FlightMin)
    {
        this.parentEntity = entity;
        this.setMutexBits(1);
        this.FlySpeed = FlySpeed;
        this.FlightLimitMax = FlightMax;
        this.FlightLimitMin = FlightMin;
    }

    public FlyingMobAI(EntityMob entity, double FlySpeed, int FlightMax)
    {
        this.parentEntity = entity;
        this.setMutexBits(1);
        this.FlySpeed = FlySpeed;
        this.FlightLimitMax = FlightMax;
        this.FlightLimitMin = 1;
    }

    public boolean shouldExecute() {
        return this.parentEntity.getAttackTarget() != null && !this.parentEntity.getMoveHelper().isUpdating();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.parentEntity.getMoveHelper().isUpdating() && this.parentEntity.getAttackTarget() != null
                && this.parentEntity.getAttackTarget().isEntityAlive();
    }

    @Override
    public void startExecuting() {
        EntityLivingBase livingentity = this.parentEntity.getAttackTarget();
        Vec3d vec3d = livingentity.getPositionEyes(1);
        this.parentEntity.getMoveHelper().setMoveTo(vec3d.x, synMath.clamp((float) vec3d.y, this.FlightLimitMin, this.FlightLimitMax), vec3d.z, FlySpeed);
    }

    @Override
    public void updateTask() {
        EntityLivingBase livingentity = this.parentEntity.getAttackTarget();
        double d0 = this.parentEntity.getDistanceSq(livingentity);
        if (d0 <= getAttackReachSq(livingentity)) {
            this.parentEntity.attackEntityAsMob(livingentity);
        } else if (d0 < 32) {
            Vec3d vec3d = livingentity.getPositionEyes(1);
            this.parentEntity.getMoveHelper().setMoveTo(vec3d.x, vec3d.y, vec3d.z, FlySpeed);
        }
    }

    protected double getAttackReachSq(EntityLivingBase attackTarget) {
        return this.parentEntity.width * 0.85 * this.parentEntity.height * 0.85 + attackTarget.height * 1.1;
    }
}
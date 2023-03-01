package com.vetpetmon.wyrmsofnyrus.locallib.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.Vec3d;

public class EntityAIFlierMob extends EntityAIBase
{
    private final EntityMob parentEntity;
    private final double FlySpeed;
    private final int FlightLimit;

    public EntityAIFlierMob(EntityMob entity, Double FlySpeed, Integer FlightLimit)
    {
        this.parentEntity = entity;
        this.setMutexBits(1);
        this.FlySpeed = FlySpeed;
        this.FlightLimit = FlightLimit;
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
        this.parentEntity.getMoveHelper().setMoveTo(vec3d.x, vec3d.y, vec3d.z, FlySpeed);
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
        return this.parentEntity.width * 1.25 * this.parentEntity.height * 1.5 + attackTarget.height * 1.2;
    }
}
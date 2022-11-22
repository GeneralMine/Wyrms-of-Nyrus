package com.vetpetmon.wyrmsofnyrus.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;

import java.util.Random;

public class voidwyrm extends EntityAIBase {
    private final EntityMob parentEntity;
    private final double FlySpeed;
    private final int FlightLevel;

    public voidwyrm(EntityMob entity, double FlySpeed, int FlightLevel)
    {
        this.parentEntity = entity;
        this.FlightLevel = FlightLevel;
        this.setMutexBits(1);
        this.FlySpeed = FlySpeed;
    }

    public boolean shouldExecute()
    {
        EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

        if (!entitymovehelper.isUpdating())
        {
            return true;
        }
        else
        {
            double d0 = entitymovehelper.getX() - this.parentEntity.posX;
            double d1 = FlightLevel;
            double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
    }

    public boolean shouldContinueExecuting()
    {
        return false;
    }

    public void startExecuting()
    {
        Random random = this.parentEntity.getRNG();
        double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 32.0F);
        double d1 = this.parentEntity.posY + (double)(FlightLevel);
        double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 32.0F);
        this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }
}

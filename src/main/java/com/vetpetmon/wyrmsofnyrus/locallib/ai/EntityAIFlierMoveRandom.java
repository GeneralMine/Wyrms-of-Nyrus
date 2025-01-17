package com.vetpetmon.wyrmsofnyrus.locallib.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

import java.util.Random;

public class EntityAIFlierMoveRandom extends EntityAIBase {
    private final EntityLiving parentEntity;

    public EntityAIFlierMoveRandom(EntityLiving entityIn)
    {
        this.parentEntity = entityIn;
        this.setMutexBits(1);
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
            double d1 = entitymovehelper.getY() - this.parentEntity.posY;
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
        double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }
}

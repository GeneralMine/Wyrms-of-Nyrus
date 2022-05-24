package com.vetpetmon.wyrmsofnyrus.entity.ability;


import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.Vec3d;

/*public class AIFlyingMobCharge extends EntityAIBase
{
    private final EntityMob parentEntity;
    private EntityMoveHelper moveHelper;
    private boolean isCharging;
    private Random rand;

    public AIFlyingMobCharge(EntityMob entity)
    {
        this.parentEntity = entity;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (this.parentEntity.getAttackTarget() != null && !this.parentEntity.getMoveHelper().isUpdating() && rand.nextInt(7) == 0)
        {
            return this.parentEntity.getDistanceSq(this.parentEntity.getAttackTarget()) > 4.0D;
        }
        else
        {
            return false;
        }
    }

    public boolean shouldContinueExecuting()
    {
        return this.parentEntity.getMoveHelper().isUpdating() && isCharging() && this.parentEntity.getAttackTarget() != null && this.parentEntity.getAttackTarget().isEntityAlive();
    }

    private boolean isCharging() {
        return 
    }

    public void startExecuting()
    {
        EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
        Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
        this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
        this.setCharging(true);
        //this.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff")), 1.0F, 1.0F);
    }

    public void resetTask()
    {
        this.setCharging(false);
    }

    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

        if (this.parentEntity.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox()))
        {
            this.parentEntity.attackEntityAsMob(entitylivingbase);
            setCharging(false);
        }
        else
        {
            double d0 = this.parentEntity.getDistanceSq(entitylivingbase);

            if (d0 < 9.0D)
            {
                Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                this.parentEntity.getMoveHelper().setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            }
        }
    }


    private void setCharging(boolean b) {
    }
}*/

public class AIFlyingMobCharge extends EntityAIBase
{
    public EntityMoveHelper moveHelper;
    private final EntityWyrm wyrm;
    public AIFlyingMobCharge(EntityWyrm wyrm)
    {
        this.wyrm = wyrm;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (wyrm.getAttackTarget() != null && !wyrm.getMoveHelper().isUpdating() && wyrm.rand.nextInt(7) == 0)
        {
            return wyrm.getDistanceSq(wyrm.getAttackTarget()) > 4.0D;
        }
        else
        {
            return false;
        }
    }

    public boolean shouldContinueExecuting()
    {
        return wyrm.getMoveHelper().isUpdating() && wyrm.isCharging() && wyrm.getAttackTarget() != null && wyrm.getAttackTarget().isEntityAlive();
    }

    public void startExecuting()
    {
        EntityLivingBase entitylivingbase = wyrm.getAttackTarget();
        Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
        moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
        wyrm.setCharging(true);
        wyrm.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
    }

    public void resetTask()
    {
        wyrm.setCharging(false);
    }

    public void updateTask()
    {
        EntityLivingBase entitylivingbase = wyrm.getAttackTarget();

        if (wyrm.getEntityBoundingBox().intersects(entitylivingbase.getEntityBoundingBox()))
        {
            wyrm.attackEntityAsMob(entitylivingbase);
            wyrm.setCharging(false);
        }
        else
        {
            double d0 = wyrm.getDistanceSq(entitylivingbase);

            if (d0 < 9.0D)
            {
                Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
                moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            }
        }
    }
}
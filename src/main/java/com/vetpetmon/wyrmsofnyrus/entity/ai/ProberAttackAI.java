package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.advancements.Advancements;
import com.vetpetmon.wyrmsofnyrus.handlers.WoNDamageSources;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ProberAttackAI extends EntityAIBase
{
    World world;
    protected EntityCreature attacker;
    protected int attackTick;
    double speedTowardsTarget;
    boolean longMemory;
    Path path;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;

    /**
     * An optimized version of EntityAIAttackMelee specifically for probers. It's shorter + ignores all armor.
     * @param creature The creature doing the attack
     * @param speedIn Speed the attacker moves at
     * @param useLongMemory Uses long memory?
     */
    public ProberAttackAI(EntityCreature creature, double speedIn, boolean useLongMemory)
    {
        this.attacker = creature;
        this.world = creature.world;
        this.speedTowardsTarget = speedIn;
        this.longMemory = useLongMemory;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else
        {
            this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);

            if (this.path != null)
            {
                return true;
            }
            else
            {
                return this.getAttackReachSqr(entitylivingbase) >= this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (!this.longMemory)
        {
            return !this.attacker.getNavigator().noPath();
        }
        else if (!this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase)))
        {
            return false;
        }
        else
        {
            return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
        }
    }

    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    public void resetTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
        {
            this.attacker.setAttackTarget((EntityLivingBase)null);
        }

        this.attacker.getNavigator().clearPath();
    }

    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
        --this.delayCounter;

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
        {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.getEntityBoundingBox().minY;
            this.targetZ = entitylivingbase.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);

            if (d0 > 1024.0D)
            {
                this.delayCounter += 10;
            }
            else if (d0 > 256.0D)
            {
                this.delayCounter += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
            {
                this.delayCounter += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entitylivingbase, d0);
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double p_190102_2_)
    {
        double d0 = this.getAttackReachSqr(target);

        if (p_190102_2_ <= d0 && this.attackTick <= 0)
        {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.attackEntityAsMob(target);
            target.attackEntityFrom(WoNDamageSources.PROBER, 50);
            if (target instanceof EntityPlayerMP) Advancements.grantAchievement((EntityPlayerMP) target, Advancements.probed);
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width;
    }
}
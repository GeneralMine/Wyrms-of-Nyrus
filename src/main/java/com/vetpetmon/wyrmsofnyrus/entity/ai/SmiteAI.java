package com.vetpetmon.wyrmsofnyrus.entity.ai;


import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;

public class SmiteAI extends EntityAIAttackMelee {

    protected float range, timesToStrike;
    private MobEntityBase entity;
    private int attackingTicks;

    /**
     * Summons a lightning bolt up to x times in y range on an enemy. Uses attack ID 11
     * @param creature Entity that is performing the banishment
     * @param speedIn Chase speed
     * @param useLongMemory Long memory
     * @param attackRange Attack range at which to reach stuff
     */
    public SmiteAI(MobEntityBase creature, double speedIn, boolean useLongMemory, float attackRange, int timesToSmite) {
        super(creature, speedIn, useLongMemory);
        this.entity = creature;
        this.range = attackRange;
        this.timesToStrike = timesToSmite;
    }
    public void resetTask()
    {
        super.resetTask();
        this.entity.setAttack(0);
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.attackingTicks = 0;
    }

    public void updateTask()
    {
        super.updateTask();
        ++this.attackingTicks;

        if (this.attackingTicks >= 1 && this.attackTick < 52)
        {
            this.entity.setAttack(10);
        }
        else
        {
            this.entity.setAttack(0);
        }
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double p_190102_2_)
    {
        double d0 = this.getAttackReachSqr(target);

        if (p_190102_2_ <= d0 && this.attackTick <= 0)
        {
            this.attackTick = 100;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            for (int i = 0; i < this.timesToStrike; i++) {
                target.world.addWeatherEffect(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, false));
                if (!target.isImmuneToFire())
                {
                    target.attackEntityFrom(DamageSource.HOT_FLOOR, 3.0F);
                    target.setFire(30);
                }
            }
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return this.attacker.width * this.range * this.attacker.width * this.range + attackTarget.width;
    }
}

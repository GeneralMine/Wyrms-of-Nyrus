package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;

public class WideRangeAttackAI extends EntityAIAttackMelee {

    protected float damage, range;
    private MobEntityBase entity;
    private int attackingTicks, animDur;

    public WideRangeAttackAI(float DMG, MobEntityBase creature, double speedIn, boolean useLongMemory, float attackRange, int animationDuration) {
        super(creature, speedIn, useLongMemory);
        this.damage = DMG;
        this.entity = creature;
        this.range = attackRange;
        this.animDur = animationDuration;
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
    }

    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
    {
        double d0 = this.getAttackReachSqr(p_190102_1_);

        if (p_190102_2_ <= d0 && this.attackTick <= 0)
        {
            this.attackTick = animDur;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.attackEntityAsMob(p_190102_1_);
            this.entity.setAttack(9);
        }
        if (this.attackTick > 0) this.entity.setAttack(9);
        else this.entity.setAttack(0);
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return this.attacker.width * this.range * this.attacker.width * this.range + attackTarget.width;
    }
}

package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.DamageSources.DamageSourceRoll;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;

public class RollAttackAI extends EntityAIAttackMelee {

    private EntityWyrm wyrm;
    private int attackingTicks;
    protected SoundEvent sound;
    protected double extraDamage;

    /**
     * REQUIRES THE SPRINTING DATAPARAMETER TO BE PRESENT ALONG WITH setSprinting() METHOD.
     * Enemies that do this task will rush up to & through targets at a temporary speed boost.
     * @param wyrmIn Entity
     * @param speedIn Entity's base speed
     * @param useLongMemory
     * @param rollSpeed Entity's bonus speed
     */
    public RollAttackAI(EntityWyrm wyrmIn, double speedIn, boolean useLongMemory, double rollSpeed, double rollDMG, SoundEvent sfx) {
        super(wyrmIn, rollSpeed, useLongMemory);
        this.wyrm = wyrmIn;
        this.sound = sfx;
        this.extraDamage = rollDMG;
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.attackingTicks = 0;
        wyrm.playSound(sound,0.5F,1.0F);
    }

    public boolean shouldContinueExecuting()
    {
        if (this.attackingTicks >= 200) return false;
        else return true;
    }

    public void resetTask()
    {
        super.resetTask();
        this.wyrm.setAttack(0);
    }

    public void updateTask()
    {
        super.updateTask();
        ++this.attackingTicks;

        if (this.attackingTicks >= 1 && this.attackTick < 10)
        {
            this.wyrm.setAttack(3);
        }
        else
        {
            this.wyrm.setAttack(0);
        }
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double p_190102_2_) {
        double d0 = this.getAttackReachSqr(target);

        if (p_190102_2_ <= d0 && this.attackTick <= 0)
        {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.attackEntityAsMob(target);
            target.attackEntityFrom(DamageSourceRoll.ROLL, (float) this.extraDamage);
        }
    }
}

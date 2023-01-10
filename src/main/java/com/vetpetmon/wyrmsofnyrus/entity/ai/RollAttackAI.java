package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.handlers.WoNDamageSources;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;

public class RollAttackAI extends EntityAIAttackMelee {

    private EntityWyrm wyrm;
    private int attackingTicks;
    protected SoundEvent sound;
    protected double extraDamage;
    protected boolean didAttack = false;
    protected int numberAttacks, rollTime, maxRollTime;

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
        this.numberAttacks = 0; //1 second max of iframe-ignoring attack
        this.rollTime = 0;
        this.maxRollTime = 1000; //5 seconds of AI task time
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.attackingTicks = 0;
        wyrm.playSound(sound,0.5F,1.0F);
        this.didAttack = false;
        this.numberAttacks = 0; //1 second max of iframe-ignoring attack
        this.rollTime = 0;
        this.maxRollTime = 100; //5 seconds of AI task time
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if (this.didAttack) return false;
        return super.shouldContinueExecuting();
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

        if (this.attackingTicks >= 1 && this.attackTick < 5)
        {
            this.wyrm.setAttack(3);
        }
        else
        {
            this.wyrm.setAttack(0);
        }
    }

    @Override
    protected void checkAndPerformAttack(EntityLivingBase target, double attackerDistance) {
        double d0 = this.getAttackReachSqr(target)*2;
        this.rollTime++;
        if (attackerDistance <= d0 && this.attackTick <= 20)
        {
            this.attackTick = 0;
            target.hurtResistantTime = 0; //Ignore I-frames.
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.attackEntityAsMob(target);
            target.attackEntityFrom(WoNDamageSources.ROLL, (float) this.extraDamage);
            target.motionX = 0.0D; //Now cancel knockback
            target.motionZ = 0.0D;
            target.motionY = 0.0D;
            this.numberAttacks++;
            wyrmsofnyrus.logger.info("Biter performed attack frame.");
        }
        if (this.numberAttacks > 10 || this.rollTime > this.maxRollTime) {
            this.didAttack = true; //after 10 attack frames OR after attack time reaches maximum, cancel attack.
            wyrmsofnyrus.logger.info("Biter canceled task, numATK: " + this.numberAttacks + " and RollTime: " + this.rollTime);
        }
        wyrmsofnyrus.logger.info("Biter did make an attack, numATK: " + this.numberAttacks + " and RollTime: " + this.rollTime);
    }
}

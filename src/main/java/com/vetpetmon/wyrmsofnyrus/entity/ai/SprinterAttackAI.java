package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class SprinterAttackAI extends EntityAIAttackMelee {

    private EntityWyrm wyrm;
    private int attackingTicks;
    protected double speed;
    protected double speedOG;

    /**
     * REQUIRES THE SPRINTING DATAPARAMETER TO BE PRESENT ALONG WITH setSprinting() METHOD.
     * Enemies that do this task will rush up to & through targets at a temporary speed boost.
     * @param wyrmIn Entity
     * @param speedIn Entity's base speed
     * @param useLongMemory
     * @param sprintSpeed Entity's bonus speed
     */
    public SprinterAttackAI(EntityWyrm wyrmIn, double speedIn, boolean useLongMemory, double sprintSpeed) {
        super(wyrmIn, speedIn, useLongMemory);
        this.wyrm = wyrmIn;
        this.speedOG = speedIn;
        this.speed = speedOG + sprintSpeed;
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.attackingTicks = 0;
    }

    public void resetTask()
    {
        super.resetTask();
        this.wyrm.setSprinting(false);
    }

    public void updateTask()
    {
        super.updateTask();
        ++this.attackingTicks;

        if (this.attackingTicks >= 5 && this.attackTick < 10)
        {
            this.wyrm.setSprinting(true);
            this.wyrm.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(speed);
        }
        else
        {
            this.wyrm.setSprinting(false);
            this.wyrm.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(speedOG);
        }
    }
}

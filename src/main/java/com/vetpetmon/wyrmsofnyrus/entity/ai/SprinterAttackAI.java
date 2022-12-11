package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class SprinterAttackAI extends EntityAIAttackMelee {

    private EntityWyrm wyrm;
    private int attackingTicks;
    protected double speed;
    protected double speedOG;

    public SprinterAttackAI(EntityWyrm wyrmIn, double speedIn, boolean useLongMemory, double baseSpeed, double sprintSpeed) {
        super(wyrmIn, speedIn, useLongMemory);
        this.wyrm = wyrmIn;
        this.speedOG = baseSpeed;
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

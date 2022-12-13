package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.SoundEvent;

public class SprinterAttackAI extends EntityAIAttackMelee {

    private EntityWyrm wyrm;
    private int attackingTicks;
    protected SoundEvent sound;

    /**
     * REQUIRES THE SPRINTING DATAPARAMETER TO BE PRESENT ALONG WITH setSprinting() METHOD.
     * Enemies that do this task will rush up to & through targets at a temporary speed boost.
     * @param wyrmIn Entity
     * @param speedIn Entity's base speed
     * @param useLongMemory
     * @param sprintSpeed Entity's bonus speed
     */
    public SprinterAttackAI(EntityWyrm wyrmIn, double speedIn, boolean useLongMemory, double sprintSpeed, SoundEvent sfx) {
        super(wyrmIn, (speedIn + sprintSpeed), useLongMemory);
        this.wyrm = wyrmIn;
        this.sound = sfx;
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.attackingTicks = 0;
        wyrm.playSound(sound,0.5F,1.0F);
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
        }
        else
        {
            this.wyrm.setSprinting(false);
        }
    }
}

package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class BiteAttackAI extends EntityAIAttackMelee {

    protected float damage;
    private EntityWyrm wyrm;
    private int attackingTicks;

    // Use follyType 0 to make it just a regular bite attack.
    public BiteAttackAI(float DMG, EntityWyrm creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        this.damage = DMG;
        this.wyrm = creature;
        //this.atkTimer = Math.max((40-(stage*5)),5); // min of 4 attacks a second, max of 1 attack in 2 seconds.
    }
    public void resetTask()
    {
        super.resetTask();
        this.wyrm.setAttack(0);
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

        if (this.attackingTicks >= 1 && this.attackTick < 10)
        {
            this.wyrm.setAttack(8);
        }
        else
        {
            this.wyrm.setAttack(0);
        }
    }
}

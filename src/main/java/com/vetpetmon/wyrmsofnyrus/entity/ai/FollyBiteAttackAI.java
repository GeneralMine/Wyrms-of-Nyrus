package com.vetpetmon.wyrmsofnyrus.entity.ai;

import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class FollyBiteAttackAI extends EntityAIAttackMelee {

    protected float damage;
    private MobEntityBase wyrm;
    private int attackingTicks;

    // Use follyType 0 to make it just a regular bite attack.
    public FollyBiteAttackAI(float DMG, MobEntityBase creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        this.damage = DMG;
        this.wyrm = creature;
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

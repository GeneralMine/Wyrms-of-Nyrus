package com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITarget;

/* Collective Consciousness system
 *
 * Any entity in the CC shares one singular target
 *
 *
 */
public class Gestalt<T extends EntityLivingBase> extends EntityAITarget {


    public Gestalt(EntityCreature creature) {
        super(creature, false);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = GestaltHostMind.getKollectiveTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive() || GestaltHostMind.getLastSeen() > 300)
        {
            GestaltHostMind.setKollectiveTarget(null); // Forget it exists
            return false;
        }
        else return this.taskOwner.getAttackTarget() == null;
    }

    public void startExecuting()
    {
        if (this.taskOwner.getAttackTarget() == GestaltHostMind.getKollectiveTarget() && (this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() < 100.D)) this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(200.0D);
        else this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        if (this.taskOwner.getAttackTarget() != GestaltHostMind.getKollectiveTarget())this.taskOwner.setAttackTarget(GestaltHostMind.getKollectiveTarget());
        super.startExecuting();
    }

    @Override
    public void resetTask() {
        super.resetTask();
    }
}

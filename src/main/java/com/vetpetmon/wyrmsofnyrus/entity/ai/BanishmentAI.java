package com.vetpetmon.wyrmsofnyrus.entity.ai;


import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.advancements.Advancements;
import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;

public class BanishmentAI extends EntityAIAttackMelee {

    protected float damage, range;
    private MobEntityBase entity;
    private int attackingTicks;

    /**
     * This essentially makes a creature able to send stuff to y-level -50. Very fun to be the target of this, I must say.
     * @param DMG Damage
     * @param creature Entity that is performing the banishment
     * @param speedIn Chase speed
     * @param useLongMemory Long memory
     * @param attackRange Attack range at which to reach stuff
     */
    public BanishmentAI(float DMG, MobEntityBase creature, double speedIn, boolean useLongMemory, float attackRange) {
        super(creature, speedIn, useLongMemory);
        this.damage = DMG;
        this.entity = creature;
        this.range = attackRange;
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
            this.attackTick = 52;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            if (target instanceof EntityPlayerMP) { //BECAUSE SOME REASON, YOU NEED TO GO THROUGH EVEN MORE STEPS TO MAKE A PLAYER ENTITY TELEPORT?
                //We will be using net.minecraftforge.event.entity.living.EnderTeleportEvent() to make things shorter.
                net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(target, target.posX, target.posY, target.posZ, 0.0F);
                if (target.isRiding()) target.dismountRidingEntity(); //Let's not send horses, pigs, etc. into limbo, alright?
                target.setPositionAndUpdate(event.getTargetX(), -50, event.getTargetZ()); //Actual TP
                target.fallDistance = 0.0F;
                Advancements.grantAchievement((EntityPlayerMP) target, Advancements.howdidwegethere);
            }
            else target.setPositionAndUpdate(target.posX,-50,target.posZ); //This only works on MOBS???
            this.attacker.playSound(SoundRegistry.banishment,20,1);
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return this.attacker.width * this.range * this.attacker.width * this.range + attackTarget.width;
    }
}

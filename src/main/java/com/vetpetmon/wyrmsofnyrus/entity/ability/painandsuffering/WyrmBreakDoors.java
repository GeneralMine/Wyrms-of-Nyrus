package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.config.AI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;


public class WyrmBreakDoors extends EntityAIDoorInteract {
    private int breakingTime;
    private int previousBreakProgress = -1;
    private int TTB;

    /**
     * Normal Minecraft's BreakDoor AI is silly. Really? Hard difficulty only? Also made it customizable, different wyrms break doors more quickly, or more slowly.
     * Let's activate it for ALL difficulties. You were never safe. They know how to get in now.
     *
     * I'm sorry, Salt.
     *
     * @param entityIn Entity
     * @param timeToBreak How many ticks should we yell "OPEN UP" for? (:
     */
    public WyrmBreakDoors(EntityLiving entityIn, int timeToBreak)
    {
        super(entityIn);
        TTB = timeToBreak;
    }

    public boolean shouldExecute()
    {
        if (!super.shouldExecute())
        {
            return false;
        }
        else if (!AI.destroyBlocks || !net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entity.world, this.entity) || !this.entity.world.getBlockState(this.doorPosition).getBlock().canEntityDestroy(this.entity.world.getBlockState(this.doorPosition), this.entity.world, this.doorPosition, this.entity) || !net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this.entity, this.doorPosition, this.entity.world.getBlockState(this.doorPosition)))
        {
            return false;
        }
        else
        {
            BlockDoor blockdoor = this.doorBlock;
            return !BlockDoor.isOpen(this.entity.world, this.doorPosition);
        }
    }

    public void startExecuting()
    {
        super.startExecuting();
        this.breakingTime = 0;
    }

    public boolean shouldContinueExecuting()
    {
        double d0 = this.entity.getDistanceSq(this.doorPosition);
        boolean flag;

        if (this.breakingTime <= TTB)
        {
            BlockDoor blockdoor = this.doorBlock;

            if (!BlockDoor.isOpen(this.entity.world, this.doorPosition) && d0 < 4.0D)
            {
                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    public void resetTask()
    {
        super.resetTask();
        this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.doorPosition, -1);
    }

    public void updateTask()
    {
        super.updateTask();

        if (this.entity.getRNG().nextInt(10) == 0)
        {
            this.entity.world.playEvent(1019, this.doorPosition, 0);
        }

        ++this.breakingTime;
        int i = (int)((float)this.breakingTime / 240.0F * 10.0F);

        if (i != this.previousBreakProgress)
        {
            this.entity.world.sendBlockBreakProgress(this.entity.getEntityId(), this.doorPosition, i);
            this.previousBreakProgress = i;
        }
        // This HAD the difficulty requirement. It is now gone. Run.
        if (this.breakingTime >= TTB)
        {
            this.entity.world.setBlockToAir(this.doorPosition);
            this.entity.world.playEvent(1021, this.doorPosition, 0);
            this.entity.world.playEvent(2001, this.doorPosition, Block.getIdFromBlock(this.doorBlock));
        }
    }
}

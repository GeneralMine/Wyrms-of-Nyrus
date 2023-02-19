package com.vetpetmon.wyrmsofnyrus.entity.follies;

import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmBreakDoors;
import com.vetpetmon.wyrmsofnyrus.entity.ai.FollyBiteAttackAI;
import com.vetpetmon.wyrmsofnyrus.handlers.WoNDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityStrykeling extends EntityWyrmfolly implements IAnimatable, IAnimationTickable {
    protected int age; // currently unused
    public EntityStrykeling(World worldIn) {
        super(worldIn);
        this.age = 0;
        this.StatMap();
    }

    public void StatMap() {
        this.setStats(
                wyrmStats.strykelingfollyHP,
                wyrmStats.strykelingfollyDEF,
                wyrmStats.strykelingfollyATK,
                wyrmStats.strykelingfollySPD,
                wyrmStats.strykelingfollyKBR
        );
    }

    @Override
    protected void makeAllTargets() {
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityStryker.class, 3, 1, 1.2));
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 2, true, false, target -> !((target instanceof EntityStryker)||(target instanceof EntityStrykeling)||(target instanceof EntityCreeper))));
    }

    @Override
    public void updateLevel(){
        super.updateLevel();
        this.StatMap();
        updateAttributes();
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.StatMap();
        updateAttributes();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("age")) this.age = compound.getInteger("age");
    }

    @Override
    protected void initEntityAI() { //AYO YOUR LEAPING IS GOOFY!!! override the whole thing
        makeAllTargets();
        this.tasks.addTask(2, new wyrmBreakDoors(this, 200));
        this.tasks.addTask(1, new EntityAIWander(this, 0.45));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new FollyBiteAttackAI(this.ATK,this, 1.0D, true));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("age", this.age);
    }
    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        this.playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.05F);
        if (getAttack() == 8) return attackEntityAsMobO(entityIn,entityIn.attackEntityFrom(WoNDamageSources.causeStrykerBiteDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
        return super.attackEntityAsMob(entityIn);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (getAttack() == 8) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.strykling.movingagro"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.strykling.moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.strykling.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {return ticksExisted;}

    @Override
    public void tick() {super.onUpdate();}
}

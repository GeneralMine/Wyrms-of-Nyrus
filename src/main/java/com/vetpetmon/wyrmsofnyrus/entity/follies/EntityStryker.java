package com.vetpetmon.wyrmsofnyrus.entity.follies;

import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.WyrmBreakDoors;
import com.vetpetmon.wyrmsofnyrus.entity.ai.FollyBiteAttackAI;
import com.vetpetmon.wyrmsofnyrus.handlers.WoNDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityStryker extends EntityWyrmfolly implements IAnimatable, IAnimationTickable {
    public EntityStryker(World worldIn) {
        super(worldIn);
    }

    public void StatMap() {
        this.setStats(
                WyrmStats.strykerfollyHP,
                WyrmStats.strykerfollyDEF,
                WyrmStats.strykerfollyATK,
                WyrmStats.strykerfollySPD,
                WyrmStats.strykerfollyKBR
        );
    }

    @Override
    public void updateLevel(){
        super.updateLevel();
        this.StatMap();
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.StatMap();
    }
    @Override
    protected void initEntityAI() { //AYO YOUR LEAPING IS GOOFY!!! override the whole thing
        makeAllTargets();
        this.tasks.addTask(2, new WyrmBreakDoors(this, 200));
        this.tasks.addTask(1, new EntityAIWander(this, 0.45));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new FollyBiteAttackAI(this.ATK,this, 1.0D, true));
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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.strykling.moving"));
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

package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Client;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.ai.SprinterAttackAI;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EntityCreepedHumanoid extends EntityCreeped implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);
    public static final ResourceLocation CREEPEDHUMANOID_LOOT_TABLE = new ResourceLocation("wyrmsofnyrus", "entities/creepedhumanoid");

    public EntityCreepedHumanoid(World worldIn) {
        super(worldIn);
        this.casteType = 0;
        setSize(0.9f, 1.85f);
        experienceValue = 16;
        enablePersistence();
        setNoAI(false);
        setPotency(6);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return CREEPEDHUMANOID_LOOT_TABLE;
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.creepedhumanoid;}

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        afterAnimals();
        afterVillagers();
        afterMobs();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 0.5D, false));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.45D));
        this.tasks.addTask(1, new SprinterAttackAI(this, 1.0, true, WyrmStats.creepedhumanoidSprintSPD, SoundRegistry.creepedhumanoidroar));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(WyrmStats.creepedhumanoidHP, WyrmStats.creepedhumanoidDEF, WyrmStats.creepedhumanoidATK, WyrmStats.creepedhumanoidSPD, WyrmStats.creepedhumanoidKBR);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (isInWater() && Client.fancyAnimations) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepedhumanoid.swim"));
            else if (getAttack() == 2) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepedhumanoid.run"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepedhumanoid.walk"));
        }
        else if (isInWater() && Client.fancyAnimations) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepedhumanoid.swimidle"));
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepedhumanoid.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

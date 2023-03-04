package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ai.SprinterAttackAI;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityMyrmur extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityMyrmur(World world) {
        super(world);
        this.casteType = 3;
        setSize(0.65f, 1.45f);
        experienceValue = 12;
        enablePersistence();
        setNoAI(false);
        setPotency(4);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        isSapient();
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.55F));
        this.tasks.addTask(5, new EntityAIAvoidEntity<>(this, EntityMyrmur.class, 30, 1, 1.2));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(1, new SprinterAttackAI(this, 2.0, true, WyrmStats.myrmurSprintSPD, SoundRegistry.myrmurcharge));
        afterPlayers();
        afterInsectoids();
    }
    @Override
    protected boolean partakesInGestalt(){return false;}
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(WyrmStats.myrmurHP, WyrmStats.myrmurDEF, WyrmStats.myrmurATK, WyrmStats.myrmurSPD, WyrmStats.myrmurKBR);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(AllItems.wyrmarmorfrag, 2).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.myrmur;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds)  {
        return SoundRegistry.wyrmHissTwo;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.wyrmSteps, 0.25F, 1.05F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isProjectile()) amount *= 0.75F;
        if (source == DamageSource.GENERIC)
            return super.attackEntityFrom(source, (float) (amount*0.75));
        return super.attackEntityFrom(source, amount);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            if (getAttack() == 2) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.attacking"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.moving"));
        }
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

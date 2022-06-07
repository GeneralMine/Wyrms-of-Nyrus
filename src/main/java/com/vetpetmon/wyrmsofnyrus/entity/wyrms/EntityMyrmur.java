package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.item.wyrmArmorFragment;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityMyrmur extends EntityWyrm implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityMyrmur(World world) {
        super(world);
        this.casteType = 3;
        setSize(0.65f, 1.0f);
        experienceValue = 12;
        enablePersistence();
        setNoAI(false);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.45D, true));
        afterPlayers();
    }

    @Override
    protected void applyEntityAttributes() {
        float difficulty = (float) getInvasionDifficulty();
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(2.0d,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(6,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(1,difficulty));
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(wyrmArmorFragment.block, 2).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.deepwyrmclicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds)  {
        return SoundRegistry.wyrmHissTwo;
    }
    /*@Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }*/

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.wyrmSteps, 0.25F, 1.05F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL && Radiogenetics.immuneToFalling)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            // TODO: If it's got a target, display special animation instead of the normal moving animation.
            //if (EntityAIAttackMelee.shouldContinueExecuting()) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.attacking"));
            /*else */event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.idle"));
        }
        return PlayState.CONTINUE;
    }

}

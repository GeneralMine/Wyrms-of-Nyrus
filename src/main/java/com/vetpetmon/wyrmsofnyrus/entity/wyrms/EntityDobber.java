package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.FlyingMobAI;
import com.vetpetmon.wyrmsofnyrus.item.ItemCreepshard;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class EntityDobber extends EntityWyrm implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityDobber(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.5f, 0.5f);
        experienceValue = 3;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new EntityDobber.DobberMoveHelper(this);
        enablePersistence();
        setNoAI(false);
    }

    static class DobberMoveHelper extends EntityMoveHelper
    {
        private final EntityDobber parentEntity;
        private int courseChangeCooldown;

        public DobberMoveHelper(EntityDobber WyrmProber)
        {
            super(WyrmProber);
            this.parentEntity = WyrmProber;
            if (AI.performanceAIMode) {
                courseChangeCooldown = 160;
            }
            else {
                courseChangeCooldown = 80;
            }
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == Action.MOVE_TO)
            {
                double d0 = this.posX - this.parentEntity.posX;
                double d1 = this.posY - this.parentEntity.posY;
                double d2 = this.posZ - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.courseChangeCooldown-- <= 0)
                {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    d3 = MathHelper.sqrt(d3);

                    if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
                    {
                        this.parentEntity.motionX += d0 / d3 * 0.1D;
                        this.parentEntity.motionY += d1 / d3 * 0.1D;
                        this.parentEntity.motionZ += d2 / d3 * 0.1D;
                    }
                    else
                    {
                        this.action = Action.WAIT;
                    }
                }
            }
        }

        private boolean isNotColliding(double x, double y, double z, double p_179926_7_)
        {
            double d0 = (x - this.parentEntity.posX) / p_179926_7_;
            double d1 = (y - this.parentEntity.posY) / p_179926_7_;
            double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
            AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

            for (int i = 1; (double)i < p_179926_7_; ++i)
            {
                axisalignedbb = axisalignedbb.offset(d0, d1, d2);

                if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty())
                {
                    return false;
                }
            }

            return true;
        }
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.9D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5D + (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty / 2));

        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2D * (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty));
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.5D, false));
        this.tasks.addTask(4, new FlyingMobAI(this, 18.75, 100));
        this.makeAllTargets();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    protected Item getDropItem() {
        return new ItemStack(ItemCreepshard.block, 1).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmClicks;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
    }
    /*@Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }*/

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 10F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.swarmwyrm.flying"));

        return PlayState.CONTINUE;
    }

}

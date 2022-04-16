package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;

import com.google.common.base.Predicate;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.FlyingMobAI;
import com.vetpetmon.wyrmsofnyrus.item.ItemCreepshard;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.*;
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
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityWyrmProber extends EntityWyrm implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    public EntityWyrmProber(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.5f, 0.5f);
        experienceValue = 1;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new EntityWyrmProber.WyrmProberMoveHelper(this);
    }

    static class WyrmProberMoveHelper extends EntityMoveHelper
    {
        private final EntityWyrmProber parentEntity;
        private int courseChangeCooldown = 40;

        public WyrmProberMoveHelper(EntityWyrmProber WyrmProber)
        {
            super(WyrmProber);
            this.parentEntity = WyrmProber;
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
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
                        this.action = EntityMoveHelper.Action.WAIT;
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
    protected void initEntityAI() {
        super.initEntityAI();
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityLiving.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayerMP.class, false, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, false, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, false, false, new Predicate<EntityMob>() {
            public boolean apply(EntityMob target) {
                return !((target instanceof EntityCreeper) || (target instanceof EntityWyrm));
            }
        }));
        this.tasks.addTask(5, new FlyingMobAI(this, 4.75, 100));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D * (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D * (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty));
        //this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        //this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(5 * (wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty));
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmprober.flying"));

        return PlayState.CONTINUE;
    }

}

package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.FlyingMobAI;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.item.wyrmArmorFragment;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
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
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;


public class EntityWyrmWarrior extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);
    //private boolean isCharging;
    public EntityWyrmWarrior(World world) {
        super(world);
        this.casteType = 2;
        setSize(1.0f, 2.25f);
        experienceValue = 3;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new EntityWyrmWarrior.WyrmWarriorMoveHelper(this);
        enablePersistence();
        setNoAI(false);
    }

    class WyrmWarriorMoveHelper extends EntityMoveHelper
    {
        private final EntityWyrmWarrior parentEntity;
        private double speedW;

        public WyrmWarriorMoveHelper(EntityWyrmWarrior WyrmWarrior)
        {
            super(WyrmWarrior);
            this.parentEntity = WyrmWarrior;
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == Action.MOVE_TO)
            {
                double d0 = this.posX - EntityWyrmWarrior.this.posX;
                double d1 = this.posY - EntityWyrmWarrior.this.posY;
                double d2 = this.posZ - EntityWyrmWarrior.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double)MathHelper.sqrt(d3);

                if (d3 < EntityWyrmWarrior.this.getEntityBoundingBox().getAverageEdgeLength())
                {
                    this.action = Action.WAIT;
                    EntityWyrmWarrior.this.motionX *= 0.5D;
                    EntityWyrmWarrior.this.motionY *= 1.5D;
                    EntityWyrmWarrior.this.motionZ *= 0.5D;
                }
                else
                {
                    EntityWyrmWarrior.this.motionX += d0 / d3 * 0.05D * this.speed;
                    EntityWyrmWarrior.this.motionY += d1 / d3 * 0.1D * this.speed;
                    EntityWyrmWarrior.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityWyrmWarrior.this.getAttackTarget() == null)
                    {
                        EntityWyrmWarrior.this.rotationYaw = -((float)MathHelper.atan2(EntityWyrmWarrior.this.motionX, EntityWyrmWarrior.this.motionZ)) * (180F / (float)Math.PI);
                        EntityWyrmWarrior.this.renderYawOffset = EntityWyrmWarrior.this.rotationYaw;
                    }
                    else
                    {
                        double d4 = EntityWyrmWarrior.this.getAttackTarget().posX - EntityWyrmWarrior.this.posX;
                        double d5 = EntityWyrmWarrior.this.getAttackTarget().posZ - EntityWyrmWarrior.this.posZ;
                        EntityWyrmWarrior.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        EntityWyrmWarrior.this.renderYawOffset = EntityWyrmWarrior.this.rotationYaw;
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
        float difficulty = (float) (getInvasionDifficulty() * evoPoints.evoMilestone(world));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(4,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(6,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(12,difficulty));
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.05D, false));
        this.tasks.addTask(4, new FlyingMobAI(this, 6.05, 100));
        this.afterPlayers(true);
        this.afterVillagers();
        this.afterAnimals();
        this.afterMobs();
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,7);
    }
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
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
        return new ItemStack(wyrmArmorFragment.block, 2).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.wyrmroars;
    }
    @Override
    protected SoundEvent getFallSound(int heightIn) {return null;}
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        return super.attackEntityFrom(source, amount);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.warriorwyrm.moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.warriorwyrm.idle"));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

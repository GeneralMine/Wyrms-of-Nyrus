package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;

import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.AIProberAttack;
import com.vetpetmon.wyrmsofnyrus.entity.ability.FlyingMobAI;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.BreakGlass;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.invasion.invasionPoints;
import com.vetpetmon.wyrmsofnyrus.item.ItemCreepshard;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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


public class EntityWyrmProber extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private int chanceToBreak;
    private int proberTimer;
    private int probingpoints;
    //private boolean isCharging;
    public EntityWyrmProber(World world) {
        super(world);
        this.casteType = 2;
        setSize(0.5f, 0.5f);
        experienceValue = 3;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new EntityWyrmProber.WyrmProberMoveHelper(this);
        enablePersistence();
        setNoAI(false);
        this.proberTimer = 2500; // Lives for ~2 two minutes
        this.probingpoints = 0;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}

    class WyrmProberMoveHelper extends EntityMoveHelper
    {
        private final EntityWyrmProber parentEntity;
        private double speedW;

        public WyrmProberMoveHelper(EntityWyrmProber WyrmProber)
        {
            super(WyrmProber);
            this.parentEntity = WyrmProber;
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = this.posX - EntityWyrmProber.this.posX;
                double d1 = this.posY - EntityWyrmProber.this.posY;
                double d2 = this.posZ - EntityWyrmProber.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double)MathHelper.sqrt(d3);

                if (d3 < EntityWyrmProber.this.getEntityBoundingBox().getAverageEdgeLength())
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityWyrmProber.this.motionX *= 0.5D;
                    EntityWyrmProber.this.motionY *= 0.5D;
                    EntityWyrmProber.this.motionZ *= 0.5D;
                }
                else
                {
                    EntityWyrmProber.this.motionX += d0 / d3 * 0.05D * this.speed;
                    EntityWyrmProber.this.motionY += d1 / d3 * 0.05D * this.speed;
                    EntityWyrmProber.this.motionZ += d2 / d3 * 0.05D * this.speed;

                    if (EntityWyrmProber.this.getAttackTarget() == null)
                    {
                        EntityWyrmProber.this.rotationYaw = -((float)MathHelper.atan2(EntityWyrmProber.this.motionX, EntityWyrmProber.this.motionZ)) * (180F / (float)Math.PI);
                        EntityWyrmProber.this.renderYawOffset = EntityWyrmProber.this.rotationYaw;
                    }
                    else
                    {
                        double d4 = EntityWyrmProber.this.getAttackTarget().posX - EntityWyrmProber.this.posX;
                        double d5 = EntityWyrmProber.this.getAttackTarget().posZ - EntityWyrmProber.this.posZ;
                        EntityWyrmProber.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        EntityWyrmProber.this.renderYawOffset = EntityWyrmProber.this.rotationYaw;
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
        if (Invasion.probingEnabled) {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(wyrmStats.proberSPD+0.25D);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(wyrmStats.proberSPD);
        }
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(wyrmStats.proberDEF,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(wyrmStats.proberATK,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(wyrmStats.proberHP,difficulty));
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        //this.tasks.addTask(4, new AIFlyingMobCharge(2.0));
        // Bypass configs entirely if probing is enabled, else make probers respect the optimizations players want.
        if (Invasion.probingEnabled) {
            this.tasks.addTask(2, new AIProberAttack(this, 1.5D, true));
            this.tasks.addTask(4, new FlyingMobAI(this, 7.75, 256, 10));
            this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
            this.afterPlayers(false);
            this.afterVillagers();
            this.afterAnimals();
            this.afterMobs();
        }
        else {
            this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.05D, false));
            this.tasks.addTask(4, new FlyingMobAI(this, 6.05, 100));
            this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
            this.makeAllTargets();
        }
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        if (Invasion.probingEnabled) {this.probingpoints += 5;}
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean result = super.attackEntityAsMob(entityIn);
        if (result) this.probingpoints += 2;
        return result;
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,1);
    }
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.proberTimer <= 0){
            invasionPoints.add(world, this.probingpoints);
            this.setDead();
        }
        chanceToBreak = RNG.dBase(20);
        if (AI.destroyBlocks && Invasion.probingEnabled && (chanceToBreak == 2)) BreakGlass.CheckAndBreak(world,getPosition(),3);
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

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("proberTimer"))
        {
            this.proberTimer = compound.getInteger("proberTimer");
        }

        if (compound.hasKey("probingpoints"))
        {
            this.probingpoints = compound.getInteger("probingpoints");
        }

        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        this.srpcothimmunity = 0;

        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
        compound.setInteger("proberTimer", this.proberTimer);
        compound.setInteger("probingpoints", this.probingpoints);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmprober.Moving"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wyrmprober.flying"));
        }

        return PlayState.CONTINUE;
    }

}

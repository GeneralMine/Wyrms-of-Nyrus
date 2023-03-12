package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrmFlying;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.BreakGlass;
import com.vetpetmon.wyrmsofnyrus.entity.ai.ProberAttackAI;
import com.vetpetmon.wyrmsofnyrus.entity.ai.FlyingMobAI;
import com.vetpetmon.wyrmsofnyrus.invasion.InvasionPoints;
import com.vetpetmon.wyrmsofnyrus.item.AllItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityMoveHelper;
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
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class EntityWyrmProber extends EntityWyrmFlying implements IAnimatable, IAnimationTickable {
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
        setPotency(1);
        this.setAnimationNames(new String[]{"wyrmprober.Moving","wyrmprober.flying"});
    }
    @Override
    protected boolean canEnrage(){return false;}
    @Override
    protected boolean partakesInGestalt(){return false;}
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
        this.setStats(WyrmStats.proberHP, WyrmStats.proberDEF, WyrmStats.proberATK, WyrmStats.proberSPD,1.0F);
        if (Invasion.probingEnabled) {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(WyrmStats.proberSPD+0.25D);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        }
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        simpleAI();
        //this.tasks.addTask(4, new AIFlyingMobCharge(2.0));
        // Bypass configs entirely if probing is enabled, else make probers respect the optimizations players want.
        if (Invasion.probingEnabled) {
            this.tasks.addTask(2, new ProberAttackAI(this, 1.5D, true));
            this.tasks.addTask(4, new FlyingMobAI(this, 7.75, 256, 10));
            this.afterPlayers(false, true);
            this.afterVillagers();
            this.afterAnimals();
            this.afterMobs();
        }
        else {
            this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.05D, false));
            this.tasks.addTask(4, new FlyingMobAI(this, 6.05, 100));
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
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.proberTimer <= 0){
            InvasionPoints.add(world, this.probingpoints);
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
    protected Item getDropItem() {
        return new ItemStack(AllItems.creepshard, 1).getItem();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.proberidle;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
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
            event.getController().setAnimation(getAnimation(0));
        }
        else {
            event.getController().setAnimation(getAnimation(1));
        }

        return PlayState.CONTINUE;
    }

}

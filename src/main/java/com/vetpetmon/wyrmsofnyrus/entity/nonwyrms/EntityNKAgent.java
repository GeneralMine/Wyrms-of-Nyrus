package com.vetpetmon.wyrmsofnyrus.entity.nonwyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import com.vetpetmon.wyrmsofnyrus.entity.ai.BanishmentAI;
import com.vetpetmon.wyrmsofnyrus.entity.ai.SmiteAI;
import com.vetpetmon.wyrmsofnyrus.entity.ai.WideRangeAttackAI;
import com.vetpetmon.wyrmsofnyrus.locallib.DifficultyStats;
import com.vetpetmon.wyrmsofnyrus.locallib.ai.EntityAIFlierMob;
import com.vetpetmon.wyrmsofnyrus.locallib.ai.movehelpers.FlierMoveHelperGhastlike;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityNKAgent extends MobEntityBase implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private int particleCooldown;
    public EntityNKAgent(World worldIn) {
        super(worldIn);
        enablePersistence();
        setSize(0.8f, 1.8f);
        experienceValue = 5;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new FlierMoveHelperGhastlike(this, 1, 0.5, 0.5); //No cooldown = most lag, but smoothest movement. Realistically, only one agent will be in the world at a time.
        this.setAnimationNames(new String[]{"nkagent.idle","nkagent.idleAgro","nkagent.move","nkagent.attack","nkagent.magic"});
        this.isImmuneToFire = true;
    }

    @Override
    protected void afterWyrms() {this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityWyrm.class, false, false));}

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100000.0);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1000000); //basically just instakills things
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(1, new WideRangeAttackAI(1000000,this, 1.15D, false,4.0F, 48));
        this.tasks.addTask(3, new BanishmentAI(1000000,this, 1.15D, false,6.0F));
        this.tasks.addTask(2, new SmiteAI(this, 1.15D, false,6.0F, 3));
        this.tasks.addTask(4, new EntityAIFlierMob(this, 1.0, 200));
        this.tasks.addTask(5, new EntityAIWanderAvoidWaterFlying(this, 0.15D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.afterWyrms();
    }

    // Sounds
    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.nkagentidle;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        this.setHealth(getMaxHealth());
        Entity entity = source.getTrueSource();
        if (entity instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase) entity);
            DifficultyStats.applyPotionEffect(entity, MobEffects.LEVITATION, 1, 50, true);
            DifficultyStats.applyPotionEffect(entity, MobEffects.GLOWING, 1, 1, true);
            DifficultyStats.applyPotionEffect(entity, MobEffects.WITHER, 40, 1, false);
        }
        if (source == DamageSource.FALL || (getAttack()>=9 )) return false;
        return super.attackEntityFrom(source,0.000001F);
    }

    public boolean isPotionApplicable(final PotionEffect potion) {return false;} //Total potion effect immunity.

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 3F, this::predicate));
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }
    public void onLivingUpdate()
    {
        particleCooldown--;
        if((getAttack()>=9) && particleCooldown <=0){
            particleCooldown = 1;
            this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
        else if(particleCooldown <=0) {
            particleCooldown = 5;
            this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
        }
        super.onLivingUpdate();
    }

    @Override
    public AnimationFactory getFactory() {return this.factory;}

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (getAttack() == 9) {
            event.getController().setAnimation(getAnimation(3));
            return PlayState.CONTINUE;
        }
        if (getAttack() == 10 || getAttack() == 11) {
            event.getController().setAnimation(getAnimation(4));
            return PlayState.CONTINUE;
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(getAnimation(2));
        }
        else {
            if (this.getAttackTarget() != null && !this.getAttackTarget().isDead) event.getController().setAnimation(getAnimation(1));
            else event.getController().setAnimation(getAnimation(0));
        }

        return PlayState.CONTINUE;
    }
}

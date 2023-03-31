package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.locallib.DifficultyStats;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
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

import javax.annotation.Nullable;

public class EntityCrawler extends EntityCreeped implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);
    public static final ResourceLocation BITER_LOOT_TABLE = new ResourceLocation("wyrmsofnyrus", "entities/biter");
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityCrawler.class, DataSerializers.BYTE);

    public EntityCrawler(World worldIn) {
        super(worldIn);
        this.casteType = 0;
        setSize(0.65f, 0.68f);
        experienceValue = 5;
        enablePersistence();
        setNoAI(false);
        setPotency(8);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return BITER_LOOT_TABLE;
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1F, this::predicate));
    }
    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.biter;}

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    public Byte getClimbing() {
        return this.dataManager.get(CLIMBING);
    }

    public double getMountedYOffset()
    {
        return this.height * 0.5F;
    }

    public boolean isBesideClimbableBlock()
    {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing) b0 = (byte)(b0 | 1);
        else b0 = (byte)(b0 & -2);

        this.dataManager.set(CLIMBING, b0);
    }

    static class AICrawlerAttack extends EntityAIAttackMelee
    {
        public AICrawlerAttack(EntityCrawler spider)
        {
            super(spider, 1.5D, true);
        }

        public boolean shouldContinueExecuting()
        {
            return super.shouldContinueExecuting();
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget) { return 4.0F + attackTarget.width;}
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateClimber(this,worldIn);
    }
    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        afterPlayers(true);
        afterAnimals();
        afterVillagers();
        afterMobs();
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.6F));
        this.tasks.addTask(4, new AICrawlerAttack(this));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.5D));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
            if (this.dataManager.get(CLIMBING) != 0) this.motionY = 0.75;
        }
    }

    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    public void setInWeb()
    {
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        // Give them the ability to inflict poison
        if (entityIn instanceof EntityLivingBase) {
            DifficultyStats.applyPotionEffect(entityIn, MobEffects.POISON, 20, WyrmStats.crawlerPoisonStrength, true);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(WyrmStats.crawlerHP, WyrmStats.crawlerDEF, WyrmStats.crawlerATK, WyrmStats.crawlerSPD, WyrmStats.crawlerKBR);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (this.dataManager.get(CLIMBING) != 0)
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crawler.jumpcrawl"));
        else if (event.isMoving()) {
            if (this.getAttackTarget() == null) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crawler.move"));
            else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crawler.chase"));
        }
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.crawler.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL) return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

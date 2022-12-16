package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

public class EntityMyrmur extends EntityWyrm implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> SPRINTING = EntityDataManager.<Boolean>createKey(EntityMyrmur.class, DataSerializers.BOOLEAN);

    public EntityMyrmur(World world) {
        super(world);
        this.casteType = 3;
        setSize(0.65f, 1.45f);
        experienceValue = 12;
        enablePersistence();
        setNoAI(false);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(SPRINTING, Boolean.valueOf(false));
    }

    public void setSprinting(boolean sprinting)
    {
        this.getDataManager().set(SPRINTING, Boolean.valueOf(sprinting));
    }

    @SideOnly(Side.CLIENT)
    public boolean isSprinting()
    {
        return this.getDataManager().get(SPRINTING).booleanValue();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        isSapient();
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.55F));
        this.tasks.addTask(5, new EntityAIAvoidEntity(this, EntityMyrmur.class, 30, 1, 1.2));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(1, new SprinterAttackAI(this, 2.0, true, wyrmStats.myrmurSprintSPD, SoundRegistry.myrmurcharge));
        afterPlayers();
        afterInsectoids();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(wyrmStats.myrmurHP,wyrmStats.myrmurDEF,wyrmStats.myrmurATK, wyrmStats.myrmurSPD,wyrmStats.myrmurKBR);
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
    /*@Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }*/

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundRegistry.wyrmSteps, 0.25F, 1.05F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isProjectile()) amount *= 0.75F;
        if (source == DamageSource.FALL && Radiogenetics.immuneToFalling)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        if (source == DamageSource.GENERIC)
            return super.attackEntityFrom(source, (float) (amount*0.75));
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,3);
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            // TODO: If it's got a target, display special animation instead of the normal moving animation.
            if (isSprinting()) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.attacking"));
            /*else */
            //if (hasAttackTarget()) event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.myrmurwyrm.attacking"));
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

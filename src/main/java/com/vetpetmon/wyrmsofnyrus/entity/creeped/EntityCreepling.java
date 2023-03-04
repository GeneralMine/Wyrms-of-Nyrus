package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.ability.CreepedEvents;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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

public class EntityCreepling extends EntityCreeped implements IAnimatable, IAnimationTickable {
    private AnimationFactory factory = new AnimationFactory(this);
    public static final ResourceLocation BITER_LOOT_TABLE = new ResourceLocation("wyrmsofnyrus", "entities/biter");
    private int timer;
    public EntityCreepling(World worldIn) {
        super(worldIn);
        this.casteType = 0;
        setSize(0.4f, 0.6f);
        experienceValue = 5;
        enablePersistence();
        setNoAI(false);
        this.timer = 3600; // Lives for ~3 minutes
        setPotency(1.25);
    }
    @Override
    public void onLivingUpdate(){
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.timer <= 0 && isGrounded()){
            CreepedEvents.creepBlockBelow(this);
            this.setDead();
        }
    }


    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return BITER_LOOT_TABLE;
    }
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }
    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.biter;}

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        afterPlayers();
        afterAnimals();
        afterVillagers();
        afterMobs();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.95D, false));
        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.75D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(WyrmStats.creeplingHP, WyrmStats.creeplingDEF, WyrmStats.creeplingATK, WyrmStats.creeplingSPD, WyrmStats.creeplingKBR);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("timer")) this.timer = compound.getInteger("timer");
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("timer", this.timer);
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepling.move"));
        }
        else event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepling.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}

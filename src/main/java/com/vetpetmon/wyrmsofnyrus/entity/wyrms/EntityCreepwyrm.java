package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashMap;
import java.util.Map;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.creepTheLands.*;
import static com.vetpetmon.wyrmsofnyrus.entity.ability.staysStill.*;

public class EntityCreepwyrm extends EntityWyrm implements IAnimatable{
    private AnimationFactory factory = new AnimationFactory(this);
    private int timeUntilNextCreep;
    public EntityCreepwyrm(World world) {
        super(world);
        this.casteType = 0;
        setSize(3f, 5f);
        experienceValue = 30;
        enablePersistence();
        setNoAI(false);
        this.timeUntilNextCreep = 100;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
            return super.attackEntityFrom(source, (amount/2));
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void initEntityAI() {
        afterPlayers();
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5D, false));
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        float difficulty = (float) getInvasionDifficulty();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(4,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(35,difficulty));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2D);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        /*int x = (int) this.posX;
        int y = (int) this.posY;
        int z = (int) this.posZ;
        Entity entity = this;
        {
            Map<String, Object> $_d = new HashMap<>();
            $_d.put("entity", entity);
            $_d.put("x", x);
            $_d.put("y", y);
            $_d.put("z", z);
            $_d.put("world", world);
            staysStill($_d);
        }*/

        if (!this.world.isRemote && --this.timeUntilNextCreep <= 0)
        {
            creepTheLands(getPosition(),this.world);
            this.timeUntilNextCreep = 100;
        }
    }

    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.creepSpread;}

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepwyrm.idle"));
        return PlayState.CONTINUE;
    }
}

package com.vetpetmon.wyrmsofnyrus.entity;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

// Create shared code across all mobs
public abstract class MobEntityBase extends EntityMob implements IAnimatable, IMob {

    private static final DataParameter<Integer> ATTACKID = EntityDataManager.createKey(MobEntityBase.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(MobEntityBase.class, DataSerializers.BOOLEAN);
    // Animation and AI util
    public void setAttack(int attackID)
    {
        this.getDataManager().set(ATTACKID, attackID);
    }
    public int getAttack()
    {
        return this.getDataManager().get(ATTACKID);
    }
    private String[] animationNames;

    public void setAnimationNames(String[] animationArray) {
        this.animationNames = animationArray;
    }

    public String[] getAnimationNames() {
        return animationNames;
    }

    public Boolean getHasTarget() {
        return this.getDataManager().get(HAS_TARGET);
    }
    public void getHasTarget(boolean state)
    {
        this.getDataManager().set(HAS_TARGET, state);
    }

    public AnimationBuilder getAnimation(int index){
        String[] anims = getAnimationNames();
        return new AnimationBuilder().addAnimation("animation." + anims[index]);
    }

    public MobEntityBase(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKID, 0);
        this.dataManager.register(HAS_TARGET, false);
    }
    //Override this in drop pods and such.
    protected boolean canEnrage(){return true;}

    // Targetting
    protected void afterWyrms() {this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityWyrm.class, true, false));}
}

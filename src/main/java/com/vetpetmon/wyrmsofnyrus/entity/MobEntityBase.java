package com.vetpetmon.wyrmsofnyrus.entity;

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
    }
}

package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.CreepedEvents;
import com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt.GestaltHostMind;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public abstract class EntityCreeped extends EntityWyrm {

    public EntityCreeped(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        CreepedEvents.convertKill(entity,this);
        if (AI.maxInfamyDoublesCreepedSpawns && GestaltHostMind.infamyIsMaxed) CreepedEvents.convertKill(entity,this);
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    }
    @Override
    protected boolean canEnrage(){return false;}
    @Override
    protected boolean partakesInGestalt(){return true;}
}

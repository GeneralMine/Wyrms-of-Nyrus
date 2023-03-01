package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.CreepedEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract class EntityCreeped extends EntityWyrm {

    public EntityCreeped(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        CreepedEvents.convertKill(entity,this);
    }
}

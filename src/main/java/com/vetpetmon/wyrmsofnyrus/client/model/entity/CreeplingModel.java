package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepling;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class CreeplingModel extends AnimatedTickingGeoModel<EntityCreepling>
{
    @Override
    public ResourceLocation getModelLocation(EntityCreepling EntityCreepling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/creepling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCreepling EntityCreepling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/creepling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCreepling EntityCreepling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/creepling.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityCreepling entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        EntityLivingBase entityIn = entity;
    }
}

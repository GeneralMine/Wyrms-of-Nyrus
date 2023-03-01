package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCallousPod;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CallousPodModel extends AnimatedGeoModel<EntityCallousPod>
{
    @Override
    public ResourceLocation getModelLocation(EntityCallousPod EntityCallousPod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/callouspod.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCallousPod EntityCallousPod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/callouspod.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCallousPod EntityCallousPod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/callouspod.animation.json");
    }
}

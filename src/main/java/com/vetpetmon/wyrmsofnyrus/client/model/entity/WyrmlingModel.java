package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmlingModel extends AnimatedGeoModel<EntityWyrmling>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmling entityWyrmling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/wyrmling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmling entityWyrmling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/wyrmling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmling entityWyrmling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/wyrmlingmodel.animation.json");
    }
}

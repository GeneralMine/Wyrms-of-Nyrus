package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmlingModel extends AnimatedGeoModel<EntityWyrmling>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmling entityWyrmling)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/wyrmling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmling entityWyrmling)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/wyrmling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmling entityWyrmling)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/wyrmlingmodel.animation.json");
    }
}

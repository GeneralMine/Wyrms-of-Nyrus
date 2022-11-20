package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HexePodModel extends AnimatedGeoModel<EntityHexePod>
{
    @Override
    public ResourceLocation getModelLocation(EntityHexePod entityHexePod)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/hexepod.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityHexePod entityHexePod)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/hexepod.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityHexePod entityHexePod)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/hexepod.animation.json");
    }
}

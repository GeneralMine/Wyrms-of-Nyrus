package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HexePodModel extends AnimatedGeoModel<EntityHexePod>
{
    @Override
    public ResourceLocation getModelLocation(EntityHexePod entityHexePod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/hexepod.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityHexePod entityHexePod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/hexepod.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityHexePod entityHexePod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/hexepod.animation.json");
    }
}

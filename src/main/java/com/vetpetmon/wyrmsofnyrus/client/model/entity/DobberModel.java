package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityDobber;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DobberModel extends AnimatedGeoModel<EntityDobber>
{
    @Override
    public ResourceLocation getModelLocation(EntityDobber entityDobber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/dobberwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityDobber entityDobber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/swarmtex.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityDobber entityDobber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/dobberwyrm.animation.json");
    }
}

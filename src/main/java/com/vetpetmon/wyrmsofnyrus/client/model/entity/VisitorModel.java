package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VisitorModel extends AnimatedGeoModel<EntityTheVisitor>
{
    @Override
    public ResourceLocation getModelLocation(EntityTheVisitor EntityTheVisitor)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/NyrusVisitor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTheVisitor EntityTheVisitor)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/NyrusVisitor.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityTheVisitor EntityTheVisitor)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/NyrusVisitor.animation.json");
    }
}

package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VisitorModel extends AnimatedGeoModel<EntityTheVisitor>
{
    @Override
    public ResourceLocation getModelLocation(EntityTheVisitor EntityTheVisitor)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/nyrusvisitor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityTheVisitor EntityTheVisitor)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/nyrusvisitor.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityTheVisitor EntityTheVisitor)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/visitor.animation.json");
    }
}

package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.follies.EntityStrykeling;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class StrykelingModel extends AnimatedTickingGeoModel<EntityStrykeling>
{
    @Override
    public ResourceLocation getModelLocation(EntityStrykeling EntityStrykeling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/strykling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStrykeling EntityStrykeling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/strykling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStrykeling EntityStrykeling)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/strykling.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityStrykeling entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        EntityLivingBase entityIn = entity;
    }
}

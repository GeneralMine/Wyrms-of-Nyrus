package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.follies.EntityStrykeling;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class StrykelingModel extends AnimatedTickingGeoModel<EntityStrykeling>
{
    @Override
    public ResourceLocation getModelLocation(EntityStrykeling EntityStrykeling)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/strykling.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStrykeling EntityStrykeling)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/strykling.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityStrykeling EntityStrykeling)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/strykling.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityStrykeling entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        EntityLivingBase entityIn = entity;
    }
}

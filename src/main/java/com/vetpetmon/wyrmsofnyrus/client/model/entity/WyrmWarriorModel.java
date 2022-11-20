package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarrior;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyrmWarriorModel extends AnimatedTickingGeoModel<EntityWyrmWarrior>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmWarrior EntityWyrmWarrior)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/warriorwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmWarrior EntityWyrmWarrior)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/warriorwyrm.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmWarrior EntityWyrmWarrior)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/warriorwyrm.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityWyrmWarrior entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}

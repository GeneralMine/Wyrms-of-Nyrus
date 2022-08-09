package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarriorTainted;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyrmWarriorTaintedModel extends AnimatedTickingGeoModel<EntityWyrmWarriorTainted>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmWarriorTainted EntityWyrmWarriorTainted)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/warriorwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmWarriorTainted EntityWyrmWarriorTainted)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/warriorwyrmt.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmWarriorTainted EntityWyrmWarriorTainted)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/warriorwyrm.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityWyrmWarriorTainted entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}

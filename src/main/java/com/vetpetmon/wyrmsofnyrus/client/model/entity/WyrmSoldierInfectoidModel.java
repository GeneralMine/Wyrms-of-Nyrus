package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldierInfectoid;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyrmSoldierInfectoidModel extends AnimatedTickingGeoModel<EntityWyrmSoldierInfectoid>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmSoldierInfectoid EntityWyrmSoldierInfectoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/infectoidsoldierwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmSoldierInfectoid EntityWyrmSoldierInfectoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/infectoidsoldierwyrm.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmSoldierInfectoid EntityWyrmSoldierInfectoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/infectoidsoldierwyrm.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityWyrmSoldierInfectoid entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}

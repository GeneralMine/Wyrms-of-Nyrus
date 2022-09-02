package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmProber;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarrior;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyrmProberModel extends AnimatedTickingGeoModel<EntityWyrmProber>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmProber EntityWyrmProber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/wyrmprober.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmProber EntityWyrmProber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/wyrmprobermodel.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmProber EntityWyrmProber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/wyrmprober.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityWyrmProber entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("Body");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        body.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        body.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}

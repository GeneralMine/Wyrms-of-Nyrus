package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCrawler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CrawlerModel extends AnimatedTickingGeoModel<EntityCrawler>
{
    @Override
    public ResourceLocation getModelLocation(EntityCrawler EntityCrawler)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/crawler.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCrawler EntityCrawler)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/crawler.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCrawler EntityCrawler)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/crawler.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityCrawler entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone body = this.getAnimationProcessor().getBone("bone");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
        if (entity.getClimbing() == (byte) 1)body.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}

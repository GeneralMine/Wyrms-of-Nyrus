package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.CrawlerModel;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCrawler;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrawlerGeoRenderer extends GeoEntityRenderer<EntityCrawler> {
    public CrawlerGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new CrawlerModel());
    }
}

package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.BiterModel;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityBiter;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BiterGeoRenderer extends GeoEntityRenderer<EntityBiter> {
    public BiterGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new BiterModel());
    }
}

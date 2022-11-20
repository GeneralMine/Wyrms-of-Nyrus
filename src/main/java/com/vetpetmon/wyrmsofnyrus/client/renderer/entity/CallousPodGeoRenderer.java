package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.CallousPodModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCallousPod;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CallousPodGeoRenderer extends GeoEntityRenderer<EntityCallousPod> {
    public CallousPodGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new CallousPodModel());
    }
}

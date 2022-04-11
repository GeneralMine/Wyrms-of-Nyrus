package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.VisitorModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityTheVisitor;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TheVisitorGeoRenderer extends GeoEntityRenderer<EntityTheVisitor> {
    public TheVisitorGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new VisitorModel());
    }
}

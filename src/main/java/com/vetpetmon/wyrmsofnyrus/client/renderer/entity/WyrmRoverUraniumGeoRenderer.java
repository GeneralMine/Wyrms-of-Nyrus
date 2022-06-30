package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmRoverUraniumModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmRoverUranium;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmRoverUraniumGeoRenderer extends GeoEntityRenderer<EntityWyrmRoverUranium> {
    public WyrmRoverUraniumGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmRoverUraniumModel());
    }
}

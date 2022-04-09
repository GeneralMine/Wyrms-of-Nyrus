package com.vetpetmon.wyrmsofnyrus.client.renderer;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.HexePodModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HexePodGeoRenderer extends GeoEntityRenderer<EntityHexePod> {
    public HexePodGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new HexePodModel());
    }
}

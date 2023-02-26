package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.NKAgentModel;
import com.vetpetmon.wyrmsofnyrus.entity.nonwyrms.EntityNKAgent;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NKAgentGeoRenderer extends GeoEntityRenderer<EntityNKAgent> {
    public NKAgentGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new NKAgentModel());
    }
}

package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.DobberModel;
import com.vetpetmon.wyrmsofnyrus.client.model.entity.HexePodModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityDobber;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DobberGeoRenderer extends GeoEntityRenderer<EntityDobber> {
    public DobberGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new DobberModel());
    }
}

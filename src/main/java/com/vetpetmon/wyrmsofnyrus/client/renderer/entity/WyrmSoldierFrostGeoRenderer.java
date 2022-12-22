package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmSoldierFrostModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldierfrost;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmSoldierFrostGeoRenderer extends GeoEntityRenderer<EntityWyrmSoldierfrost> {
    public WyrmSoldierFrostGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmSoldierFrostModel());
    }
}

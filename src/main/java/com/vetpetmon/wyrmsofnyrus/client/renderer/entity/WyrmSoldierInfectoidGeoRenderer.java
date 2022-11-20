package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmSoldierInfectoidModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldierInfectoid;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmSoldierInfectoidGeoRenderer extends GeoEntityRenderer<EntityWyrmSoldierInfectoid> {
    public WyrmSoldierInfectoidGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmSoldierInfectoidModel());
    }
}

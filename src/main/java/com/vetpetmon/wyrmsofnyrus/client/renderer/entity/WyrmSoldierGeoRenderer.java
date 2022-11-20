package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmSoldierModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldier;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmSoldierGeoRenderer extends GeoEntityRenderer<EntityWyrmSoldier> {
    public WyrmSoldierGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmSoldierModel());
    }
}

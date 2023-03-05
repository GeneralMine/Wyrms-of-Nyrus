package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmWarriorOroModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarriorOro;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OroWarriorGeoRenderer extends GeoEntityRenderer<EntityWyrmWarriorOro> {
    public OroWarriorGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmWarriorOroModel());
    }
}

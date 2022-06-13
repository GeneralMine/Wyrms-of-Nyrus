package com.vetpetmon.wyrmsofnyrus.client;

import com.vetpetmon.wyrmsofnyrus.client.renderer.entity.*;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class renderEngine {
    public static void renderEngine() {
        RenderingRegistry.registerEntityRenderingHandler(EntityHexePod.class, HexePodGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmling.class, WyrmlingGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmProber.class, WyrmProberGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTheVisitor.class, TheVisitorGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmWorker.class, WyrmWorkerGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmRover.class, WyrmRoverGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDobber.class, DobberGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCallousPod.class, CallousPodGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmSoldier.class, WyrmSoldierGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCreepwyrm.class, CreepwyrmGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMyrmur.class, MyrmurGeoRenderer::new);
    }

}

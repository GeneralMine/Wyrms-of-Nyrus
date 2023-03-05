package com.vetpetmon.wyrmsofnyrus.client;

import com.vetpetmon.wyrmsofnyrus.client.renderer.entity.*;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.*;
import com.vetpetmon.wyrmsofnyrus.entity.follies.*;
import com.vetpetmon.wyrmsofnyrus.entity.nonwyrms.EntityNKAgent;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;

public class RenderEngine {
    public static void renderEngine() {
        RenderingRegistry.registerEntityRenderingHandler(EntityHexePod.class, HexePodGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmling.class, WyrmlingGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmProber.class, WyrmProberGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTheVisitor.class, TheVisitorGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmWorker.class, WyrmWorkerGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmRover.class, WyrmRoverGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmRoverUranium.class, WyrmRoverUraniumGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCallousPod.class, CallousPodGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmSoldier.class, WyrmSoldierGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmSoldierfrost.class, WyrmSoldierFrostGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmSoldierInfectoid.class, WyrmSoldierInfectoidGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCreepwyrm.class, CreepwyrmGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMyrmur.class, MyrmurGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmWarrior.class, WyrmWarriorGeoRenderer::new);
        if (Loader.isModLoaded("hbm")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityWyrmWarriorTainted.class, WyrmWarriorTaintedGeoRenderer::new);
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityBiter.class, BiterGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCreepedHumanoid.class, CreepedHumanoidGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCreepPod.class, CreepPodGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCreepling.class, CreeplingGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityStrykeling.class, StrykelingGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNKAgent.class, NKAgentGeoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWyrmWarriorOro.class, OroWarriorGeoRenderer::new);
    }

}

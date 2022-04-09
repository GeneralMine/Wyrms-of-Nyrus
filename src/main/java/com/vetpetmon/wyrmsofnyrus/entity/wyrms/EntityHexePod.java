package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.world.World;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;

public class EntityHexePod extends EntityMob {
    public EntityHexePod(World world) {
        super(world);
        setSize(1f, 1f);
        experienceValue = 0;
        this.isImmuneToFire = true;
        setNoAI(false);
        enablePersistence();
    }
    //no AI

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof EntityArrow)
            return false;
        if (source.getImmediateSource() instanceof EntityPlayer)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
    }

    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityHexePod.class, renderManager -> new RenderLiving(renderManager, new Modelhexepod(), 0.5f) {
            protected ResourceLocation getEntityTexture(Entity entity) {
                return new ResourceLocation("wyrmsofnyrus:textures/hexepod.png");
            }
        });
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation(""));
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }

    // Made with Blockbench 4.1.0
    // Exported for Minecraft version 1.7 - 1.12
    // Paste this class into your mod and generate all required imports
    public static class Modelhexepod extends ModelBase {
        private final ModelRenderer Pod;
        public Modelhexepod() {
            textureWidth = 64;
            textureHeight = 64;
            Pod = new ModelRenderer(this);
            Pod.setRotationPoint(0.0F, 24.0F, 0.0F);
            Pod.cubeList.add(new ModelBox(Pod, 0, 17, -3.0F, -13.0F, -3.0F, 6, 13, 6, 0.0F, false));
            Pod.cubeList.add(new ModelBox(Pod, 0, 0, -4.0F, -10.0F, -4.0F, 8, 9, 8, 0.0F, false));
            Pod.cubeList.add(new ModelBox(Pod, 24, 0, -2.0F, -15.0F, -2.0F, 4, 4, 4, 0.0F, false));
        }

        @Override
        public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
            Pod.render(f5);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }

        public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
            super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
        }
    }
}

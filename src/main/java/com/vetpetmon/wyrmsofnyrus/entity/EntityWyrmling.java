
package com.vetpetmon.wyrmsofnyrus.entity;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;

import java.util.Iterator;
import java.util.ArrayList;

import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class EntityWyrmling extends ElementswyrmsofnyrusMod.ModElement {
	public static final int ENTITYID = 1;
	public static final int ENTITYID_RANGED = 2;
	public EntityWyrmling(ElementswyrmsofnyrusMod instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> EntityEntryBuilder.create().entity(EntityCustom.class)
				.id(new ResourceLocation("wyrmsofnyrus", "wyrmling"), ENTITYID).name("wyrmling").tracker(32, 3, true).egg(-26317, -52).build());
	}

	private Biome[] allbiomes(net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Biome> in) {
		Iterator<Biome> itr = in.iterator();
		ArrayList<Biome> ls = new ArrayList<Biome>();
		while (itr.hasNext())
			ls.add(itr.next());
		return ls.toArray(new Biome[ls.size()]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityCustom.class, renderManager -> {
			return new RenderLiving(renderManager, new Modelwyrmlingmodel(), 0.5f) {
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("wyrmsofnyrus:textures/yrmlingex.png");
				}
			};
		});
	}
	public static class EntityCustom extends EntityCreature {
		public EntityCustom(World world) {
			super(world);
			setSize(0.4f, 0.3f);
			experienceValue = 0;
			this.isImmuneToFire = false;
			setNoAI(!true);
			enablePersistence();
		}

		@Override
		protected void initEntityAI() {
			super.initEntityAI();
			this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityWyrmling.EntityCustom.class, (float) 6, 1, 1.2));
			this.tasks.addTask(2, new EntityAIFollow(this, (float) 1, 10, 5));
			this.tasks.addTask(3, new EntityAIWander(this, 1));
			this.tasks.addTask(4, new EntityAILookIdle(this));
			this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayerMP.class, (float) 12));
			this.tasks.addTask(6, new EntityAISwimming(this));
			this.tasks.addTask(7, new EntityAILeapAtTarget(this, (float) 0.8));
			this.tasks.addTask(8, new EntityAIPanic(this, 1.2));
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.ARTHROPOD;
		}

		@Override
		protected boolean canDespawn() {
			return false;
		}

		@Override
		protected Item getDropItem() {
			return null;
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("wyrmsofnyrus:wyrmclicks3"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("wyrmsofnyrus:hiss2"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.death"));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		@Override
		public boolean attackEntityFrom(DamageSource source, float amount) {
			if (source == DamageSource.FALL)
				return false;
			if (source == DamageSource.DROWN)
				return false;
			return super.attackEntityFrom(source, amount);
		}

		@Override
		protected void applyEntityAttributes() {
			super.applyEntityAttributes();
			if (this.getEntityAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.3D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0D);
		}
	}

	// Made with Blockbench
	// Paste this code into your mod.
	// Make sure to generate all required imports
	public static class Modelwyrmlingmodel extends ModelBase {
		private final ModelRenderer head;
		private final ModelRenderer cuber1;
		private final ModelRenderer antennaleftr1;
		private final ModelRenderer bodymain;
		private final ModelRenderer leg8r1;
		private final ModelRenderer leg6r1;
		private final ModelRenderer tailmain;
		private final ModelRenderer leg3r1;
		private final ModelRenderer leg2r1;
		private final ModelRenderer leg1r1;
		private final ModelRenderer leg4r1;
		private final ModelRenderer tailmid;
		private final ModelRenderer leg10r1;
		private final ModelRenderer leg9r1;
		private final ModelRenderer tailend;
		private final ModelRenderer cuber2;
		public Modelwyrmlingmodel() {
			textureWidth = 32;
			textureHeight = 32;
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 23.0F, -5.0F);
			head.cubeList.add(new ModelBox(head, 18, 26, -2.0F, -2.0F, -3.0F, 4, 3, 3, 0.0F, false));
			cuber1 = new ModelRenderer(this);
			cuber1.setRotationPoint(0.0F, -0.6022F, -3.1948F);
			setRotationAngle(cuber1, 0.4363F, 0.0F, 0.0F);
			head.addChild(cuber1);
			cuber1.cubeList.add(new ModelBox(cuber1, 0, 12, -1.5F, -1.0F, -1.0F, 3, 2, 2, 0.0F, false));
			antennaleftr1 = new ModelRenderer(this);
			antennaleftr1.setRotationPoint(-1.0F, -4.0F, -1.5F);
			setRotationAngle(antennaleftr1, -0.5672F, 0.0F, 0.0F);
			head.addChild(antennaleftr1);
			antennaleftr1.cubeList.add(new ModelBox(antennaleftr1, 12, 10, 2.0F, -2.0F, 0.5F, 0, 4, 2, 0.0F, false));
			antennaleftr1.cubeList.add(new ModelBox(antennaleftr1, 12, 10, 0.0F, -2.0F, 0.5F, 0, 4, 2, 0.0F, false));
			bodymain = new ModelRenderer(this);
			bodymain.setRotationPoint(0.0F, 23.0F, -2.0F);
			bodymain.cubeList.add(new ModelBox(bodymain, 0, 24, -2.5F, -3.1F, -3.0F, 5, 4, 4, 0.0F, false));
			leg8r1 = new ModelRenderer(this);
			leg8r1.setRotationPoint(3.5F, -1.0F, 6.5F);
			setRotationAngle(leg8r1, 0.4363F, -0.2618F, 0.6981F);
			bodymain.addChild(leg8r1);
			leg8r1.cubeList.add(new ModelBox(leg8r1, 0, 21, -3.5F, -3.0F, -8.25F, 3, 0, 1, 0.0F, true));
			leg8r1.cubeList.add(new ModelBox(leg8r1, 0, 21, -3.0F, -2.25F, -6.0F, 3, 0, 1, 0.0F, true));
			leg6r1 = new ModelRenderer(this);
			leg6r1.setRotationPoint(-3.5F, -1.0F, 6.5F);
			setRotationAngle(leg6r1, 0.4363F, 0.2618F, -0.6981F);
			bodymain.addChild(leg6r1);
			leg6r1.cubeList.add(new ModelBox(leg6r1, 0, 21, 0.0F, -2.25F, -6.0F, 3, 0, 1, 0.0F, false));
			leg6r1.cubeList.add(new ModelBox(leg6r1, 0, 21, 0.5F, -3.0F, -8.25F, 3, 0, 1, 0.0F, false));
			tailmain = new ModelRenderer(this);
			tailmain.setRotationPoint(0.0F, 24.0F, 0.0F);
			tailmain.cubeList.add(new ModelBox(tailmain, 1, 0, -2.5F, -5.0F, -1.0F, 5, 5, 7, 0.0F, false));
			leg3r1 = new ModelRenderer(this);
			leg3r1.setRotationPoint(3.5F, -2.0F, 4.5F);
			setRotationAngle(leg3r1, 0.4363F, -0.2618F, 0.6981F);
			tailmain.addChild(leg3r1);
			leg3r1.cubeList.add(new ModelBox(leg3r1, 0, 21, -1.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, true));
			leg2r1 = new ModelRenderer(this);
			leg2r1.setRotationPoint(-3.5F, -2.0F, 0.5F);
			setRotationAngle(leg2r1, 0.4363F, 0.2618F, -0.6981F);
			tailmain.addChild(leg2r1);
			leg2r1.cubeList.add(new ModelBox(leg2r1, 0, 21, -1.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, false));
			leg1r1 = new ModelRenderer(this);
			leg1r1.setRotationPoint(3.5F, -2.0F, 0.5F);
			setRotationAngle(leg1r1, 0.4363F, -0.2618F, 0.6981F);
			tailmain.addChild(leg1r1);
			leg1r1.cubeList.add(new ModelBox(leg1r1, 0, 21, -1.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, true));
			leg4r1 = new ModelRenderer(this);
			leg4r1.setRotationPoint(-3.5F, -2.0F, 4.5F);
			setRotationAngle(leg4r1, 0.4363F, 0.2618F, -0.6981F);
			tailmain.addChild(leg4r1);
			leg4r1.cubeList.add(new ModelBox(leg4r1, 0, 21, -1.5F, 0.0F, -0.5F, 3, 0, 1, 0.0F, false));
			tailmid = new ModelRenderer(this);
			tailmid.setRotationPoint(0.0F, 23.0F, 7.0F);
			tailmid.cubeList.add(new ModelBox(tailmid, 18, 0, -2.0F, -3.1F, -1.0F, 4, 4, 3, 0.0F, false));
			leg10r1 = new ModelRenderer(this);
			leg10r1.setRotationPoint(-3.5F, -1.0F, -2.5F);
			setRotationAngle(leg10r1, 0.4363F, 0.2618F, -0.6981F);
			tailmid.addChild(leg10r1);
			leg10r1.cubeList.add(new ModelBox(leg10r1, 0, 21, -1.75F, 2.0F, 2.25F, 3, 0, 1, 0.0F, false));
			leg9r1 = new ModelRenderer(this);
			leg9r1.setRotationPoint(3.5F, -1.0F, -2.5F);
			setRotationAngle(leg9r1, 0.4363F, -0.2618F, 0.6981F);
			tailmid.addChild(leg9r1);
			leg9r1.cubeList.add(new ModelBox(leg9r1, 0, 21, -1.25F, 2.0F, 2.25F, 3, 0, 1, 0.0F, true));
			tailend = new ModelRenderer(this);
			tailend.setRotationPoint(0.0F, 23.0F, 8.0F);
			cuber2 = new ModelRenderer(this);
			cuber2.setRotationPoint(0.0F, 1.0F, -4.0F);
			setRotationAngle(cuber2, 0.0F, -0.7854F, 0.0F);
			tailend.addChild(cuber2);
			cuber2.cubeList.add(new ModelBox(cuber2, 0, 2, 2.5F, -3.2F, 2.5F, 2, 3, 2, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			head.render(f5);
			bodymain.render(f5);
			tailmain.render(f5);
			tailmid.render(f5);
			tailend.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.tailmid.rotateAngleZ = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.tailend.rotateAngleZ = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.tailmain.rotateAngleZ = MathHelper.cos(f * 0.6662F) * f1;
		}
	}
}


package com.vetpetmon.wyrmsofnyrus.entity;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;

import java.util.Iterator;
import java.util.ArrayList;

import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class EntityRoyalGrub extends ElementswyrmsofnyrusMod.ModElement {
	public static final int ENTITYID = 3;
	public static final int ENTITYID_RANGED = 4;
	public EntityRoyalGrub(ElementswyrmsofnyrusMod instance) {
		super(instance, 2);
	}

	@Override
	public void initElements() {
		elements.entities.add(() -> EntityEntryBuilder.create().entity(EntityCustom.class)
				.id(new ResourceLocation("wyrmsofnyrus", "royalgrub"), ENTITYID).name("royalgrub").tracker(64, 3, true).egg(-52, -13159).build());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		Biome[] spawnBiomes = allbiomes(Biome.REGISTRY);
		EntityRegistry.addSpawn(EntityCustom.class, 1, 1, 1, EnumCreatureType.MONSTER, spawnBiomes);
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
			return new RenderLiving(renderManager, new ModelroyalgrubModel(), 1f) {
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("wyrmsofnyrus:textures/royalgrubodel.png");
				}
			};
		});
	}
	public static class EntityCustom extends EntityMob {
		public EntityCustom(World world) {
			super(world);
			setSize(1.5f, 1.5f);
			experienceValue = 5;
			this.isImmuneToFire = false;
			setNoAI(!true);
			enablePersistence();
		}

		@Override
		protected void initEntityAI() {
			super.initEntityAI();
			this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false, true));
			this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.2, true));
			this.tasks.addTask(3, new EntityAIWander(this, 1));
			this.tasks.addTask(4, new EntityAILeapAtTarget(this, (float) 1.5));
			this.tasks.addTask(5, new EntityAISwimming(this));
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
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("wyrmsofnyrus:grubidle"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("wyrmsofnyrus:grubhurt"));
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
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.5D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5D);
		}
	}

	// Made with Blockbench
	// Paste this code into your mod.
	// Make sure to generate all required imports
	public static class ModelroyalgrubModel extends ModelBase {
		private final ModelRenderer Head;
		private final ModelRenderer Fangs;
		private final ModelRenderer Fang4;
		private final ModelRenderer Fang3;
		private final ModelRenderer Fang2;
		private final ModelRenderer Fang1;
		private final ModelRenderer Body;
		private final ModelRenderer BodyMid;
		private final ModelRenderer BodyEnd;
		public ModelroyalgrubModel() {
			textureWidth = 128;
			textureHeight = 80;
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0F, 17.1111F, -19.9444F);
			Head.cubeList.add(new ModelBox(Head, 22, 43, -6.5F, -6.1111F, -9.0556F, 12, 12, 9, 0.0F, false));
			Head.cubeList.add(new ModelBox(Head, 0, 42, -5.5F, -5.1111F, -11.5556F, 2, 2, 3, 0.0F, false));
			Head.cubeList.add(new ModelBox(Head, 0, 42, 2.5F, -5.1111F, -11.5556F, 2, 2, 3, 0.0F, false));
			Head.cubeList.add(new ModelBox(Head, 0, 42, 2.5F, 2.8889F, -11.5556F, 2, 2, 3, 0.0F, false));
			Head.cubeList.add(new ModelBox(Head, 0, 42, -5.5F, 2.8889F, -11.5556F, 2, 2, 3, 0.0F, false));
			Fangs = new ModelRenderer(this);
			Fangs.setRotationPoint(-3.5F, 3.3889F, -11.8056F);
			Head.addChild(Fangs);
			Fang4 = new ModelRenderer(this);
			Fang4.setRotationPoint(0.0F, -7.0F, 0.0F);
			setRotationAngle(Fang4, -0.2618F, -0.5236F, 1.2217F);
			Fangs.addChild(Fang4);
			Fang4.cubeList.add(new ModelBox(Fang4, 0, 38, -0.75F, 0.0F, -1.75F, 1, 1, 3, 0.0F, false));
			Fang3 = new ModelRenderer(this);
			Fang3.setRotationPoint(7.0F, -7.0F, 0.0F);
			setRotationAngle(Fang3, -0.2618F, 0.5236F, -1.0472F);
			Fangs.addChild(Fang3);
			Fang3.cubeList.add(new ModelBox(Fang3, 0, 38, -0.75F, -1.0F, -1.75F, 1, 1, 3, 0.0F, false));
			Fang2 = new ModelRenderer(this);
			Fang2.setRotationPoint(7.0F, 0.0F, 0.0F);
			setRotationAngle(Fang2, -0.2618F, 0.5236F, 0.4363F);
			Fangs.addChild(Fang2);
			Fang2.cubeList.add(new ModelBox(Fang2, 0, 38, -1.25F, -0.25F, -1.75F, 1, 1, 3, 0.0F, false));
			Fang1 = new ModelRenderer(this);
			Fang1.setRotationPoint(0.0F, 0.0F, 0.0F);
			setRotationAngle(Fang1, -0.2618F, -0.5236F, -0.4363F);
			Fangs.addChild(Fang1);
			Fang1.cubeList.add(new ModelBox(Fang1, 0, 38, -0.75F, -0.75F, -1.75F, 1, 1, 3, 0.0F, false));
			Body = new ModelRenderer(this);
			Body.setRotationPoint(0.0F, 20.5F, -8.5F);
			Body.cubeList.add(new ModelBox(Body, 60, 0, -7.5F, -10.5F, -12.5F, 14, 14, 20, 0.0F, false));
			BodyMid = new ModelRenderer(this);
			BodyMid.setRotationPoint(-0.25F, -0.25F, 9.5F);
			Body.addChild(BodyMid);
			BodyMid.cubeList.add(new ModelBox(BodyMid, 48, 41, -8.25F, -11.25F, -3.0F, 16, 15, 24, 0.0F, false));
			BodyEnd = new ModelRenderer(this);
			BodyEnd.setRotationPoint(-0.25F, -2.25F, 28.0F);
			BodyMid.addChild(BodyEnd);
			BodyEnd.cubeList.add(new ModelBox(BodyEnd, 0, 0, -6.0F, -6.0F, -8.0F, 12, 12, 16, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			Head.render(f5);
			Body.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.Body.rotateAngleZ = f3 / (180F / (float) Math.PI);
		}
	}
}

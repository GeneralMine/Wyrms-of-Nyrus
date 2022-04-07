
package com.vetpetmon.wyrmsofnyrus.entity;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBase;
import net.minecraft.block.state.IBlockState;

import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;

import com.vetpetmon.wyrmsofnyrus.item.ItemCreepshard;
import com.vetpetmon.wyrmsofnyrus.ElementswyrmsofnyrusMod;

@ElementswyrmsofnyrusMod.ModElement.Tag
public class EntityWyrmProber extends ElementswyrmsofnyrusMod.ModElement {
	public static final int ENTITYID = 5;
	public static final int ENTITYID_RANGED = 6;
	public EntityWyrmProber(ElementswyrmsofnyrusMod instance) {
		super(instance, 20);
	}

	@Override
	public void initElements() {
		elements.entities
				.add(() -> EntityEntryBuilder.create().entity(EntityCustom.class).id(new ResourceLocation("wyrmsofnyrus", "wyrmprober"), ENTITYID)
						.name("wyrmprober").tracker(64, 3, true).egg(-10066177, -26368).build());
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
			return new RenderLiving(renderManager, new Modelwyrmprobermodel(), 0.5f) {
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("wyrmsofnyrus:textures/wyrmprobermodel.png");
				}
			};
		});
	}
	public static class EntityCustom extends EntityMob {
		public EntityCustom(World world) {
			super(world);
			setSize(0.6f, 1.2f);
			experienceValue = 5;
			this.isImmuneToFire = false;
			setNoAI(!true);
			this.navigator = new PathNavigateFlying(this, this.world);
			this.moveHelper = new EntityFlyHelper(this);
		}

		@Override
		protected void initEntityAI() {
			super.initEntityAI();
			this.tasks.addTask(1, new EntityAIBase() {
				{
					this.setMutexBits(1);
				}
				public boolean shouldExecute() {
					if (EntityCustom.this.getAttackTarget() != null && !EntityCustom.this.getMoveHelper().isUpdating()) {
						return true;
					} else {
						return false;
					}
				}

				@Override
				public boolean shouldContinueExecuting() {
					return EntityCustom.this.getMoveHelper().isUpdating() && EntityCustom.this.getAttackTarget() != null
							&& EntityCustom.this.getAttackTarget().isEntityAlive();
				}

				@Override
				public void startExecuting() {
					EntityLivingBase livingentity = EntityCustom.this.getAttackTarget();
					Vec3d vec3d = livingentity.getPositionEyes(1);
					EntityCustom.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 4);
				}

				@Override
				public void updateTask() {
					EntityLivingBase livingentity = EntityCustom.this.getAttackTarget();
					double d0 = EntityCustom.this.getDistanceSq(livingentity);
					if (d0 <= getAttackReachSq(livingentity)) {
						EntityCustom.this.attackEntityAsMob(livingentity);
					} else if (d0 < 32) {
						Vec3d vec3d = livingentity.getPositionEyes(1);
						EntityCustom.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 4);
					}
				}

				protected double getAttackReachSq(EntityLivingBase attackTarget) {
					return EntityCustom.this.width * 1.5 * EntityCustom.this.height * 1.5 + attackTarget.height;
				}
			});


			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayerMP.class, false, true));
			this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false, true));
			this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityMob.class, false, true));

			//this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityHusk.class, false, true));
			//this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityZombie.class, false, true));

			this.tasks.addTask(5, new EntityAIWander(this, 2, 20) {
				@Override
				protected Vec3d getPosition() {
					Random random = EntityCustom.this.getRNG();
					double dir_x = EntityCustom.this.posX + ((random.nextFloat() * 2 - 1) * 16);
					double dir_y = EntityCustom.this.posY + ((random.nextFloat() * 2 - 1) * 16);
					double dir_z = EntityCustom.this.posZ + ((random.nextFloat() * 2 - 1) * 16);
					return new Vec3d(dir_x, dir_y, dir_z);
				}
			});
		}

		@Override
		public EnumCreatureAttribute getCreatureAttribute() {
			return EnumCreatureAttribute.UNDEFINED;
		}

		@Override
		protected Item getDropItem() {
			return new ItemStack(ItemCreepshard.block, (int) (1)).getItem();
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("wyrmsofnyrus:wyrmclicks4"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.bat.takeoff"));
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
		public void fall(float l, float d) {
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
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.2D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2D);
			if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
			if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
			this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(2);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.setNoGravity(true);
		}

		@Override
		protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		}

		@Override
		public void setNoGravity(boolean ignored) {
			super.setNoGravity(true);
		}
	}

	// Made with Blockbench
	// Paste this code into your mod.
	// Make sure to generate all required imports
	public static class Modelwyrmprobermodel extends ModelBase {
		private final ModelRenderer Body;
		private final ModelRenderer wingLeft;
		private final ModelRenderer wingRight;
		private final ModelRenderer End;
		private final ModelRenderer Head;
		private final ModelRenderer Prob;
		public Modelwyrmprobermodel() {
			textureWidth = 64;
			textureHeight = 64;
			Body = new ModelRenderer(this);
			Body.setRotationPoint(-0.0833F, 19.4583F, 0.5F);
			Body.cubeList.add(new ModelBox(Body, 13, 23, -2.4167F, -5.2083F, -5.5F, 5, 4, 11, 0.0F, false));
			Body.cubeList.add(new ModelBox(Body, 0, 13, -3.4167F, -2.4583F, -3.5F, 6, 2, 8, 0.0F, false));
			wingLeft = new ModelRenderer(this);
			wingLeft.setRotationPoint(2.5833F, -3.9583F, 1.0F);
			Body.addChild(wingLeft);
			wingLeft.cubeList.add(new ModelBox(wingLeft, 8, 7, -1.5F, -0.5F, -2.5F, 9, 1, 5, 0.0F, false));
			wingRight = new ModelRenderer(this);
			wingRight.setRotationPoint(-2.4167F, -3.9583F, 1.0F);
			Body.addChild(wingRight);
			wingRight.cubeList.add(new ModelBox(wingRight, 8, 7, -7.5F, -0.5F, -2.5F, 9, 1, 5, 0.0F, false));
			End = new ModelRenderer(this);
			End.setRotationPoint(0.0833F, -2.4583F, 8.0F);
			Body.addChild(End);
			setRotationAngle(End, -0.2618F, 0.0F, 0.0F);
			End.cubeList.add(new ModelBox(End, 34, 13, -2.25F, -1.25F, -5.5F, 4, 3, 11, 0.0F, false));
			Head = new ModelRenderer(this);
			Head.setRotationPoint(0.0833F, 4.5417F, -3.5F);
			Body.addChild(Head);
			Head.cubeList.add(new ModelBox(Head, 38, 0, -3.0F, -10.0F, -8.0F, 6, 6, 7, 0.0F, false));
			Prob = new ModelRenderer(this);
			Prob.setRotationPoint(0.5F, -4.5F, -9.5F);
			Head.addChild(Prob);
			setRotationAngle(Prob, 1.0472F, 0.0F, 0.0F);
			Prob.cubeList.add(new ModelBox(Prob, 29, 6, -1.0F, -0.116F, -6.1651F, 1, 1, 7, 0.0F, false));
			Prob.cubeList.add(new ModelBox(Prob, 22, 0, -2.25F, -1.2679F, 0.134F, 3, 3, 4, 0.0F, false));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			Body.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
			this.wingRight.rotateAngleZ = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.wingLeft.rotateAngleZ = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.Body.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.Body.rotateAngleX = f4 / (180F / (float) Math.PI);
		}
	}
}

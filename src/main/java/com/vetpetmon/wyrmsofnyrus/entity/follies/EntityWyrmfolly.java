package com.vetpetmon.wyrmsofnyrus.entity.follies;

import com.vetpetmon.wyrmsofnyrus.advancements.Advancements;
import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmBreakDoors;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.creepspreadRules;

public abstract class EntityWyrmfolly extends MobEntityBase implements IAnimatable, IMob {
    private final AnimationFactory factory = new AnimationFactory(this);
    protected int srpcothimmunity;
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.VARINT);
    protected int killCount, level; // Also known as ascension points
    protected float HP, DEF, ATK, SPD, KBRR; // Stats
    private static final DataParameter<Float>
            FOLLYHEALTH = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.FLOAT),
            FOLLYARMOR = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.FLOAT),
            FOLLYBASEDAMAGE = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.FLOAT),
            FOLLYSPEED = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.FLOAT),
            FOLLYKBR = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.FLOAT);



    public void updateLevel(){
        this.setLevel((int) (Math.floor((float)killCount/Radiogenetics.follyAscenSteps)+1));
        wyrmsofnyrus.logger.info("Wyrmfolly level is:" + this.getLevel()); //https://media.discordapp.net/attachments/1043999806038757406/1047202044227878912/unknown.png?width=604&height=702
        this.setHealth(this.getHealth() + (this.getMaxHealth()/8)); //Heals 1/8 of health for every kill.
    }

    public EntityWyrmfolly(World worldIn) { //, float health, float armor, float damage, float speed, float knockbackResist
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
        enablePersistence();
        setNoAI(false);
        this.killCount = 0;
    }

    public void setStats(float entityHealth, float entityArmor, float entityDamage,  float entitySpeed, float entityKBR) {
        float diff = (float) ((float)this.getLevel() * Radiogenetics.follyAscenBuffFactor);
        this.setFollyKBR(entityKBR);
        this.setFollySpeed(entitySpeed);
        this.setFollyATK(entityDamage + ((entityDamage/10) * diff));
        this.setFollyArmor(entityArmor + (entityArmor  * diff));
        this.setFollyHealth(entityHealth + ((entityHealth/(entityHealth*1.5F)) * diff));
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        this.setKillCount(getKillCount()+1);
        this.updateLevel();
        World world = entity.world;
        BlockPos lookingBlock = new BlockPos(entity.posX, entity.posY - 1, entity.posZ);
        if (creepspreadRules(lookingBlock, world, lookingBlock)) {
            assert false;
            world.setBlockState(lookingBlock, AllBlocks.follyflesh.getDefaultState(), 3);
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        updateAttributes();
    }

    protected void updateAttributes() {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getFollyHealth());
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(this.getFollyArmor());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.getFollyATK());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getFollySpeed());
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(this.getFollyKBR());
    }


    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        makeAllTargets();
        this.tasks.addTask(2, new wyrmBreakDoors(this, 200));
        this.tasks.addTask(1, new EntityAIWander(this, 0.65));
        this.tasks.addTask(1, new EntityAILeapAtTarget(this, 1.25F));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.15D, true));
    }

    public boolean attackEntityAsMobO(Entity entityIn, boolean flag)
    {
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public void setKillCount(int num) {this.killCount = num;}
    public int getKillCount() {return this.killCount;}

    protected boolean canDespawn() {return false;}

    // Will target anything that is alive that isn't another folly.
    protected void makeAllTargets() {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 2, true, false, target -> !((target instanceof EntityWyrmfolly)||(target instanceof EntityCreeper))));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(LEVEL, Integer.valueOf(0));
        this.dataManager.register(FOLLYHEALTH, Float.valueOf(0));
        this.dataManager.register(FOLLYARMOR, Float.valueOf(0));
        this.dataManager.register(FOLLYBASEDAMAGE, Float.valueOf(0));
        this.dataManager.register(FOLLYSPEED, Float.valueOf(0));
        this.dataManager.register(FOLLYKBR, Float.valueOf(0));
    }

    protected abstract void StatMap();

    public int getLevel() {return dataManager.get(LEVEL).intValue();}
    public void setLevel(int level) {this.dataManager.set(LEVEL,level);}
    public float getFollyHealth() {return dataManager.get(FOLLYHEALTH).floatValue();}
    public void setFollyHealth(float input) {this.dataManager.set(FOLLYHEALTH,input);}
    public float getFollyArmor() {return dataManager.get(FOLLYARMOR).floatValue();}
    public void setFollyArmor(float input) {this.dataManager.set(FOLLYARMOR,input);}
    public float getFollyATK() {return dataManager.get(FOLLYBASEDAMAGE).floatValue();}
    public void setFollyATK(float input) {this.dataManager.set(FOLLYBASEDAMAGE,input);}
    public float getFollySpeed() {return dataManager.get(FOLLYSPEED).floatValue();}
    public void setFollySpeed(float input) {this.dataManager.set(FOLLYSPEED,input);}
    public float getFollyKBR() {return dataManager.get(FOLLYKBR).floatValue();}
    public void setFollyKBR(float input) {this.dataManager.set(FOLLYKBR,input);}

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {

        super.readEntityFromNBT(compound);
        if (compound.hasKey("srpcothimmunity")) this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        if (compound.hasKey("killcount")) this.killCount = compound.getInteger("killcount");

        compound.setInteger("level", this.getLevel());
        compound.setFloat("follyhealth", this.getFollyHealth());
        compound.setFloat("follyarmor", this.getFollyArmor());
        compound.setFloat("follydamage", this.getFollyATK());
        compound.setFloat("follyspeed", this.getFollySpeed());
        compound.setFloat("follykbr", this.getFollyKBR());

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
        compound.setInteger("killcount", this.killCount);
        // this is terrible there needs to be a better way
        this.setLevel(compound.getInteger("level"));
        this.setFollyHealth(compound.getFloat("follyhealth"));
        this.setFollyArmor(compound.getFloat("follyarmor"));
        this.setFollyATK(compound.getFloat("follydamage"));
        this.setFollySpeed(compound.getFloat("follyspeed"));
        this.setFollyKBR(compound.getFloat("follykbr"));
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        Entity entity = source.getTrueSource();
        if (entity instanceof EntityPlayerMP && this.isBurning()) Advancements.grantAchievement((EntityPlayerMP) entity, Advancements.killitwithfire);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    // Checkers for animations or abilities.
    private boolean blockIsWater(BlockPos pos)
    {
        return world.getBlockState(pos).getMaterial() == Material.WATER;
    }

    public boolean isGrounded(){
        return (world.isBlockFullCube(new BlockPos(getPosition().getX(), getPosition().getY()-1,getPosition().getZ())));
    }
}

package com.vetpetmon.wyrmsofnyrus.entity.follies;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmBreakDoors;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.creepspreadRules;

public abstract class EntityWyrmfolly extends EntityMob implements IAnimatable, IMob {
    private final AnimationFactory factory = new AnimationFactory(this);
    protected int srpcothimmunity;
    protected int killCount, level; // Also known as ascension points
    protected float HP, DEF, ATK, SPD, KBR; // Stats
    private static final DataParameter<Integer> ATTACKID = EntityDataManager.createKey(EntityWyrmfolly.class, DataSerializers.VARINT);
    // Animation and AI util
    public void setAttack(int attackID)
    {
        this.getDataManager().set(ATTACKID, attackID);
    }


    //@SideOnly(Side.CLIENT)
    public int getAttack()
    {
        return this.getDataManager().get(ATTACKID);
    }

    public void updateLevel(){
        level = (int) (Math.floor((float)killCount/Radiogenetics.follyAscenSteps)+1);
        //wyrmsofnyrus.logger.info("Wyrmfolly level is:" + this.level); //https://media.discordapp.net/attachments/1043999806038757406/1047202044227878912/unknown.png?width=604&height=702
        this.setHealth(this.getHealth() + (this.getMaxHealth()/2)); //Heals half of health for every kill.
    }
    public int getLevel(){
        return this.level;
    }

    public EntityWyrmfolly(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
        enablePersistence();
        setNoAI(false);
        this.killCount = 0;
    }

    public void setStats(float entityHealth, float entityArmor, float entityDamage,  float entitySpeed, float entityKBR) {
        float diff = (float) (this.getLevel() * Radiogenetics.follyAscenBuffFactor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(entityHealth * Radiogenetics.wyrmVitality, diff));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(entityArmor * Radiogenetics.wyrmResistance, diff));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(entityDamage * Radiogenetics.wyrmStrength, diff));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(entitySpeed);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(entityKBR);
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        this.setKillCount(getKillCount()+1);
        this.updateLevel();
        World world = entity.world;
        BlockPos lookingBlock = new BlockPos(entity.posX, entity.posY - 1, entity.posZ);
        //Block blockLooking = (world.getBlockState(lookingBlock)).getBlock();
        if (creepspreadRules(lookingBlock, world, lookingBlock)) {
            assert false;
            world.setBlockState(lookingBlock, AllBlocks.follyflesh.getDefaultState(), 3);
        }
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
        this.dataManager.register(ATTACKID, 0);
    }

    protected abstract void StatMap();

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {

        if (compound.hasKey("srpcothimmunity")) this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        if (compound.hasKey("killcount")) this.killCount = compound.getInteger("killcount");
        if (compound.hasKey("level")) this.level = compound.getInteger("level");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
        compound.setInteger("killcount", this.killCount);
        compound.setInteger("level", this.level);
    }

    @Override
    public void registerControllers(AnimationData animationData) {

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

    public boolean isInWater(){
        return (blockIsWater(new BlockPos(getPosition().getX(), getPosition().getY(),getPosition().getZ())));
    }

    public boolean isGrounded(){
        return (world.isBlockFullCube(new BlockPos(getPosition().getX(), getPosition().getY()-1,getPosition().getZ())));
    }
}

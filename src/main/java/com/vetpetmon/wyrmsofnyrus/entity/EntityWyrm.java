package com.vetpetmon.wyrmsofnyrus.entity;

import com.google.common.base.Predicate;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.wyrmsofnyrus.advancements.Advancements;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.WyrmBreakDoors;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmKillBonuses;
import com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt.GestaltFollow;
import com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt.GestaltHostMind;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreeped;
import com.vetpetmon.wyrmsofnyrus.evo.EvoPoints;
import com.vetpetmon.wyrmsofnyrus.locallib.DifficultyStats;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmDeathSpecial.wyrmDeathSpecial;

/**
 * Abstract class that all Wyrms of Nyrus entities are built from.
 * Handles a lot of the hot nonsense of class inheritance for you.
 * You're welcome. <3
 */
public abstract class EntityWyrm extends MobEntityBase implements IAnimatable, IMob {

    private static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(EntityWyrm.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> RAGECOOLDOWN = EntityDataManager.createKey(EntityWyrm.class, DataSerializers.VARINT);

    public static EntityLiving kollectiveTarget;
    private double potency = 0.0;

    // TODO: FIX THIS.
    //  LIST OF CASTE TYPES:
    //  0 - Other
    //  1 - Ignoble (workers)
    //  2 - Scouters
    //  3 - Warriors
    //  4 - Archives
    //  5 - Royal
    //  6 - Imperial
    //  7 - Hexe pods (no AI but gravity)
    //  8 - Visitor (No AI + vanishes)
    //  9 - Event (vanishes)
    public int casteType;
    // Shared GeckoLib method for all wyrms. Saves some spaghetti in the non-abstract wyrm classes.
    private final AnimationFactory factory = new AnimationFactory(this);
    // SRP compatibility <3
    protected int srpcothimmunity;

    // data management
    public byte getByteFromDataManager(DataParameter<Byte> key) {
        try {
            return this.getDataManager().get(key);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public double getPotency() {return potency;}
    public void setPotency(double potency) {this.potency = potency;}

    public EntityWyrm(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
    }

    public boolean isPotionApplicable(final PotionEffect potion) {
        if (potion.getPotion() == MobEffects.POISON) return false;
        return super.isPotionApplicable(potion);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        wyrmDeathSpecial(this,getPosition(),world,getPotency());
    }
    public boolean canBreatheUnderwater()
    {
        return true;
    }

        // Most wyrms don't need to despawn. Despawning breaks a lot of things, like Creepwyrms, for a prime example.
    protected boolean canDespawn() {return false;}

    // Getters for Wyrms.
    protected static boolean getSimpleAI() {return AI.performanceAIMode;}
    protected static boolean getAttackMobs() {return AI.attackMobs;}
    protected static boolean getAttackAnimals() {return AI.attackAnimals;}
    protected static boolean getAttackVillagers() {return AI.attackVillagers;}
    protected static boolean getWillAttackCreepers() {return AI.suicidalWyrms;}
    protected static boolean getWillNotAttackPlayers() {return AI.niceToPlayers;}

    /**
     * Getter for invasion difficulty. Makes things look a million times neater, and can be called outside EntityWyrm.
     * Handles the world dependency for you. I love it.
     * @return The invasion difficulty of the world.
     */
    public double getInvasionDifficulty() {return WyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty;}

    public float genDifficulty() {
        return (float) (getInvasionDifficulty());
    }

    public void setStats(float entityHealth, float entityArmor, float entityDamage,  float entitySpeed, float entityKBR) {
        float diff = genDifficulty();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(DifficultyStats.health(entityHealth * Radiogenetics.wyrmVitality, diff));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(DifficultyStats.armor(entityArmor * Radiogenetics.wyrmResistance, diff));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(DifficultyStats.damage(entityDamage * Radiogenetics.wyrmStrength, diff));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(entitySpeed);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(entityKBR);
    }
    public void setStatsEvo(float entityHealth, float entityArmor, float entityDamage,  float entitySpeed, float entityKBR, int minLevel) {
        float diff = genDifficulty();
        int level = ((EvoPoints.getLevel() - minLevel)+1);
        double HP = (entityHealth * Radiogenetics.wyrmVitality), DEF = (entityArmor * Radiogenetics.wyrmResistance), ATK = (entityDamage * Radiogenetics.wyrmStrength);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(DifficultyStats.health((HP+(HP*(Evo.evoPowerHP*level))), diff));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(DifficultyStats.armor((DEF+ (DEF*(Evo.evoPowerDEF*level))), diff));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(DifficultyStats.damage((ATK+(ATK*(Evo.evoPowerATK*level))), diff));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(entitySpeed);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(entityKBR);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    }

    /**
     * If we are not running with Simple AI (config option), then add Entity AI LookIdle & WanderAvoidWater.
     * The reason being is these add random entity updates 24/7, and with 400 wyrms in loaded chunks, this makes a difference of ~300 ms processing time. AKA: With hundreds of wyrms, this saves TPS.
     * If it is running with Simple AI, don't add it to the AI task pool, lowering entity update counts.
     * PLEASE use this instead of using addTask. I will not accept unoptimized code for optimization functions.
     */
    protected void simpleAI() {
        if (!getSimpleAI()) {
            this.tasks.addTask(2, new EntityAILookIdle(this));
            this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        }
    }

    /**
     * Wyrms with Sapient AI can futz with doors and break stuff and just harass players in general.
     * They're given better AI and can ruin the player's day. Nuff said.
     */
    protected void isSapient() {
        this.tasks.addTask(2, new WyrmBreakDoors(this, 200));
        this.tasks.addTask(1, new EntityAIWander(this, 0.8));
    }

    /**
     * Makes wyrms attack ALL possible targets. Some targets may be disabled by configs.
     * Use the direct methods like after______(); to bypass config settings for specific entities, just don't use this general method!
     * It'll add more tasks on top of tasks, causing funky behaviours... Or nothing goes wrong at all, but I'd prefer you didn't make us find out.
     * If all config options disable attacking other mobs, only player targeting will remain.
     */
    protected void makeAllTargets() {
        afterPlayers();
        if(getAttackAnimals()) afterAnimals();
        if(getAttackVillagers()) afterVillagers();
        if(getAttackMobs()) afterMobs();

        //Lycanites Mobs just copies the raw EntityLiving class and builds their entities using that base class. Why.
        if (Loader.isModLoaded("lycanitesmobs") && getAttackMobs()) {
            this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, true, false, new Predicate<EntityMob>() {
            public boolean apply(EntityMob target) {return(target instanceof EntityLiving && !((target instanceof EntityCreeper) || (target instanceof EntityWyrm)));}
        }));}
    }
    // Everything here bypasses config options. Use sparingly with good reason.
    protected void afterMobs() {
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, true, false, new Predicate<EntityMob>() {
            public boolean apply(EntityMob target) {
                if (getWillAttackCreepers()) return !(target instanceof EntityWyrm);
                else return !((target instanceof EntityCreeper) || (target instanceof EntityWyrm));
            }
        }));
    }
    protected void afterAnimals() {this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, true, false));}
    protected void afterVillagers() { this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true, false));}

    protected void afterPlayers(boolean checkSight, boolean ignoreConfig) { if (ignoreConfig) afterPlayers(checkSight); }
    protected void afterPlayers(boolean checkSight) {
        if (!getWillNotAttackPlayers()){
            this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, checkSight, false));
        }
    }
    protected void afterPlayers() {
        if (!getWillNotAttackPlayers()){
            this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, false, false, e -> true));
        }
    }
    protected void afterInsectoids() {
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityCaveSpider.class, true, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntitySpider.class, true, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntitySilverfish.class, true, false));
    }

    // Collective Consciousness, AKA Gestalt
    protected boolean partakesInGestalt(){return true;}

    /**
     * MAKES WYRMS SLOWLY GRAVITATE TOWARDS PLAYERS
     * ABSOLUTELY BRILLIANT!
     */
    protected void enabledGestalt() {
        if (this.partakesInGestalt()) {
            this.tasks.addTask(8, new GestaltFollow(this, EntityPlayer.class, 1.0D, 128, 24, e -> true));
        }
        //this.targetTasks.addTask(0, new Gestalt<>(this));
    }

    // Rage
    public int getRageCooldown() {
        return this.getDataManager().get(RAGECOOLDOWN);
    }
    public void setRageCooldown(int input) {
        this.getDataManager().set(RAGECOOLDOWN, input);
    }
    public void rageEffect(EntityLivingBase ogEntity) {
        ogEntity.knockBack(ogEntity,3,2,2);
        DifficultyStats.applyPotionEffect(this, MobEffects.STRENGTH, 3, 2);
        DifficultyStats.applyPotionEffect(this, MobEffects.RESISTANCE, 3, 1);
        this.playSound(SoundRegistry.wyrmannoyed,0.9F,(this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F);
        if (ogEntity instanceof EntityPlayerMP && !(ogEntity instanceof EntityCreature)) {
            EntityPlayerMP playerEntity = (EntityPlayerMP) ogEntity;
            Advancements.grantAchievement(playerEntity, Advancements.theycandothat);
        }
        this.setRageCooldown(AI.rageCooldownMax * 20);
    }

    // GeckoLib thing so that way all wyrms share this code automatically. Saves some time.
    public AnimationFactory getFactory() {return this.factory;}

    //TODO: Fix this.
    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        switch(casteType) {
            case 1:
            case 7:
            case 8:
                return false;
            default:
                return true;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAS_TARGET, false);
        this.dataManager.register(RAGECOOLDOWN, 0);
    }

    /**
     * Detects if the Wyrm entity has found a target. Useful for animation.
     * dOESN'T ACTUALLY WORK YET THO.
     *
     * @return boolean
     */
    public boolean hasAttackTarget() {
        if (this.world.isRemote) {
            return this.dataManager.get(HAS_TARGET);
        } else {
            return this.getAttackTarget() != null && !this.getAttackTarget().isDead;
        }
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
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
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


    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (getAttackTarget() == null) {
            this.dataManager.set(HAS_TARGET, false);
        }
        else {
            this.dataManager.set(HAS_TARGET, true);
        }
        this.setRageCooldown(getRageCooldown() - 1);
    }

    // Wyrms now earn points when they kill something.
    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        wyrmKillBonuses.pointIncrease(world);
    }

    // Controls how all wyrms respond to damage.
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof EntityLivingBase && source.getTrueSource() != null) {
            EntityLivingBase entity = (EntityLivingBase) source.getTrueSource();
            EntityLivingBase ogEntity = this.getAttackTarget();
            if (entity != null && ogEntity != null && !(entity instanceof EntityWyrm)) {
                int attentionAdded = 0;
                if (entity instanceof EntityPlayer && attentionAdded == 0 && AI.gestaltUseInfamy) { //Ignore 2nd run of this, so that only 1 infamy is added at a time
                    GestaltHostMind.increaseAttentionLevel(this.world);
                    attentionAdded++;
                }
                // Notify other wyrms

                if (entity instanceof EntityPlayer) {
                    EntityPlayerMP playerEn = (EntityPlayerMP) entity;
                    if (!playerEn.isSpectator() && !playerEn.isCreative()) {
                        this.setAttackTarget(playerEn);
                        List nearbyWyrms = this.world.getEntitiesWithinAABB(EntityWyrm.class, new AxisAlignedBB(
                                this.posX - 30, this.posY - 10, this.posZ - 30, this.posX + 30, this.posY + 10, this.posZ + 30));
                        if (!nearbyWyrms.isEmpty()) {
                            for (Object ent : nearbyWyrms) {
                                if (ent instanceof EntityWyrm) ((EntityWyrm) ent).setAttackTarget(entity);
                            }
                        }
                    }
                }
                else {
                    this.setAttackTarget(entity);
                    List nearbyWyrms = this.world.getEntitiesWithinAABB(EntityWyrm.class, new AxisAlignedBB(
                            this.posX - 30, this.posY - 30, this.posZ - 30, this.posX + 30, this.posY + 30, this.posZ + 30));
                    if (!nearbyWyrms.isEmpty()) {
                        for (Object ent : nearbyWyrms) {
                            if (ent instanceof EntityWyrm) ((EntityWyrm) ent).setAttackTarget(entity);
                        }
                    }
                }

                if (!(this instanceof EntityCreeped) && this.getRageCooldown() <= 0 && this.canEnrage() && AI.rageEnabled && entity != ogEntity && this.getDistance(entity) < 5) rageEffect(ogEntity);
            }
        }

        if (this instanceof EntityCreeped) {
            if (source == DamageSource.FALL && (Radiogenetics.creepedImmuneToFalling && !(this.casteType == 9)))
                return false;
            if (source.isExplosion() && Radiogenetics.creepedImmuneToExplosions)
                return false;
            if (source == DamageSource.CACTUS && Radiogenetics.creepedImmuneToCacti)
                return false;
        }
        else {
            if (source == DamageSource.FALL && (Radiogenetics.immuneToFalling && !(this.casteType == 9)))
                return false;
            if (source.isExplosion() && Radiogenetics.immuneToExplosions)
                return false;
            if (source == DamageSource.CACTUS && Radiogenetics.immuneToCacti)
                return false;
        }
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.IN_WALL)
            return false;
        if (source == DamageSource.ON_FIRE)
            return super.attackEntityFrom(source, amount*3);
        return super.attackEntityFrom(source,amount);
    }

    // Gives all wyrms COTH (from Scape & Run: Parasites) immunity so that way pack makers and unknowing players DON'T have to add it themselves.
    // Credit to Dhantry for helping me figure this out <3 turns out it's not a boolean, it's an integer. I feel stupid but then again SRP is closed-source, so I can't blame myself for being clueless.
    // Other modders are 100% free to use this knowledge to make their entities SRP-compatible too, such as a few mobs in Mowzie's Mobs that are also inorganic. You don't need to credit me, just Dhan for SRP.
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
    }
    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        enabledGestalt();
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1));
        this.tasks.addTask(6, new EntityAIWander(this, 0.85D));
        this.tasks.addTask(0, new EntityAISwimming(this));
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
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

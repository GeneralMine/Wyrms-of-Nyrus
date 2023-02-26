package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.advancements.Advancements;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.WyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ai.VoidwyrmAI;
import com.vetpetmon.wyrmsofnyrus.locallib.ai.movehelpers.FlierMoveHelperGhastlike;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class EntityTheVisitor extends EntityWyrm implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private int dropTimer, timesdropped;

    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);

    public EntityTheVisitor(World world) {
        super(world);
        setSize(20.0f, 3.5f);
        experienceValue = 100;
        this.casteType = 8;
        this.navigator = new PathNavigateFlying(this, this.world);
        this.moveHelper = new FlierMoveHelperGhastlike(this, 200, WyrmStats.visitorSPD, 0.0D);
        this.dropTimer = (20*45); //48 seconds until first cymbal crash, so give 3 seconds to spawn and fall.
        setNoAI(false);
        enablePersistence();
        setPotency(100);
        this.setAnimationNames(new String[]{"visitor.Idle"});
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setStats(WyrmStats.visitorHP, WyrmStats.visitorDEF,0.0F, WyrmStats.visitorSPD, WyrmStats.visitorKBR);
    }
    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new VoidwyrmAI(this, WyrmStats.visitorSPD, 200));
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        Entity entity = source.getTrueSource();
        if (entity instanceof EntityPlayerMP && this.timesdropped == 0) Advancements.grantAchievement((EntityPlayerMP) entity, Advancements.nottoday);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setNoGravity(true);
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    // Both methods are necessary for the boss healthbar to show up. This does NOT wait for the player to attack the Visitor.
    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
        Advancements.grantAchievement(player, Advancements.witness);
    }
    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return null;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {return null;}
    @Override
    public SoundEvent getDeathSound() {return null;}

    @Override
    protected boolean canDespawn() {return true;}

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("dropTimer"))
        {
            this.dropTimer = compound.getInteger("dropTimer");
        }

        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
        this.timesdropped=compound.getInteger("timesdropped");
        if (this.hasCustomName()) this.bossInfo.setName(this.getDisplayName());
    }

    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        this.srpcothimmunity = 0;

        compound.setInteger("dropTimer", this.dropTimer);
        compound.setInteger("timesdropped", this.timesdropped);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.dropTimer <= 0)
        {
            summonPods((int) this.posX, (int) this.posY, (int) this.posZ);
            this.dropTimer = (this.rand.nextInt( Invasion.visitorDropPodFrequencyVariation) + Invasion.visitorDropPodFrequency);
            world.playSound(null, (int) this.posX, (int) this.posY, (int) this.posZ, SoundRegistry.wyrmroars, SoundCategory.MASTER, (float) 100, (float) 0.5);
        }
    }

    public void summonPods(int x, int y, int z) {
        int spawnX;
        int spawnZ;
        Entity entityToSpawn;
        for (int index0 = 0; index0 < (RNG.getIntRangeInclu(2,5)); index0++) {
            spawnX = x + RNG.getIntRangeInclu(-5,5);
            spawnZ = z + RNG.getIntRangeInclu(-5,5);
            if (!world.isRemote) {
                if (RNG.getIntRangeInclu(1,2) == 1){
                    entityToSpawn = new EntityHexePod(world);
                }
                else {
                    entityToSpawn = new EntityCallousPod(world);
                    ((EntityCallousPod) entityToSpawn).setPodType(2,1);
                }
                entityToSpawn.setLocationAndAngles(spawnX, y-5, spawnZ, world.rand.nextFloat() * 360F, 0.0F);
                world.spawnEntity(entityToSpawn);
            }
        }
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20F, this::predicate));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isProjectile())
            return super.attackEntityFrom(source, (float) (amount * Radiogenetics.voidwyrmProjWeakness));
        return super.attackEntityFrom(source, amount);
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(getAnimation(0));

        return PlayState.CONTINUE;
    }

    public boolean isNonBoss()
    {
        return false;
    }

}

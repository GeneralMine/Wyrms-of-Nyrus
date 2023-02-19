package com.vetpetmon.wyrmsofnyrus.entity.hivemind;

import com.vetpetmon.wyrmsofnyrus.entity.ability.creepTheLands;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import com.vetpetmon.synapselib.util.RNG;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Abstract class for all Hivemind handlers.
 *
 * Wyrms naturally gravitate towards different waypoints, though some
 * hivemind waypoints will attract some types of wyrms more than others
 * do.
 */
public abstract class EntityHivemind extends EntityLiving {
    private int alertStrength; // Determines the "power ranking" of wyrms that investigate the waypoint
    private int tickTimer;

    enum waypointGoal {
        INFEST, // Infects blocks in the area with inactive Hive Creep
        BECKON, // Attracts wyrms to an area
        SWARM // Spawns wyrms to an area
    } // End goal for waypoints

    private waypointGoal setGoal;

    public EntityHivemind(World worldIn) {
        super(worldIn);
        this.tickTimer = 200;
        experienceValue = 0;
        setSize(0.05f, 0.05f);
        setNoAI(true);
    }

    public waypointGoal getSetGoal() {
        return setGoal;
    }

    public void setSetGoal(waypointGoal input) {
        setGoal = input;
    }

    public int getAlertStrength() {
        return this.alertStrength;
    }

    public void setAlertStrength(int value) {
        this.alertStrength = value;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote && --this.tickTimer <= 0)
        {
            performGoal(getSetGoal());
            this.tickTimer = 200;
        }
        if (getAlertStrength() <= 0) this.setDead(); // end lifespan of waypoint entity
    }

    public void performGoal(waypointGoal goalIn) {
        waypointGoal goal = goalIn;
        int x,y,z;
        BlockPos pos;
        switch(goal){
            case INFEST:
                creepTheLands.creepTheLands(getPosition(),this.world, 4, this);
                setAlertStrength(getAlertStrength() - (RNG.getIntRangeInclu(0,1)));
                break;
            case BECKON:
                setAlertStrength(getAlertStrength() - (RNG.getIntRangeInclu(0,1)));
                break;
            case SWARM:
                boolean validSpawn = false;
                while (!validSpawn) {
                    x = (int) ((getPosition().getX()) + RNG.PMRange(alertStrength * 2));
                    y = (int) ((getPosition().getY()) + RNG.PMRange(alertStrength * 2));
                    z = (int) ((getPosition().getZ()) + RNG.PMRange(alertStrength * 2));

                    pos = new BlockPos(x,y,z);
                    if (world.canSeeSky(pos) && world.isBlockFullCube(pos) && world.isBlockFullCube(new BlockPos(x,y + 3,z))) validSpawn = true;
                    else if (RNG.dBase(10) == 1) break; //Sometimes checks will fail, prevents infinite loops.
                    if (validSpawn) {
                        Entity entityToSpawn = entitySpawnPicker();
                        entityToSpawn.setLocationAndAngles((x), (y+3), (z), world.rand.nextFloat() * 360F, 0.0F);
                        world.spawnEntity(entityToSpawn);
                        setAlertStrength(getAlertStrength() - (RNG.getIntRangeInclu(0,1)));
                    }
                }

                break;
        }
    }

    private Entity entitySpawnPicker() {
        Entity entityPicked;
        switch(getAlertStrength()) {
            case (2):
                entityPicked = new EntityWyrmRover(this.world);
                break;
            case (3):
                entityPicked = new EntityWyrmSoldier(this.world);
                break;
            case (4):
                entityPicked = new EntityMyrmur(this.world);
                break;
            case (5):
                entityPicked = new EntityWyrmWarrior(this.world);
                break;
            default:
                entityPicked = new EntityWyrmRover(this.world);
                break;
        }
        return entityPicked;
    }

    // Takes no damage. Despawns naturally.
    public boolean attackEntityFrom(DamageSource source, float amount) {return false;}
}

package com.vetpetmon.wyrmsofnyrus.entity.ai.gestalt;

import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.locallib.util.Vect3D;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestaltFollow extends EntityAIBase {

    private Class targetClass;
    private EntityLivingBase target;
    private EntityLiving host;
    private final Sorter targetSorter;
    private final int range, distance;
    private static final int vert = 10;
    private double speed;

    public GestaltFollow(EntityCreature host, Class target, double speedIn, int range, int distance) {
        super();
        this.targetClass = target;
        this.host = host;
        this.speed = speedIn;
        this.range = range;
        this.distance = distance;
        this.targetSorter = new Sorter(host);
    }

    @Override
    public boolean shouldExecute() {
        //WyrmsOfNyrus.logger.info("[GESTALT] preparing to select random target");
        //Finds a random target to gravitate to
        if (AI.gestaltUseInfamy) {
            if ((host.getRNG().nextInt(110) < GestaltHostMind.getAttentionLevel(host.getEntityWorld())) && host.getAttackTarget() == null) {
                //WyrmsOfNyrus.logger.info("[GESTALT] selecting random target");
                findTarget();
                return target != null;
            }
        }
        else if ((host.getRNG().nextInt(110) < 5) && host.getAttackTarget() == null) {
            //WyrmsOfNyrus.logger.info("[GESTALT] selecting random target without considering infamy");
            findTarget();
            return target != null;
        }
        //Don't look for new target otherwise
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {return !this.host.getNavigator().noPath();} //if path is not valid, don't waste effort to reach an unreachable target


    public void startExecuting() {
        Vect3D v = Vect3D.createVector(target.posX - host.posX, target.posY - host.posY, target.posZ - host.posZ);

        int range = distance;
        if (GestaltHostMind.getAttentionLevel(host.world) > 50) range *= 2;
        if (AI.maxInfamyTotalWar && GestaltHostMind.infamyIsMaxed && v.length() < AI.gestaltTotalAwarenessRange * 8) host.setAttackTarget(target);
        else if(AI.gestaltTotalAwareness && v.length() < AI.gestaltTotalAwarenessRange) host.setAttackTarget(target);

        v = v.normalize();
        v.x *= range;v.y *= range;v.z *= range;

        double x,y,z;
        x = host.posX + v.x;
        y = host.posY + v.y - 5 + host.getRNG().nextInt(11);
        z = host.posZ + v.z;
        this.host.getNavigator().tryMoveToXYZ(x, y, z, this.speed);
    }

    @Override
    public void resetTask() {target = null;}

    //scans the area and determines a new target entity
    private void findTarget() {
        int multiplier = 1;
        if (AI.maxInfamyTotalWar && GestaltHostMind.infamyIsMaxed) multiplier = 2;
        List list = host.world.getEntitiesWithinAABB(targetClass, new AxisAlignedBB(
                host.posX - range*multiplier,
                host.posY - vert*multiplier,
                host.posZ - range*multiplier,
                host.posX + range*multiplier,
                host.posY + vert*multiplier,
                host.posZ + range*multiplier));

        Collections.sort(list, targetSorter);

        if (!list.isEmpty()){
            target = (EntityLivingBase) list.get(0);
        }
    }

    public static class Sorter implements Comparator {
        private final Entity host;

        public Sorter(Entity hostEnt) {
            this.host = hostEnt;
        }

        public int compare(Entity firstEntity, Entity secondEntity) {
            double d0 = this.host.getDistanceSq(firstEntity);
            double d1 = this.host.getDistanceSq(secondEntity);
            return Double.compare(d0, d1);
        }

        public int compare(Object firstEntity, Object secondEntity) {
            return this.compare((Entity) firstEntity, (Entity) secondEntity);
        }
    }
}

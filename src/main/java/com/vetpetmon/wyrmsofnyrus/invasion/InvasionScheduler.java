package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.world.World;

public class InvasionScheduler {
    private static int nextDay = 0, nextHalfDay = 0, nextEventTime=0;
    public static int getWorldDays(World world) {
        return (int) Math.floor((int) (world.getWorldTime()/24000));
    }
    public static int getWorldHalfDays(World world) {return (int) Math.floor((int) (world.getTotalWorldTime()/12000));}
    public static int getWorldSchedule(World world) {return (int) Math.floor((int) (world.getTotalWorldTime()/(Invasion.invasionEventFrequency * 1200)));} //1,200 ticks is one minute.
    public static int getWorldQuarterDays(World world) {return (int) Math.floor((int) (world.getTotalWorldTime()/6000));}
    private static int currentDay, currentHalfDay, currentEventTime;

    public static int getCurrentHalfDay() {return currentHalfDay;}

    public static boolean invasionStartCondition() {
        if (Debug.LOGGINGENABLED) {
            wyrmsofnyrus.logger.info("invasionStartTime is " + Invasion.invasionStartTime + ".");
            wyrmsofnyrus.logger.info("invasionStartChance is " + Invasion.invasionStartChance + ".");
        }
        if (Invasion.invasionStartMode > 1) {
            return currentDay >= Invasion.invasionStartTime && (RNG.getIntRangeInclu(0, Invasion.invasionStartChance) == 1);
        } else if (Invasion.invasionStartMode == 1) {
            return currentDay >= Invasion.invasionStartTime;
        } else {
            return RNG.getIntRangeInclu(0, Invasion.invasionStartChance) == 1;
        }
    }

    public static boolean detectDayChange(World world) {
        currentDay = getWorldDays(world);
        if (nextDay > currentDay + 1) nextDay = currentDay;
        else if (currentDay >= nextDay) {
            nextDay = currentDay + 1;
            if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 3) {
                wyrmsofnyrus.logger.info("Day changed detected, current day is now " + currentDay + ".");
                wyrmsofnyrus.logger.info("Day changed detected, next day is now " + nextDay + ".");
            }
            return true;
        }
        return false;
    }

    public static boolean detectHalfDayChange(World world) {
        currentHalfDay = getWorldHalfDays(world);
        if (nextHalfDay > currentHalfDay + 1) nextHalfDay = currentHalfDay;
        else if (currentHalfDay >= nextHalfDay) {
            nextHalfDay = currentHalfDay + 1;
            if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 3) {
                wyrmsofnyrus.logger.info("Half-day changed detected, current half-day is now " + currentHalfDay + ".");
                wyrmsofnyrus.logger.info("Half-day changed detected, next half-day is now " + nextHalfDay + ".");
            }
            return true;
        }
        return false;
    }

    public static boolean runSchedule(World world) {
        currentEventTime = getWorldSchedule(world);
        if (nextEventTime > currentEventTime + 1) nextEventTime = currentEventTime;
        else if (currentEventTime >= nextEventTime) {
            nextEventTime = currentEventTime + 1;
            if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 3) {
                wyrmsofnyrus.logger.info("Current Event time schedule: " + currentEventTime + ".");
                wyrmsofnyrus.logger.info("Next Event scheduled for: " + nextEventTime + ".");
            }
            return true;
        }
        return false;
    }
}

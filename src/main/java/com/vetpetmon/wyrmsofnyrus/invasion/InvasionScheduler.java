package com.vetpetmon.wyrmsofnyrus.invasion;

import com.vetpetmon.wyrmsofnyrus.config.Debug;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.WyrmVariables;
import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.world.World;

public class InvasionScheduler {
    private static int nextDay = 0, nextHalfDay = 0, nextEventTime=0;
    public static int getWorldDays(World world) {
        return (int) Math.floor((int) (world.getWorldTime()/24000));
    }
    public static int getWorldHalfDays(World world) {return (int) Math.floor((int) (world.getWorldTime()/12000));}
    public static int getWorldSchedule(World world) {return (int) Math.floor((int) (world.getWorldTime()/(Invasion.invasionEventFrequency * 1200)));} //1,200 ticks is one minute.
    public static int getWorldQuarterDays(World world) {return (int) Math.floor((int) (world.getWorldTime()/6000));}
    private static int currentDay, currentHalfDay, currentEventTime;

    public static int getCurrentHalfDay() {return currentHalfDay;}

    public static boolean invasionStartCondition() {
        if (Debug.LOGGINGENABLED) {
            WyrmsOfNyrus.logger.info("invasionStartTime is " + Invasion.invasionStartTime + ".");
            WyrmsOfNyrus.logger.info("invasionStartChance is " + Invasion.invasionStartChance + ".");
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
                WyrmsOfNyrus.logger.info("Day changed detected, current day is now " + currentDay + ".");
                WyrmsOfNyrus.logger.info("Day changed detected, next day is now " + nextDay + ".");
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
                WyrmsOfNyrus.logger.info("Half-day changed detected, current half-day is now " + currentHalfDay + ".");
                WyrmsOfNyrus.logger.info("Half-day changed detected, next half-day is now " + nextHalfDay + ".");
            }
            return true;
        }
        return false;
    }

    public static boolean runSchedule(World world) {
        getScheduler(world); // RUN THIS FIRST. Checks world for existing variables before it just takes the values at face-value. This updates both variables that are stored to disk.
        // EXPLANATION OF GHSA-xwmh-4hmj-2vw4 & patch:
        // Because the currentEventTime and nextEventTime variables aren't stored in the world data files, every time this class is initialized,
        // it will take whatever values the two variables have in this class and just run the event scheduler. This is part 1 of the fix.
        // Those who know how to edit class variables in Memory could easily exploit this with automation.
        //
        // By checking what is in the world data files first before changing this class's variables, let's say, World A has these:
        // eventSchedulerCurrentInstance    =   1
        // eventSchedulerNextInstance       =   2
        // And World B hase these:
        // eventSchedulerCurrentInstance    =   3
        // eventSchedulerNextInstance       =   0
        // World A sees this when loaded:
        // currentEventTime     =   1
        // nextEventTime        =   2
        // After that, getWorldSchedule(world) is then ran, but instead of skipping up a lot of numbers on existing worlds, currentEventTime remains unchanged
        // Because currentEventTime is unchanged, it is still less than nextEventTime, and thus does not return true.
        // Player unloads World A and loads World B, variables update accordingly:
        // currentEventTime     =   3
        // nextEventTime        =   0
        //
        // Right after getScheduler(world), an if-statement checks if nextEventTime is not 1 over currentEventTime. If it detects such an issue, it will set
        // nextEventTime to currentEventTime + 1, like so:
        //
        // 1. nextEventTime is at 0
        // 2. currentEventTime is at 3
        // 3. nextEventTime is expected to have a value of 4
        // 3. if nextEventTime is not 4, then set it to 4 and update the world data file
        // 4. currentEventTime is still 3, but nextEventTime was corrected to 4 when sanity check failed.
        // 5. THEN run getWorldSchedule(world), which can potentially update to 4 and trip the next conditional
        //
        // setScheduler(world) is only called once a change is detected, and it saves to the world file. This hopefully stops some variable de-sync.
        //
        // Yes, GHSA-xwmh-4hmj-2vw4 could still happen, but it'd require more steps to perform. Part 2 addresses this by only allowing the server to execute
        // this function at runtime. This is to stop clients from creating the exploit, at least on a remote/networked scope.
        if (nextEventTime != currentEventTime+1) {
            nextEventTime = currentEventTime+1;
            setScheduler(world);
        }
        currentEventTime = getWorldSchedule(world);
        if (nextEventTime > currentEventTime+1) nextEventTime = currentEventTime;
        else if (currentEventTime >= nextEventTime) {
            nextEventTime = currentEventTime+1;
            setScheduler(world); // update world variables and save them to the file
            if (Debug.LOGGINGENABLED && Debug.DEBUGLEVEL >= 3) {
                WyrmsOfNyrus.logger.info("Current Event time schedule: " + currentEventTime + ".");
                WyrmsOfNyrus.logger.info("Next Event scheduled for: " + nextEventTime + ".");
            }
            return true;
        }
        return false;
    }
    public static void getScheduler(World w) {
        currentEventTime = WyrmVariables.WorldVariables.get(w).eventSchedulerCurrentInstance;
        nextEventTime = WyrmVariables.WorldVariables.get(w).eventSchedulerNextInstance;
        if (currentEventTime > getWorldSchedule(w)) {
            WyrmVariables.WorldVariables.get(w).eventSchedulerCurrentInstance = getWorldSchedule(w);
            WyrmVariables.WorldVariables.get(w).syncData(w);
            getScheduler(w);
        }
    }
    private static void setScheduler(World w) {
        WyrmVariables.WorldVariables.get(w).eventSchedulerCurrentInstance = currentEventTime;
        WyrmVariables.WorldVariables.get(w).eventSchedulerNextInstance = nextEventTime;
        WyrmVariables.WorldVariables.get(w).syncData(w);
    }
}

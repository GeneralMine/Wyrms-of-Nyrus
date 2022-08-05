package com.vetpetmon.wyrmsofnyrus.synapselib;

import java.util.concurrent.ThreadLocalRandom;


/**
 * Random Number Generation component
 *
 * Includes various methods for getting numbers generated on the fly, instead of constantly running
 * the same functions over and over again at the most raw format. This RNG simplifier is an
 * RNG coder's best friend by being far easier to understand and faster to type out.
 * Most code that is included is intended for use in Minecraft mods, but can work without.
 */
public class RNG {
    /**
     * Gets a randomly generated whole number (integer) within a specified range.
     * This returns a number in that range, inclusive of Min, but exclusive of Max.
     * Exclusive range is good when calling for the size() of an ArrayList, just
     * make sure to have the resulting code look at the actual index position.
     *
     * Use getIntRangeInclu(Min, Max) instead for an inclusive Max value.
     */
    public static int getIntRange(int Min, int Max)
    {
        return ThreadLocalRandom.current().nextInt(Min, Max);
    }
    public static int getIntRangeInclu(int Min, int Max)
    {
        return ThreadLocalRandom.current().nextInt(Min, Max + 1);
    }

    /**
     * Gets a randomly generated decimal number (Double) within a specified range.
     * This returns a number in that range, inclusive of Min, but exclusive of Max.
     * There is currently no way to reliably make the Max value inclusive for a
     * double.
     */
    public static double getDoubleRange(double Min, double Max) {
        return (ThreadLocalRandom.current().nextDouble() * (Max - Min)) + Min;
    }

    /**
     * A set of digital dice to INSTANTLY generate a random number without needing to use two manual inputs.
     * This shortens it to one input.
     */
    public static int dBase(int Sides)
    {
        return ThreadLocalRandom.current().nextInt(0, Sides + 1);
    }

    /**
     * Tests an RNG component function n amount of times with a given min-max value.
     * Intended for demonstration or debugging purposes only, doesn't return any
     * value at all, prints outputs into the log. Can be ran outside of Minecraft.
     * Returns an integer.
     */
    public static void testRNGI(int timesToRun, int Min, int Max, boolean isInclusive) {
        for (int i = 0; i < timesToRun; i++) {
            if (!isInclusive) {
                System.out.println(getIntRange(Min, Max));
            }
            else {
                System.out.println(getIntRangeInclu(Min, Max));
            }
        }
    }

    /**
     * More shorthands. This one selects a random value between two directions, both directions (positive and negative)
     * have the same length.
     * @param range double
     * @return negative or positive position value
     */
    public static double PMRange(double range) {
        return getDoubleRange(-range,range);
    }

    /**
     * Tests an RNG component function n amount of times with a given min-max value.
     * Intended for demonstration or debugging purposes only, doesn't return any
     * value at all, prints outputs into the log. Can be ran outside of Minecraft.
     * Returns a double.
     */
    public static void testRNGD(int timesToRun, double Min, double Max) {
        for (int i = 0; i < timesToRun; i++) {
            System.out.println(getDoubleRange(Min, Max));
        }
    }

    // Uncomment this for running in other environments.
	/*public static void main(String[] args) {
        	testRNGD(20,0.0,2.5);
        	testRNGI(20,40,100, true);
        	System.out.println(roundDeci(475.3856867347d, 8));
        	System.out.println(roundDeci(475.986585665347f, 4));
	}*/
}

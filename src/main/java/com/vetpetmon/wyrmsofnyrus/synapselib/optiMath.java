package com.vetpetmon.wyrmsofnyrus.synapselib;

/**
 * Number optimimzation component
 *
 * Reduces the numbers of bits used within data values, has been
 * proven to reduce saved data size by ~50% on average, 90% in some
 * cases.
 */
public class optiMath {

    /**
     * Draconic Formula
     *
     * Runs as many optiMath components as the computer can for the given
     * data type. This algorithm can reduce variable size by around 80%,
     * but may introduce a few inconsistencies. Prefers rounding decimals
     * to the hundreths place, can round to the tenth place. Note that
     * both Draconic and Archive Formulas are for doubles and floats.
     * Integers do not need optimization.
     *
     * If accuracy is a concern, please see the Archive Formula instead.
     */
    public static double dracForm(double input) {
        return (roundDeci(input,2));
    }
    public static float dracForm(float input) {
        return (roundDeci(input,2));
    }

    /**
     * Archive Formula
     *
     * Optimizes variable data contents while maintaining some sense
     * of accuracy. Due to limitations, this is still lossy, but reduces
     * data entry size by ~50%. In many other cases, you'd be better off
     * using the Draconic Formula, as it actually handles less data, but
     * the compression is EXTREMELY lossy. Only use the Archive Formula
     * if you need your decimal numbers accurate to the T without having
     * 30 or so decimal places stored.
     *
     * If you get a number with less than 4 decimal points, keep in mind
     * that rounding is still part of this formula.
     */
    public static double arcForm(double input) {
        return (roundDeci(input,4));
    }
    public static float arcForm(float input) {
        return (roundDeci(input,4));
    }

    /**
     * Rounds a number to the desired number of decimal points.
     * Commonly used to go for 2 decimal points, but sometimes we want 4.
     * May still have rounding inconsistencies, but what do we expect from Java?
     * This works with both doubles and floats.
     */
    public static double roundDeci(double input, int places) {
        double rounder = Math.pow(10,places);
        return Math.round(input*rounder)/rounder;
    }
    public static float roundDeci(float input, int places) {
        float rounder = (float) Math.pow(10,places);
        return Math.round(input*rounder)/rounder;
    }
}

package com.vetpetmon.wyrmsofnyrus.synapselib.util;

public class synMath {
    /**
     * Clamps the passed variable within the min-max range.
     * @param input input
     * @param min min allowed
     * @param max max allowed
     * @return clamped value
     */
    public static float clamp(float input, float min, float max){
        float currentVal = input;
        if (input > max) currentVal = max;
        else if (input < min) currentVal = min;
        return currentVal;
    }
    /**
     * Clamps the passed whole number variable within the min-max range.
     * @param input input
     * @param min min allowed
     * @param max max allowed
     * @return clamped value
     */
    public static int clampInt(int input, int min, int max){
        int currentVal = input;
        if (input > max) currentVal = max;
        else if (input < min) currentVal = min;
        return currentVal;
    }
}

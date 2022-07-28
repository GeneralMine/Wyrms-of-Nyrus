package com.vetpetmon.wyrmsofnyrus.synapselib;

public class synMath {
    public static float clamp(float input, float min, float max){
        float currentVal = input;
        if (input > max) currentVal = max;
        else if (input < min) currentVal = min;
        return currentVal;
    }
}

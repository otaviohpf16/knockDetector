package com.nosoftskills.kd.util;

public class FConverter implements TempConverter {
    public float convert(float tempC) {
        return (1.8f * tempC) + 32.0f;
    }

    public int convert(int tempC) {
        return (int) ((1.8f * ((float) tempC)) + 32.0f);
    }
}

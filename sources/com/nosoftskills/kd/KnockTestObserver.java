package com.nosoftskills.kd;

public interface KnockTestObserver {
    public static final int SPEED_HIGH = 2;
    public static final int SPEED_LOW = 0;
    public static final int SPEED_MEDIUM = 1;

    void onSpeedModeChange(int i);

    void onTestEnded(KnockTestResult knockTestResult);

    void onTestStarted();

    void onTestUpdated(float f, float f2, float f3, float f4, float f5, float f6);
}

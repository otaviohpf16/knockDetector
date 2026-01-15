package com.nosoftskills.kd;

import java.util.LinkedList;

public class KnockTestResult {
    private long endTime;
    private boolean finishedOK;
    private boolean isValid;
    private LinkedList<KnockEvent> knockEvents = new LinkedList<>();
    private int samples;
    private long startTime;

    public long getTestDuration() {
        return this.endTime - this.startTime;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setValid(boolean isValid2) {
        this.isValid = isValid2;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime2) {
        this.startTime = startTime2;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime2) {
        this.endTime = endTime2;
    }

    public LinkedList<KnockEvent> getKnockEvents() {
        return this.knockEvents;
    }

    public void setKnockEvents(LinkedList<KnockEvent> knockEvents2) {
        this.knockEvents = knockEvents2;
    }

    public boolean isFinishedOK() {
        return this.finishedOK;
    }

    public void setFinishedOK(boolean finishedOK2) {
        this.finishedOK = finishedOK2;
    }

    public int getSamples() {
        return this.samples;
    }

    public void setSamples(int samples2) {
        this.samples = samples2;
    }
}

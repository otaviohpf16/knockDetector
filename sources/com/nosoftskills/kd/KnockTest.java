package com.nosoftskills.kd;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import org.prowl.torque.remote.ITorqueService;

public class KnockTest {
    public static final long COOLANT_PID = 5;
    public static final boolean DEBUG_MODE = false;
    public static final long DELAY = 100;
    public static final long INTAKE_PID = 15;
    public static final int LOW_SPEED_LIMIT = 10;
    public static final int MAX_PREPARATION_TRIES = 4;
    public static final int MEDIUM_SPEED_LIMIT = 40;
    public static final float MIN_THROTTLE = 75.0f;
    public static final long PERIOD = 200;
    public static final long RPM_PID = 12;
    public static final int SIZE = 512;
    public static final long SPEED_PID = 13;
    public static final float THROTTLE_DIF = 0.6f;
    public static final long THROTTLE_PID = 17;
    public static final long TIMING_PID = 14;
    protected Context context;
    protected int count;
    protected float curCoolant;
    protected float curIntake;
    protected float curRpm;
    protected float curSpeed;
    protected float curThrottle;
    protected float curTiming;
    protected int index;
    protected boolean isStarted;
    protected boolean isTesting;
    protected LinkedList<KnockEvent> knocks;
    protected KnockTestObserver observer;
    protected float prevRpm;
    protected float prevThrottle;
    protected float prevTiming;
    protected float[] rpms;
    protected int speedMode = 0;
    protected boolean stopped;
    protected boolean stoppedUpdate;
    protected float[] throttles;
    protected float[] timings;
    protected ITorqueService torqueService;
    protected int tries;
    protected Timer updateTimer;

    public KnockTest(KnockTestObserver observer2) {
        this.observer = observer2;
        this.stopped = true;
        this.index = 0;
        this.throttles = new float[512];
        this.rpms = new float[512];
        this.timings = new float[512];
        this.isTesting = false;
        this.knocks = new LinkedList<>();
    }

    public void start() {
        if (!this.isStarted) {
            this.isStarted = true;
            this.isTesting = false;
            this.stopped = false;
            this.stoppedUpdate = false;
            this.updateTimer = new Timer();
            this.updateTimer.schedule(new TimerTask() {
                public void run() {
                    KnockTest.this.testValues();
                }
            }, 100, 200);
        }
    }

    public void stop() {
        if (!this.stopped) {
            this.updateTimer.cancel();
            this.isStarted = false;
            this.isTesting = false;
            this.stopped = true;
            this.stoppedUpdate = true;
            this.count = 0;
            this.knocks.clear();
        }
    }

    public boolean isTesting() {
        return this.isTesting;
    }

    public void setTesting(boolean isTesting2) {
        this.isTesting = isTesting2;
    }

    /* access modifiers changed from: private */
    public void testValues() {
        if (!this.stoppedUpdate && this.torqueService != null) {
            this.prevThrottle = this.curThrottle;
            this.prevRpm = this.curRpm;
            this.prevTiming = this.curTiming;
            try {
                this.curThrottle = this.torqueService.getValueForPid(17, true);
                this.curRpm = this.torqueService.getValueForPid(12, true);
                this.curTiming = this.torqueService.getValueForPid(14, true);
                this.curSpeed = this.torqueService.getValueForPid(13, true);
                this.curCoolant = this.torqueService.getValueForPid(5, true);
                this.curIntake = this.torqueService.getValueForPid(15, true);
                if (this.curSpeed < 10.0f && this.speedMode != 0) {
                    this.speedMode = 0;
                    this.observer.onSpeedModeChange(0);
                } else if (this.curSpeed >= 10.0f && this.speedMode != 1) {
                    this.speedMode = 1;
                    this.observer.onSpeedModeChange(1);
                }
                if (this.curThrottle <= 75.0f || Math.abs(this.curThrottle - this.prevThrottle) > 0.6f) {
                    if (this.isTesting) {
                        if (this.curThrottle < this.prevThrottle) {
                            this.isTesting = false;
                            this.isStarted = false;
                            this.tries = 0;
                            KnockTestResult result = new KnockTestResult();
                            result.setFinishedOK(true);
                            result.setKnockEvents(this.knocks);
                            result.setSamples(this.count);
                            this.stoppedUpdate = true;
                            this.observer.onTestEnded(result);
                        } else if (this.curThrottle >= 75.0f) {
                            if (this.tries > 0) {
                                this.tries = 1;
                            }
                            this.knocks.clear();
                        }
                    } else if (this.curThrottle < this.prevThrottle) {
                        this.tries = 0;
                    } else if (this.curThrottle >= 75.0f && this.tries > 0) {
                        this.tries--;
                    }
                } else if (this.isTesting) {
                    this.count++;
                    if ((this.curTiming < this.prevTiming || this.curRpm <= this.prevRpm) && this.curTiming < this.prevTiming && this.curRpm > this.prevRpm && this.knocks.size() < 100) {
                        KnockEvent knock = new KnockEvent();
                        knock.timestamp = new Date().getTime();
                        knock.timing = this.curTiming;
                        knock.correction = this.curTiming - this.prevTiming;
                        knock.rpm = this.curRpm;
                        knock.speed = this.curSpeed;
                        this.knocks.add(knock);
                    }
                } else if (this.curTiming >= this.prevTiming && this.curRpm >= this.prevRpm) {
                    this.tries++;
                    if (this.tries == 4) {
                        this.isTesting = true;
                        this.tries = 0;
                        this.observer.onTestStarted();
                    }
                } else if (this.tries > 0) {
                    this.tries--;
                }
                if (!this.stoppedUpdate) {
                    this.observer.onTestUpdated(this.curRpm, this.curThrottle, this.curTiming, this.curSpeed, this.curCoolant, this.curIntake);
                }
            } catch (RemoteException e) {
                this.isStarted = false;
                this.isTesting = false;
                this.stopped = true;
                this.tries = 0;
                this.count = 0;
                KnockTestResult result2 = new KnockTestResult();
                result2.setFinishedOK(false);
                result2.setKnockEvents(this.knocks);
                this.observer.onTestEnded(result2);
                Log.e(getClass().getCanonicalName(), e.getMessage(), e);
            }
        }
    }

    public ITorqueService getTorqueService() {
        return this.torqueService;
    }

    public void setTorqueService(ITorqueService torqueService2) {
        this.torqueService = torqueService2;
    }
}

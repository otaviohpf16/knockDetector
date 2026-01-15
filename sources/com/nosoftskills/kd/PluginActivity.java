package com.nosoftskills.kd;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.nosoftskills.kd.util.CConverter;
import com.nosoftskills.kd.util.FConverter;
import com.nosoftskills.kd.util.TempConverter;
import java.text.NumberFormat;
import java.util.Iterator;
import org.prowl.torque.remote.ITorqueService;

public class PluginActivity extends Activity implements KnockTestObserver {
    public static final int COOLANT_TEMP_HIGH = 102;
    public static final int COOLANT_TEMP_LOW = 75;
    public static final int INTAKE_TEMP_HIGH = 40;
    public static final char degree = '°';
    public static final String degreeC = "°C";
    private AudioManager audio;
    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName arg0, IBinder service) {
            PluginActivity.this.torqueService = ITorqueService.Stub.asInterface(service);
            try {
                PluginActivity.this.torqueService.setDebugTestMode(false);
                if (PluginActivity.this.torqueService.getPreferredUnit(PluginActivity.degreeC).equals(PluginActivity.degreeC)) {
                    PluginActivity.this.tempConverter = new CConverter();
                } else {
                    PluginActivity.this.tempConverter = new FConverter();
                }
            } catch (RemoteException e) {
                Log.e(getClass().getCanonicalName(), e.getMessage(), e);
            }
            PluginActivity.this.test.setTorqueService(PluginActivity.this.torqueService);
            PluginActivity.this.test.start();
        }

        public void onServiceDisconnected(ComponentName name) {
            PluginActivity.this.torqueService = null;
        }
    };
    /* access modifiers changed from: private */
    public TextView coolantTV;
    /* access modifiers changed from: private */
    public int curCoolant;
    /* access modifiers changed from: private */
    public int curIntake;
    /* access modifiers changed from: private */
    public int curThrottle;
    /* access modifiers changed from: private */
    public int drawableIndex;
    /* access modifiers changed from: private */
    public Drawable[] draws;
    private Handler handler;
    private int height;
    /* access modifiers changed from: private */
    public Drawable[] idles = new Drawable[4];
    /* access modifiers changed from: private */
    public ImageView image;
    private int index;
    /* access modifiers changed from: private */
    public TextView intakeTV;
    private boolean lowSpeed;
    private boolean lowSpeedLoaded = true;
    /* access modifiers changed from: private */
    public MediaPlayer mPlayer;
    private PowerManager.WakeLock mWakeLock;
    /* access modifiers changed from: private */
    public NumberFormat nf;
    private TextView pRV;
    /* access modifiers changed from: private */
    public TextView percents;
    /* access modifiers changed from: private */
    public String popResults;
    private int prevCoolant;
    private int prevIntake;
    private int prevThrottle;
    /* access modifiers changed from: private */
    public Button restartBtn;
    /* access modifiers changed from: private */
    public Button resultsBtn;
    private int soundLevel;
    private int speedMode;
    private boolean stillImageLoaded = false;
    /* access modifiers changed from: private */
    public TempConverter tempConverter;
    /* access modifiers changed from: private */
    public KnockTest test;
    /* access modifiers changed from: private */
    public Drawable testEndedImage;
    /* access modifiers changed from: private */
    public Drawable testEndedNDImage;
    /* access modifiers changed from: private */
    public Drawable testEndedPDImage;
    /* access modifiers changed from: private */
    public boolean testSuitStarted = false;
    /* access modifiers changed from: private */
    public Drawable[] testings = new Drawable[4];
    private TelephonyManager tm;
    /* access modifiers changed from: private */
    public ITorqueService torqueService;
    /* access modifiers changed from: private */
    public ProgressBar tp;
    /* access modifiers changed from: private */
    public boolean updateCoolant;
    /* access modifiers changed from: private */
    public boolean updateIntake;
    /* access modifiers changed from: private */
    public boolean updateThrottle;
    private int width;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tm = (TelephonyManager) getSystemService("phone");
        this.speedMode = 0;
        this.idles[0] = getResources().getDrawable(R.drawable.ki1);
        this.idles[1] = getResources().getDrawable(R.drawable.ki2);
        this.idles[2] = getResources().getDrawable(R.drawable.ki3);
        this.idles[3] = getResources().getDrawable(R.drawable.ki4);
        this.testings[0] = getResources().getDrawable(R.drawable.kt1);
        this.testings[1] = getResources().getDrawable(R.drawable.kt2);
        this.testings[2] = getResources().getDrawable(R.drawable.kt3);
        this.testings[3] = getResources().getDrawable(R.drawable.kt4);
        this.testEndedImage = getResources().getDrawable(R.drawable.ke);
        this.testEndedNDImage = getResources().getDrawable(R.drawable.ken);
        this.testEndedPDImage = getResources().getDrawable(R.drawable.kep);
        this.draws = this.idles;
        this.testSuitStarted = true;
        this.mWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(10, "KD");
        this.mWakeLock.acquire();
        Display display = getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = display.getHeight();
        setRequestedOrientation(1);
        setContentView(R.layout.images);
        this.image = (ImageView) findViewById(R.id.iv);
        this.image.setImageDrawable(getResources().getDrawable(R.drawable.ki1));
        this.restartBtn = (Button) findViewById(R.id.restartBtn);
        this.restartBtn.setVisibility(4);
        this.restartBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PluginActivity.this.restartBtn.setVisibility(4);
                PluginActivity.this.resultsBtn.setVisibility(4);
                PluginActivity.this.test.start();
                PluginActivity.this.draws = PluginActivity.this.idles;
                PluginActivity.this.testSuitStarted = true;
                PluginActivity.this.image.setImageDrawable(PluginActivity.this.idles[0]);
            }
        });
        this.resultsBtn = (Button) findViewById(R.id.resultsBtn);
        this.resultsBtn.setVisibility(4);
        this.resultsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PluginActivity.this.displayPopup();
            }
        });
        this.percents = (TextView) findViewById(R.id.percents);
        this.coolantTV = (TextView) findViewById(R.id.coolantTV);
        this.intakeTV = (TextView) findViewById(R.id.airTV);
        this.nf = NumberFormat.getInstance();
        this.nf.setMaximumFractionDigits(2);
        this.handler = new Handler();
        this.audio = (AudioManager) getSystemService("audio");
        this.soundLevel = this.audio.getStreamVolume(3);
        int max = this.audio.getStreamMaxVolume(3);
        this.mPlayer = MediaPlayer.create(this, R.raw.pb);
        this.audio.setStreamVolume(3, max, 2);
        this.mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                PluginActivity.this.mPlayer.seekTo(0);
            }
        });
        this.tp = (ProgressBar) findViewById(R.id.tp);
        this.test = new KnockTest(this);
    }

    public void onSpeedModeChange(int newSpeedMode) {
        this.speedMode = newSpeedMode;
        if (newSpeedMode == 0 && !this.lowSpeedLoaded) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    PluginActivity.this.image.setImageDrawable(PluginActivity.this.idles[0]);
                }
            });
            this.lowSpeedLoaded = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setClassName("org.prowl.torque", "org.prowl.torque.remote.TorqueService");
        boolean bindService = bindService(intent, this.connection, 0);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.test.stop();
        unbindService(this.connection);
        this.audio.setStreamVolume(3, this.soundLevel, 2);
    }

    public void onTestStarted() {
        this.handler.post(new Runnable() {
            public void run() {
                PluginActivity.this.draws = PluginActivity.this.testings;
                PluginActivity.this.playSound();
                PluginActivity.this.resultsBtn.setVisibility(4);
                PluginActivity.this.restartBtn.setVisibility(4);
                PluginActivity.this.image.setImageDrawable(PluginActivity.this.idles[0]);
            }
        });
    }

    public void onTestEnded(final KnockTestResult result) {
        this.handler.post(new Runnable() {
            public void run() {
                PluginActivity.this.testSuitStarted = false;
                if (result.isFinishedOK()) {
                    int count = result.getKnockEvents().size();
                    if (count == 0) {
                        PluginActivity.this.image.setImageDrawable(PluginActivity.this.testEndedNDImage);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("KNOCK DETECTOR RESULTS\n" + count);
                        if (count == 1) {
                            sb.append(" knock event");
                        } else {
                            sb.append(" knock events");
                        }
                        sb.append(" from " + result.getSamples() + " samples");
                        int i = 0;
                        Iterator it = result.getKnockEvents().iterator();
                        while (it.hasNext()) {
                            KnockEvent knock = (KnockEvent) it.next();
                            i++;
                            sb.append("\nKnock event " + i + ": \nRPM: " + PluginActivity.this.nf.format((double) knock.rpm) + ", speed: " + PluginActivity.this.nf.format((double) knock.speed) + "\nTiming: " + PluginActivity.this.nf.format((double) knock.timing) + ", correction: " + PluginActivity.this.nf.format((double) knock.correction));
                        }
                        PluginActivity.this.popResults = sb.toString();
                        PluginActivity.this.resultsBtn.setVisibility(0);
                        PluginActivity.this.image.setImageDrawable(PluginActivity.this.testEndedPDImage);
                    }
                } else {
                    PluginActivity.this.image.setImageDrawable(PluginActivity.this.testEndedImage);
                }
                PluginActivity.this.test.stop();
                PluginActivity.this.playSound();
                PluginActivity.this.restartBtn.setVisibility(0);
            }
        });
    }

    public void onTestUpdated(float rpm, float throttle, float timing, float speed, float coolant, float intake) {
        this.curThrottle = (int) throttle;
        this.curCoolant = (int) this.tempConverter.convert(coolant);
        this.curIntake = (int) this.tempConverter.convert(intake);
        this.updateThrottle = false;
        this.updateCoolant = false;
        this.updateIntake = false;
        if (this.curThrottle != this.prevThrottle) {
            this.prevThrottle = this.curThrottle;
            this.updateThrottle = true;
        }
        if (this.curCoolant != this.prevCoolant) {
            this.prevCoolant = this.curCoolant;
            this.updateCoolant = true;
        }
        if (this.curIntake != this.prevIntake) {
            this.prevIntake = this.curIntake;
            this.updateIntake = true;
        }
        if (this.updateThrottle || this.updateCoolant || this.updateIntake) {
            this.handler.post(new Runnable() {
                public void run() {
                    if (PluginActivity.this.updateThrottle) {
                        PluginActivity.this.tp.setProgress(PluginActivity.this.curThrottle);
                        PluginActivity.this.percents.setText(String.valueOf(PluginActivity.this.curThrottle) + "%");
                    }
                    if (PluginActivity.this.updateCoolant) {
                        if (PluginActivity.this.curCoolant < PluginActivity.this.tempConverter.convert(75)) {
                            PluginActivity.this.coolantTV.setTextColor(PluginActivity.this.getResources().getColor(R.color.result_blue));
                        } else if (PluginActivity.this.curCoolant < PluginActivity.this.tempConverter.convert(75) || PluginActivity.this.curCoolant >= PluginActivity.this.tempConverter.convert((int) PluginActivity.COOLANT_TEMP_HIGH)) {
                            PluginActivity.this.coolantTV.setTextColor(PluginActivity.this.getResources().getColor(R.color.result_red));
                        } else {
                            PluginActivity.this.coolantTV.setTextColor(PluginActivity.this.getResources().getColor(R.color.result_green));
                        }
                        PluginActivity.this.coolantTV.setText(" " + PluginActivity.this.curCoolant + PluginActivity.degree);
                    }
                    if (PluginActivity.this.updateIntake) {
                        if (PluginActivity.this.curIntake < PluginActivity.this.tempConverter.convert(40)) {
                            PluginActivity.this.intakeTV.setTextColor(PluginActivity.this.getResources().getColor(R.color.result_green));
                        } else {
                            PluginActivity.this.intakeTV.setTextColor(PluginActivity.this.getResources().getColor(R.color.result_lightred));
                        }
                        PluginActivity.this.intakeTV.setText(" " + PluginActivity.this.curIntake + PluginActivity.degree);
                    }
                }
            });
        }
        if (this.speedMode != 0) {
            this.index++;
            if (this.index % 3 == 0) {
                this.drawableIndex++;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        PluginActivity.this.image.setImageDrawable(PluginActivity.this.draws[PluginActivity.this.drawableIndex % 4]);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void displayPopup() {
        View layout = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popup));
        final PopupWindow pw = new PopupWindow(layout, this.width - 40, this.height - 100, true);
        this.pRV = (TextView) layout.findViewById(R.id.pResultsView);
        this.pRV.setMovementMethod(new ScrollingMovementMethod());
        pw.showAtLocation(findViewById(R.id.mainLL), 17, 0, 0);
        if (this.popResults != null) {
            this.pRV.setText(this.popResults);
        }
        ((Button) layout.findViewById(R.id.pOkBtn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void playSound() {
        if (this.tm.getCallState() != 1 && this.tm.getCallState() != 2) {
            try {
                if (!this.mPlayer.isPlaying()) {
                    this.mPlayer.start();
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        this.mWakeLock.release();
        this.mPlayer.release();
        super.onDestroy();
    }
}

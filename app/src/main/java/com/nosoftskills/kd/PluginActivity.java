package com.nosoftskills.kd;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.nosoftskills.kd.util.CConverter;
import com.nosoftskills.kd.util.FConverter;
import com.nosoftskills.kd.util.TempConverter;

import java.text.NumberFormat;
import java.util.Iterator;

import org.prowl.torque.remote.ITorqueService;

public class PluginActivity extends AppCompatActivity implements KnockTestObserver {
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
                Log.e("KD", e.getMessage(), e);
            }
            PluginActivity.this.test.setTorqueService(PluginActivity.this.torqueService);
            PluginActivity.this.test.start();
        }

        public void onServiceDisconnected(ComponentName name) {
            PluginActivity.this.torqueService = null;
        }
    };

    private TextView coolantTV;
    private int curCoolant;
    private int curIntake;
    private int curThrottle;
    private int drawableIndex;
    private Drawable[] draws;
    private Handler handler;
    private int height;
    private Drawable[] idles = new Drawable[4];
    private ImageView image;
    private int index;
    private TextView intakeTV;
    private boolean lowSpeedLoaded = true;
    private MediaPlayer mPlayer;
    private NumberFormat nf;
    private TextView pRV;
    private TextView percents;
    private String popResults;
    private int prevCoolant;
    private int prevIntake;
    private int prevThrottle;
    private Button restartBtn;
    private Button resultsBtn;
    private int soundLevel;
    private int speedMode;
    private TempConverter tempConverter;
    private KnockTest test;
    private Drawable testEndedImage;
    private Drawable testEndedNDImage;
    private Drawable testEndedPDImage;
    private boolean testSuitStarted = false;
    private Drawable[] testings = new Drawable[4];
    private TelephonyManager tm;
    private ITorqueService torqueService;
    private ProgressBar tp;
    private boolean updateCoolant;
    private boolean updateIntake;
    private boolean updateThrottle;
    private int width;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Mantém a tela ligada sem precisar de permissão WakeLock manual
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.tm = (TelephonyManager) getSystemService("phone");
        this.speedMode = 0;
        
        // Carregamento de recursos usando o método compatível
        this.idles[0] = ContextCompat.getDrawable(this, R.drawable.ki1);
        this.idles[1] = ContextCompat.getDrawable(this, R.drawable.ki2);
        this.idles[2] = ContextCompat.getDrawable(this, R.drawable.ki3);
        this.idles[3] = ContextCompat.getDrawable(this, R.drawable.ki4);
        
        this.testings[0] = ContextCompat.getDrawable(this, R.drawable.kt1);
        this.testings[1] = ContextCompat.getDrawable(this, R.drawable.kt2);
        this.testings[2] = ContextCompat.getDrawable(this, R.drawable.kt3);
        this.testings[3] = ContextCompat.getDrawable(this, R.drawable.kt4);
        
        this.testEndedImage = ContextCompat.getDrawable(this, R.drawable.ke);
        this.testEndedNDImage = ContextCompat.getDrawable(this, R.drawable.ken);
        this.testEndedPDImage = ContextCompat.getDrawable(this, R.drawable.kep);
        
        this.draws = this.idles;
        this.testSuitStarted = true;

        // Medição de tela moderna (API 30+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            Rect bounds = windowMetrics.getBounds();
            this.width = bounds.width();
            this.height = bounds.height();
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            this.width = size.x;
            this.height = size.y;
        }

        setContentView(R.layout.images);
        
        this.image = findViewById(R.id.iv);
        this.image.setImageDrawable(idles[0]);
        
        this.restartBtn = findViewById(R.id.restartBtn);
        this.restartBtn.setVisibility(View.INVISIBLE);
        this.restartBtn.setOnClickListener(v -> {
            restartBtn.setVisibility(View.INVISIBLE);
            resultsBtn.setVisibility(View.INVISIBLE);
            test.start();
            draws = idles;
            testSuitStarted = true;
            image.setImageDrawable(idles[0]);
        });

        this.resultsBtn = findViewById(R.id.resultsBtn);
        this.resultsBtn.setVisibility(View.INVISIBLE);
        this.resultsBtn.setOnClickListener(v -> displayPopup());

        this.percents = findViewById(R.id.percents);
        this.coolantTV = findViewById(R.id.coolantTV);
        this.intakeTV = findViewById(R.id.airTV);
        
        this.nf = NumberFormat.getInstance();
        this.nf.setMaximumFractionDigits(2);
        
        // Handler com Looper explícito (Obrigatório em APIs recentes)
        this.handler = new Handler(Looper.getMainLooper());
        
        this.audio = (AudioManager) getSystemService("audio");
        this.soundLevel = this.audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = this.audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        
        this.mPlayer = MediaPlayer.create(this, R.raw.pb);
        this.audio.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);
        this.mPlayer.setOnCompletionListener(mp -> mPlayer.seekTo(0));

        this.tp = findViewById(R.id.tp);
        this.test = new KnockTest(this);
    }

    public void onSpeedModeChange(int newSpeedMode) {
        this.speedMode = newSpeedMode;
        if (newSpeedMode == 0 && !this.lowSpeedLoaded) {
            new Handler(Looper.getMainLooper()).post(() -> image.setImageDrawable(idles[0]));
            this.lowSpeedLoaded = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setClassName("org.prowl.torque", "org.prowl.torque.remote.TorqueService");
        bindService(intent, this.connection, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.test.stop();
        unbindService(this.connection);
        this.audio.setStreamVolume(AudioManager.STREAM_MUSIC, this.soundLevel, 0);
    }

    public void onTestStarted() {
        this.handler.post(() -> {
            draws = testings;
            playSound();
            resultsBtn.setVisibility(View.INVISIBLE);
            restartBtn.setVisibility(View.INVISIBLE);
            image.setImageDrawable(idles[0]);
        });
    }

    public void onTestEnded(final KnockTestResult result) {
        this.handler.post(() -> {
            testSuitStarted = false;
            if (result.isFinishedOK()) {
                int count = result.getKnockEvents().size();
                if (count == 0) {
                    image.setImageDrawable(testEndedNDImage);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("KNOCK DETECTOR RESULTS\n").append(count);
                    sb.append(count == 1 ? " knock event" : " knock events");
                    sb.append(" from ").append(result.getSamples()).append(" samples");
                    
                    int i = 0;
                    for (Object obj : result.getKnockEvents()) {
                        KnockEvent knock = (KnockEvent) obj;
                        i++;
                        sb.append("\nKnock event ").append(i).append(": \nRPM: ")
                          .append(nf.format(knock.rpm)).append(", speed: ")
                          .append(nf.format(knock.speed)).append("\nTiming: ")
                          .append(nf.format(knock.timing)).append(", correction: ")
                          .append(nf.format(knock.correction));
                    }
                    popResults = sb.toString();
                    resultsBtn.setVisibility(View.VISIBLE);
                    image.setImageDrawable(testEndedPDImage);
                }
            } else {
                image.setImageDrawable(testEndedImage);
            }
            test.stop();
            playSound();
            restartBtn.setVisibility(View.VISIBLE);
        });
    }

    public void onTestUpdated(float rpm, float throttle, float timing, float speed, float coolant, float intake) {
        this.curThrottle = (int) throttle;
        this.curCoolant = (int) this.tempConverter.convert(coolant);
        this.curIntake = (int) this.tempConverter.convert(intake);
        this.updateThrottle = (curThrottle != prevThrottle);
        this.updateCoolant = (curCoolant != prevCoolant);
        this.updateIntake = (curIntake != prevIntake);

        if (this.updateThrottle) this.prevThrottle = this.curThrottle;
        if (this.updateCoolant) this.prevCoolant = this.curCoolant;
        if (this.updateIntake) this.prevIntake = this.curIntake;

        if (this.updateThrottle || this.updateCoolant || this.updateIntake) {
            this.handler.post(() -> {
                if (updateThrottle) {
                    tp.setProgress(curThrottle);
                    percents.setText(curThrottle + "%");
                }
                if (updateCoolant) {
                    if (curCoolant < tempConverter.convert(75)) {
                        coolantTV.setTextColor(ContextCompat.getColor(this, R.color.result_blue));
                    } else if (curCoolant >= tempConverter.convert(COOLANT_TEMP_HIGH)) {
                        coolantTV.setTextColor(ContextCompat.getColor(this, R.color.result_red));
                    } else {
                        coolantTV.setTextColor(ContextCompat.getColor(this, R.color.result_green));
                    }
                    coolantTV.setText(" " + curCoolant + degree);
                }
                if (updateIntake) {
                    if (curIntake < tempConverter.convert(40)) {
                        intakeTV.setTextColor(ContextCompat.getColor(this, R.color.result_green));
                    } else {
                        intakeTV.setTextColor(ContextCompat.getColor(this, R.color.result_lightred));
                    }
                    intakeTV.setText(" " + curIntake + degree);
                }
            });
        }
        
        if (this.speedMode != 0) {
            this.index++;
            if (this.index % 3 == 0) {
                this.drawableIndex++;
                new Handler(Looper.getMainLooper()).post(() -> 
                    image.setImageDrawable(draws[drawableIndex % 4])
                );
            }
        }
    }

    private void displayPopup() {
        View layout = getLayoutInflater().inflate(R.layout.popup, findViewById(R.id.popup));
        final PopupWindow pw = new PopupWindow(layout, width - 40, height - 100, true);
        this.pRV = layout.findViewById(R.id.pResultsView);
        this.pRV.setMovementMethod(new ScrollingMovementMethod());
        pw.showAtLocation(findViewById(R.id.mainLL), 17, 0, 0);
        if (this.popResults != null) {
            this.pRV.setText(this.popResults);
        }
        layout.findViewById(R.id.pOkBtn).setOnClickListener(v -> pw.dismiss());
    }

    private void playSound() {
        // No Android 12+, o estado da chamada requer permissão explícita.
        // Se não tiver permissão, o som toca de qualquer forma para evitar crash.
        boolean isInCall = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                isInCall = tm.getCallState() != TelephonyManager.CALL_STATE_IDLE;
            }
        } else {
            isInCall = tm.getCallState() != TelephonyManager.CALL_STATE_IDLE;
        }

        if (!isInCall) {
            try {
                if (mPlayer != null && !mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            } catch (Exception e) {
                Log.e("KD", "Erro ao tocar áudio", e);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }
}

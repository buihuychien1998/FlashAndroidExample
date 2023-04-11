package com.demo.example.OS;

import android.app.KeyguardManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.OS.screen.BaseScreen;
import com.demo.example.OS.screen.iphone.ScreenIphone;
import com.demo.example.OS.screen.mate.ScreenMate;
import com.demo.example.OS.screen.pixel.ScreenPixel;
import com.demo.example.OS.screen.s20.ScreenS20;
import com.demo.example.OS.service.CallManager;
import com.demo.example.OS.until.MyShare;
import com.demo.example.OS.until.OtherUntil;


public class ActivityFakeCall extends AppCompatActivity implements SensorEventListener, BaseScreen.ScreenResult, BaseScreen.FakeResult {
    private BaseScreen baseScreen;
    private Bitmap bm;
    private ImageView im;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private MediaPlayer mediaPlayer;
    private Vibrator myVib;
    private String photo;
    private PowerManager pm;


    Context mContext;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityFakeCall.this.baseScreen.callStatus(intent.getIntExtra("data", -1));
        }
    };
    private VideoView vv;

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onNum(String str) {
    }

    @Override
    public void onRecorder() {
    }


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerReceiver(this.receiver, new IntentFilter(CallManager.ACTION_CALL));
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(1792);
        getWindow().setNavigationBarColor(0);
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(0);
        setContentView(R.layout.activity_call);

        mContext = this;
        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService("keyguard");
        String stringExtra = getIntent().getStringExtra("num");
        int intExtra = getIntent().getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        if (stringExtra == null) {
            stringExtra = "";
        }
        int style = MyShare.getStyle(this);
        if (style == 0) {
            this.baseScreen = new ScreenIphone(this);
        } else if (style == 1) {
            this.baseScreen = new ScreenS20(this);
        } else if (style != 2) {
            this.baseScreen = new ScreenMate(this);
        } else {
            this.baseScreen = new ScreenPixel(this);
        }
        if (intExtra != -1) {
            this.baseScreen.callStatus(intExtra);
        }
        this.baseScreen.setScreenResult(null, this);
        this.baseScreen.setFakeResult(this);
        this.baseScreen.setName(stringExtra);
        ((RelativeLayout) findViewById(R.id.rl_main)).addView(this.baseScreen, new RelativeLayout.LayoutParams(-1, -1));
        initView();
        initBg();
        SensorManager sensorManager = (SensorManager) getSystemService("sensor");
        this.mSensorManager = sensorManager;
        if (sensorManager != null) {
            this.mSensor = sensorManager.getDefaultSensor(8);
        }
        this.pm = (PowerManager) getSystemService("power");
        turnOn();
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            if (keyguardManager != null) {
                keyguardManager.requestDismissKeyguard(this, null);
            }
        } else {
            getWindow().addFlags(6815744);
        }
        Vibrator vibrator = (Vibrator) getSystemService("vibrator");
        this.myVib = vibrator;
        long[] jArr = {0, 100, 100, 100, 200, 100, 100, 100};
        if (vibrator != null) {
            vibrator.vibrate(jArr, 1);
        }
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.setDataSource(this, RingtoneManager.getDefaultUri(1));
            AudioManager audioManager = (AudioManager) getSystemService("audio");
            if (audioManager != null && audioManager.getStreamVolume(2) != 0) {
                this.mediaPlayer.setAudioStreamType(2);
                this.mediaPlayer.setLooping(true);
                this.mediaPlayer.prepare();
                this.mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBg() {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public final boolean handleMessage(Message message) {
                return ActivityFakeCall.this.m2x8c9147ae(message);
            }
        });
        new Thread(new Runnable() {
            @Override
            public final void run() {
                ActivityFakeCall.this.m3xa6acc64d(handler);
            }
        }).start();
    }


    public boolean m2x8c9147ae(Message message) {
        Bitmap bitmap = this.bm;
        if (bitmap != null) {
            this.im.setImageBitmap(bitmap);
            this.vv.setVisibility(View.GONE);
            return true;
        } else if (this.photo.contains(".gif")) {
            Glide.with((FragmentActivity) this).load(this.photo).into(this.im);
            return true;
        } else {
            this.im.setVisibility(View.GONE);
            this.vv.setVideoURI(Uri.parse(this.photo));
            this.vv.start();
            return true;
        }
    }


    public void m3xa6acc64d(Handler handler) {
        try {
            String photo = MyShare.getPhoto(this);
            this.photo = photo;
            if (photo.isEmpty() || this.photo.toLowerCase().contains(".jpg") || this.photo.toLowerCase().contains(".png")) {
                try {
                    this.bm = BitmapFactory.decodeFile(this.photo);
                } catch (Exception unused) {
                    this.bm = null;
                }
                if (this.bm == null) {
                    try {
                        this.bm = ((BitmapDrawable) WallpaperManager.getInstance(this).getDrawable()).getBitmap();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (this.bm == null) {
                    this.bm = BitmapFactory.decodeResource(getResources(), R.drawable.im_bg_call);
                }
                this.bm = OtherUntil.fastblur(this.bm, 0.5f, 25);
            }
            handler.sendEmptyMessage(1);
        } catch (SecurityException unused2) {
        }
    }


    private void initView() {
        VideoView videoView = (VideoView) findViewById(R.id.vv);
        this.vv = videoView;
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
                mp.setVolume(0.0f, 0.0f);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.im_bg);
        this.im = imageView;
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    private void turnOn() {
        this.vv.start();
        PowerManager powerManager = this.pm;
        if (powerManager != null) {
            powerManager.newWakeLock(268435466, "tag").acquire();
        }
    }

    private void turnOff() {
        PowerManager powerManager = this.pm;
        if (powerManager != null) {
            powerManager.newWakeLock(32, "tag").acquire();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            sensorManager.registerListener(this, this.mSensor, 3);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] == 0.0f) {
            turnOff();
        } else {
            turnOn();
        }
    }

    @Override
    public void onBackPressed() {
        OtherUntil.senBroad(this, 7);
    }


    @Override

    public void onDestroy() {
        unregisterReceiver(this.receiver);
        if (this.vv.isPlaying()) {
            this.vv.stopPlayback();
        }
        super.onDestroy();
    }

    @Override
    public void onDis() {
        onAddCall();
        finish();
    }

    @Override
    public void onAddCall() {
        Vibrator vibrator = this.myVib;
        if (vibrator != null) {
            vibrator.cancel();
        }
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}

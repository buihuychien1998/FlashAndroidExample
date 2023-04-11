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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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


public class CallActivity extends AppCompatActivity implements SensorEventListener, BaseScreen.ScreenResult {
    private BaseScreen baseScreen;
    private Bitmap bm;
    private ImageView im;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private CallManager ongoingCall;
    private String photo;
    private PowerManager pm;
    private final BroadcastReceiver receiver = new BroadcastReceiver() { 
        @Override 
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (action.equals(CallManager.ACTION_CALL)) {
                CallActivity.this.baseScreen.callStatus(intent.getIntExtra("data", -1));
                return;
            }
            String stringExtra = intent.getStringExtra("time");
            if (stringExtra == null || stringExtra.isEmpty()) {
                stringExtra = "00:00";
            }
            CallActivity.this.baseScreen.setTime(stringExtra);
        }
    };
    private VideoView vv;

    @Override 
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    
    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setColorNavi();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CallManager.ACTION_CALL);
        intentFilter.addAction(CallManager.ACTION_TIME);
        registerReceiver(this.receiver, intentFilter);
        setContentView(R.layout.activity_call);
        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService("keyguard");
        this.ongoingCall = new CallManager(this);
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
        this.baseScreen.setScreenResult(this.ongoingCall, this);
        this.baseScreen.setName(CallManager.num);
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
                return;
            }
            return;
        }
        getWindow().addFlags(6815744);
    }

    private void initBg() {
        final Handler handler = new Handler(new Handler.Callback() { 
            @Override 
            public final boolean handleMessage(Message message) {
                return CallActivity.this.m6lambda$initBg$0$comdialercolorscreenphone12OS14CallActivity(message);
            }
        });
        new Thread(new Runnable() { 
            @Override 
            public final void run() {
                CallActivity.this.m7lambda$initBg$1$comdialercolorscreenphone12OS14CallActivity(handler);
            }
        }).start();
    }

    
    public  boolean m6lambda$initBg$0$comdialercolorscreenphone12OS14CallActivity(Message message) {
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

    
    public  void m7lambda$initBg$1$comdialercolorscreenphone12OS14CallActivity(Handler handler) {
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
        imageView.setLayerType(1, null);
    }

    
    public static  void lambda$initView$2(MediaPlayer mediaPlayer) {

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
        CallManager callManager = this.ongoingCall;
        if (callManager == null || callManager.getCall() == null) {
            finish();
        } else {
            this.baseScreen.callStatus(this.ongoingCall.getCall().getState());
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
        this.ongoingCall.hangup();
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
        finish();
    }

    @Override 
    public void onNum(String str) {
        if (this.ongoingCall.getCall() != null && checkSelfPermission("android.permission.CALL_PHONE") == 0) {
            this.ongoingCall.getCall().playDtmfTone(str.charAt(0));
            new Thread(new Runnable() { 
                @Override 
                public final void run() {
                    CallActivity.this.lambda$onNum$3$CallActivity();
                }
            }).start();
        }
    }

    public  void lambda$onNum$3$CallActivity() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.ongoingCall.getCall().stopDtmfTone();
    }

    @Override 
    public void onRecorder() {
        if (!OtherUntil.checkPer(this, "android.permission.RECORD_AUDIO")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, 1);
        } else {
            onRec();
        }
    }

    @Override
    
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr.length > 0 && iArr[0] == 0) {
            onRec();
        }
    }

    private void onRec() {
        this.ongoingCall.startRecorder();
    }

    private void setColorNavi() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(1792);
        getWindow().setNavigationBarColor(0);
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(0);
    }
}

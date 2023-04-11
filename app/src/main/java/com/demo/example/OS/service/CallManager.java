package com.demo.example.OS.service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.telecom.Call;
import com.demo.example.OS.until.MyRecorder;
import com.demo.example.OS.until.OtherUntil;


public class CallManager {
    public static final String ACTION_CALL = "action_call";
    public static final String ACTION_TIME = "action_time";
    private static Call call;
    public static Handler handler;
    private static MyRecorder myRecorder;
    public static String num;
    public static int status;
    public static int time;
    public AudioManager am;
    private final Context context;
    private MediaPlayer player;
    private Vibrator v;
    private boolean hold = false;
    private Object callback = new Call.Callback() { 
        @Override 
        public void onStateChanged(Call call2, int i) {
            super.onStateChanged(call2, i);
            if (i != 2) {
                CallManager.this.stopSound();
            }
            Intent intent = new Intent(CallManager.ACTION_CALL);
            intent.putExtra("data", i);
            CallManager.this.context.sendBroadcast(intent);
            if (i != 4) {
                if (i == 10 && CallManager.handler != null) {
                    CallManager.handler.removeCallbacks(CallManager.this.runnable);
                }
            } else if (CallManager.handler != null) {
                CallManager.handler.removeCallbacks(CallManager.this.runnable);
                CallManager.handler.post(CallManager.this.runnable);
            }
        }
    };
    private Runnable runnable = new Runnable() { 
        @Override 
        public void run() {
            if (CallManager.handler != null) {
                CallManager.handler.postDelayed(this, 1000);
                String time2 = OtherUntil.getTime(CallManager.time);
                Intent intent = new Intent(CallManager.ACTION_TIME);
                intent.putExtra("time", time2);
                CallManager.this.context.sendBroadcast(intent);
                CallManager.time++;
            }
        }
    };

    public CallManager(Context context) {
        this.context = context;
        this.v = (Vibrator) context.getSystemService("vibrator");
        this.am = (AudioManager) context.getSystemService("audio");
    }

    
    public final void setCall(Call call2) {
        Call call3 = call;
        if (call3 != null) {
            call3.unregisterCallback((Call.Callback) this.callback);
            this.hold = false;
            stopRecorder();
            Handler handler2 = handler;
            if (handler2 != null) {
                handler2.removeCallbacks(this.runnable);
            }
        }
        if (call2 != null) {
            call2.registerCallback((Call.Callback) this.callback);
            status = call2.getState();
        }
        call = call2;
        if (call2 != null && call2.getState() == 2) {
            startSound();
        }
    }

    public Call getCall() {
        return call;
    }

    public void answer() {
        Call call2 = call;
        if (call2 != null) {
            call2.answer(0);
        }
    }

    public void holdAndPlay() {
        if (call != null) {
            if (!this.hold) {
                Handler handler2 = handler;
                if (handler2 != null) {
                    handler2.removeCallbacks(this.runnable);
                }
                call.hold();
                this.hold = true;
                return;
            }
            Handler handler3 = handler;
            if (handler3 != null) {
                handler3.removeCallbacks(this.runnable);
                handler.postDelayed(this.runnable, 1000);
            }
            this.hold = false;
            call.unhold();
        }
    }

    public boolean isHold() {
        return this.hold;
    }

    public void hangup() {
        Call call2 = call;
        if (call2 != null) {
            call2.disconnect();
        }
    }

    public void startRecorder() {
        MyRecorder myRecorder2 = new MyRecorder(this.context, num, status);
        myRecorder = myRecorder2;
        myRecorder2.startRecord();
    }

    public void stopRecorder() {
        MyRecorder myRecorder2 = myRecorder;
        if (myRecorder2 != null) {
            myRecorder2.stopRecord();
            myRecorder = null;
        }
    }

    
    public void stopSound() {
        MediaPlayer mediaPlayer = this.player;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.player.release();
            this.player = null;
        }
        Vibrator vibrator = this.v;
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    private void startSound() {
        stopSound();
        if (this.am.getRingerMode() != 0) {
            if (this.am.getRingerMode() == 2) {
                MediaPlayer create = MediaPlayer.create(this.context, Settings.System.DEFAULT_RINGTONE_URI);
                this.player = create;
                create.setLooping(true);
                this.player.start();
            }
            long[] jArr = {0, 100, 200, 100, 200, 100};
            Vibrator vibrator = this.v;
            if (vibrator != null) {
                vibrator.vibrate(jArr, 0);
            }
        }
    }
}

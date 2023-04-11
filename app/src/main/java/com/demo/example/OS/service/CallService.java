package com.demo.example.OS.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.telecom.Call;
import android.telecom.InCallService;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.demo.example.R;
import com.demo.example.OS.CallActivity;
import com.demo.example.OS.screen.BaseScreen;
import com.demo.example.OS.until.OtherUntil;

import java.io.IOException;


public class CallService extends InCallService {
    public static final String CHANNEL = "call channel";
    public static final String END = "end";
    public static final int ID_NOTIFICATION = 1233326;
    private CallManager callManager;
    private boolean end;
    private RemoteViews notiSmall;
    private Notification notification;
    private final BroadcastReceiver receiver = new BroadcastReceiver() { 
        @Override 
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && !CallService.this.end) {
                if (action.equals(BaseScreen.ACTION_UPDATE)) {
                    String stringExtra = intent.getStringExtra("num");
                    String stringExtra2 = intent.getStringExtra("name");
                    String stringExtra3 = intent.getStringExtra("photo");
                    if (stringExtra3 == null || stringExtra3.isEmpty()) {
                        CallService.this.notiSmall.setImageViewResource(R.id.im_avatar, R.drawable.ic_contact);
                    } else {
                        try {
                            CallService.this.notiSmall.setImageViewBitmap(R.id.im_avatar, OtherUntil.getCroppedBitmap(MediaStore.Images.Media.getBitmap(CallService.this.getContentResolver(), Uri.parse(stringExtra3))));
                        } catch (IOException unused) {
                            CallService.this.notiSmall.setImageViewResource(R.id.im_avatar, R.drawable.ic_contact);
                        }
                    }
                    if (stringExtra2 != null && !stringExtra2.isEmpty()) {
                        CallService.this.notiSmall.setTextViewText(R.id.tv_name, stringExtra2);
                    } else if (stringExtra != null) {
                        CallService.this.notiSmall.setTextViewText(R.id.tv_name, stringExtra);
                    } else {
                        CallService.this.notiSmall.setTextViewText(R.id.tv_name, CallService.this.getString(R.string.unknown));
                    }
                    CallService callService = CallService.this;
                    callService.startForeground(CallService.ID_NOTIFICATION, callService.notification);
                } else if (action.equals(CallManager.ACTION_TIME)) {
                    String stringExtra4 = intent.getStringExtra("time");
                    if (stringExtra4 == null || stringExtra4.isEmpty()) {
                        stringExtra4 = "00:00";
                    }
                    CallService.this.notiSmall.setTextViewText(R.id.tv_time, stringExtra4);
                    CallService callService2 = CallService.this;
                    callService2.startForeground(CallService.ID_NOTIFICATION, callService2.notification);
                }
            }
        }
    };

    @Override 
    public void onCallAdded(Call call) {
        String str;
        super.onCallAdded(call);
        if (call != null) {
            this.end = false;
            try {
                str = call.getDetails().getHandle().getSchemeSpecificPart();
            } catch (NullPointerException unused) {
                str = getString(R.string.unknown);
            }
            CallManager.num = str;
            CallManager.handler = new Handler();
            CallManager callManager = new CallManager(this);
            this.callManager = callManager;
            callManager.setCall(call);
            TelecomAdapter.getInstance().setInCallService(this);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setFlags(268451840);
            intent.putExtra("num", str);
            intent.putExtra(NotificationCompat.CATEGORY_STATUS, call.getState());
            startActivity(intent);
            startForeground(ID_NOTIFICATION, this.notification);
        }
    }

    @Override 
    public void onCallRemoved(Call call) {
        super.onCallRemoved(call);
        this.end = true;
        TelecomAdapter.getInstance().clearInCallService();
        if (this.callManager == null) {
            this.callManager = new CallManager(this);
        }
        this.callManager.setCall(null);
        CallManager.num = null;
        CallManager.handler = null;
        CallManager.time = 0;
        stopForeground(true);
    }

    @Override 
    public void onConnectionEvent(Call call, String str, Bundle bundle) {
        super.onConnectionEvent(call, str, bundle);
    }

    @Override 
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CallManager.ACTION_TIME);
        intentFilter.addAction(BaseScreen.ACTION_UPDATE);
        registerReceiver(this.receiver, intentFilter);
        this.notiSmall = new RemoteViews(getPackageName(), (int) R.layout.layout_call);
        Intent intent = new Intent(this, CallActivity.class);
        intent.setFlags(268468224);
        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
        Intent intent2 = new Intent(this, CallService.class);
        intent2.setAction(END);
        this.notiSmall.setOnClickPendingIntent(R.id.tv_end, PendingIntent.getService(this, 1, intent2, 1073741824));
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("001", "iCaller", NotificationManager.IMPORTANCE_NONE);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.setLockscreenVisibility(0);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
                this.notification = new Notification.Builder(getApplicationContext(), "001").setOngoing(true).build();
            }
        } else {
            this.notification = new NotificationCompat.Builder(this, CHANNEL).setAutoCancel(false).build();
        }
        this.notification.contentView = this.notiSmall;
        this.notification.flags = 2;
        this.notification.icon = R.drawable.ic_add_call_mate;
        this.notification.sound = null;
        this.notification.contentIntent = activity;
        this.notification.priority = Notification.PRIORITY_LOW;
    }

    @Override 
    public int onStartCommand(Intent intent, int i, int i2) {
        String action;
        if (!(intent == null || this.callManager == null || (action = intent.getAction()) == null || !action.equals(END))) {
            this.callManager.hangup();
        }
        return super.onStartCommand(intent, i, i2);
    }

    @Override 
    public void onDestroy() {
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }
}

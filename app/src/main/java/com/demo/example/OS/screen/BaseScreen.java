package com.demo.example.OS.screen;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.core.view.GravityCompat;

import com.demo.example.R;
import com.demo.example.OS.custom.BoldText;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.service.CallManager;
import com.demo.example.OS.until.OtherUntil;
import com.demo.example.OS.until.ReadContact;
import com.makeramen.roundedimageview.RoundedImageView;


public abstract class BaseScreen extends RelativeLayout {
    public static final String ACTION_UPDATE = "action_update";
    public static final int ID_CONTACT = 26;
    public static final int ID_HOLD = 24;
    public static final int ID_MUTE = 21;
    public static final int ID_PAD = 22;
    public static final int ID_REC = 25;
    public static final int ID_SPEAKER = 23;
    protected CallManager callManager;
    private FakeResult fakeResult;
    protected Handler handler;
    protected RoundedImageView imPhoto;
    protected AudioManager mAudioManager;
    protected String num;
    protected ScreenResult screenResult;
    protected int time;
    protected BoldText tvName;
    protected MediumText tvStatus;

    
    public interface FakeResult {
        void onAddCall();
    }

    
    public interface ScreenResult {
        void onDis();

        void onNum(String str);

        void onRecorder();
    }

    public abstract void updateLayout(boolean z);

    public void setScreenResult(CallManager callManager, ScreenResult screenResult) {
        this.screenResult = screenResult;
        this.callManager = callManager;
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
    }

    public void setFakeResult(FakeResult fakeResult) {
        this.fakeResult = fakeResult;
    }

    public BaseScreen(Context context) {
        super(context);
        initHandler();
    }

    public BaseScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initHandler();
    }

    public BaseScreen(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initHandler();
    }

    private void initHandler() {
        this.handler = new Handler(new Handler.Callback() { 
            @Override 
            public boolean handleMessage(Message message) {
                try {
                    String[] strArr = (String[]) message.obj;
                    Intent intent = new Intent(BaseScreen.ACTION_UPDATE);
                    intent.putExtra("num", BaseScreen.this.num);
                    if (!strArr[0].isEmpty()) {
                        intent.putExtra("name", strArr[0]);
                        BaseScreen.this.tvName.setText(strArr[0]);
                        if (!strArr[1].isEmpty()) {
                            intent.putExtra("photo", strArr[1]);
                            BaseScreen.this.imPhoto.setVisibility(View.VISIBLE);
                            BaseScreen.this.tvName.setGravity(GravityCompat.START);
                            BaseScreen.this.tvStatus.setGravity(GravityCompat.START);
                            try {
                                BaseScreen.this.imPhoto.setImageBitmap(OtherUntil.getCroppedBitmap(MediaStore.Images.Media.getBitmap(BaseScreen.this.getContext().getContentResolver(), Uri.parse(strArr[1]))));
                            } catch (Exception unused) {
                            }
                        }
                    }
                    BaseScreen.this.getContext().sendBroadcast(intent);
                } catch (NullPointerException unused2) {
                }
                return true;
            }
        });
    }

    public void setName(String str) {
        this.time = 0;
        this.tvName.setText(str);
        this.imPhoto.setVisibility(GONE);
        this.num = str;
        if (OtherUntil.checkPer(getContext(), "android.permission.READ_CONTACTS")) {
            new Thread(new Runnable() { 
                @Override 
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = ReadContact.getNamePhoto(BaseScreen.this.getContext(), BaseScreen.this.num);
                    BaseScreen.this.handler.sendMessage(message);
                }
            }).start();
        }
    }

    public void callStatus(int i) {
        if (i != 1) {
            if (i == 2) {
                updateLayout(true);
                return;
            } else if (i == 4) {
                FakeResult fakeResult = this.fakeResult;
                if (fakeResult != null) {
                    fakeResult.onAddCall();
                }
                updateLayout(false);
                return;
            } else if (i == 7) {
                ScreenResult screenResult = this.screenResult;
                if (screenResult != null) {
                    screenResult.onDis();
                    return;
                }
                return;
            } else if (i != 9) {
                if (i == 10) {
                    this.tvStatus.setText(getResources().getString(R.string.end));
                    return;
                }
                return;
            }
        }
        this.time = 0;
        this.tvStatus.setText(getResources().getString(R.string.calling));
        updateLayout(false);
    }

    public void setTime(String str) {
        this.tvStatus.setText(str);
    }

    public int getTime() {
        return this.time;
    }
}

package com.demo.example.OS.screen.mate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.VideoActivity;
import com.demo.example.OS.custom.BoldText;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.screen.BaseScreen;
import com.demo.example.OS.screen.ITFAddCall;
import com.demo.example.OS.screen.ITFPadResult;
import com.demo.example.OS.screen.ModeCallResult;
import com.demo.example.OS.service.TelecomAdapter;
import com.demo.example.OS.until.OtherUntil;
import com.makeramen.roundedimageview.RoundedImageView;


public class ScreenMate extends BaseScreen implements ITFAddCall, ModeCallResult, ITFPadResult {
    private PadMate padMate;
    private ViewAddCallMate viewAddCallMate;
    private ViewModeMate viewModeMate;

    public ScreenMate(Context context) {
        super(context);
        init();
    }

    public ScreenMate(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ScreenMate(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        View view = new View(getContext());
        view.setId(125487);
        LayoutParams layoutParams = new LayoutParams(-1, OtherUntil.dpToPixel(50, getContext()));
        layoutParams.addRule(15);
        addView(view, layoutParams);
        this.tvStatus = new MediumText(getContext());
        this.tvStatus.setId(5423);
        this.tvStatus.setTextSize(2, 10.0f);
        this.tvStatus.setTextColor(-1);
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.addRule(14);
        layoutParams2.addRule(2, view.getId());
        addView(this.tvStatus, layoutParams2);
        this.tvName = new BoldText(getContext());
        this.tvName.setId(6426);
        this.tvName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/sf-ui-display-medium-58646be638f96.otf"));
        this.tvName.setTextColor(-1);
        this.tvName.setTextSize(2, 27.0f);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        layoutParams3.addRule(14);
        layoutParams3.addRule(2, this.tvStatus.getId());
        addView(this.tvName, layoutParams3);
        this.imPhoto = new RoundedImageView(getContext());
        this.imPhoto.setCornerRadius(10.0f);
        this.imPhoto.setBorderWidth(5.0f);
        this.imPhoto.setBorderColor(-16777216);
        this.imPhoto.setOval(true);
        int i = getResources().getDisplayMetrics().widthPixels / 3;
        LayoutParams layoutParams4 = new LayoutParams(i, i);
        layoutParams4.addRule(14);
        layoutParams4.addRule(2, this.tvName.getId());
        layoutParams4.setMargins(0, 0, 0, OtherUntil.dpToPixel(15, getContext()));
        addView(this.imPhoto, layoutParams4);
        int i2 = (getResources().getDisplayMetrics().widthPixels * 19) / 100;
        ViewAddCallMate viewAddCallMate = new ViewAddCallMate(getContext());
        this.viewAddCallMate = viewAddCallMate;
        viewAddCallMate.setVisibility(GONE);
        this.viewAddCallMate.setItfAddCall(this);
        LayoutParams layoutParams5 = new LayoutParams(-1, i2);
        layoutParams5.addRule(12);
        int i3 = i2 / 2;
        layoutParams5.setMargins(i3, 0, i3, i2);
        addView(this.viewAddCallMate, layoutParams5);
        ViewModeMate viewModeMate = new ViewModeMate(getContext());
        this.viewModeMate = viewModeMate;
        viewModeMate.setVisibility(GONE);
        this.viewModeMate.setModeCallResult(this);
        LayoutParams layoutParams6 = new LayoutParams(-1, -2);
        layoutParams6.addRule(12);
        layoutParams6.setMargins(0, 0, 0, i2);
        addView(this.viewModeMate, layoutParams6);
        PadMate padMate = new PadMate(getContext());
        this.padMate = padMate;
        padMate.setVisibility(GONE);
        this.padMate.setItfPadResult(this);
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams7 = new LayoutParams(-1, -2);
        layoutParams7.addRule(12);
        layoutParams7.setMargins(0, 0, 0, (i2 * 2) + (i2 / 4));
        addView(linearLayout, layoutParams7);
        linearLayout.addView(this.padMate, new LinearLayout.LayoutParams(-1, -2));
    }

    @Override 
    public void updateLayout(boolean z) {
        int i = 0;
        int i2 = 4;
        if (!z) {
            i2 = 0;
            i = 4;
        }


        if (i == 0) {

            this.viewAddCallMate.setVisibility(VISIBLE);

        } else {
            this.viewAddCallMate.setVisibility(INVISIBLE);

        }
        if (i2 == 0) {

            this.viewModeMate.setVisibility(VISIBLE);

        } else {
            this.viewModeMate.setVisibility(INVISIBLE);

        }
    }

    @Override 
    public void onAddCall() {
        if (this.callManager == null) {
            OtherUntil.senBroad(getContext(), 4);
        } else {
            this.callManager.answer();
        }
    }

    @Override 
    public void onCancel() {
        if (this.callManager == null) {
            OtherUntil.senBroad(getContext(), 7);
        } else {
            this.callManager.hangup();
        }
    }

    @Override 
    public void onModeClick(int i) {
        if (i == 22) {
            if (this.padMate.getVisibility() != VISIBLE) {
                this.padMate.setVisibility(VISIBLE);
                this.padMate.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_up));
                return;
            }
            this.padMate.setVisibility(INVISIBLE);
            this.padMate.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_down));
        } else if (i == 21) {
            this.viewModeMate.onMute(!this.mAudioManager.isMicrophoneMute());
            TelecomAdapter.getInstance().muteSpeaker(this.mAudioManager);
        } else if (i == 23) {
            this.viewModeMate.onSpeaker(!this.mAudioManager.isSpeakerphoneOn());
            TelecomAdapter.getInstance().switchSpeaker(this.mAudioManager);
        } else if (i == 24) {
            if (this.callManager != null) {
                this.callManager.holdAndPlay();
                this.viewModeMate.onHold(this.callManager.isHold());
            }
        } else if (i == 26) {
            getContext().startActivity(new Intent(getContext(), VideoActivity.class));
        } else if (i == 3010) {
            if (this.callManager == null) {
                OtherUntil.senBroad(getContext(), 7);
            } else {
                this.callManager.hangup();
            }
        } else if (i == 25) {
            this.screenResult.onRecorder();
        }
    }

    @Override 
    public void onTextResult(String str) {
        this.screenResult.onNum(str);
    }
}

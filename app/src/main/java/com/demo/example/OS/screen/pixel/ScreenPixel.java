package com.demo.example.OS.screen.pixel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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


public class ScreenPixel extends BaseScreen implements ITFAddCall, View.OnClickListener, ModeCallResult, ITFPadResult {
    private ImageView imEnd;
    private PadPixel padPixel;
    private ViewAdd viewAdd;
    private ViewModePixel viewModePixel;

    public ScreenPixel(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = new View(getContext());
        view.setId(121212);
        LayoutParams layoutParams = new LayoutParams(-1, 1);
        layoutParams.addRule(15);
        addView(view, layoutParams);
        this.imPhoto = new RoundedImageView(getContext());
        this.imPhoto.setOval(true);
        this.imPhoto.setCornerRadius(10.0f);
        this.imPhoto.setBorderWidth(5.0f);
        this.imPhoto.setBorderColor(-16777216);
        int i = (getResources().getDisplayMetrics().widthPixels * 5) / 12;
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(13);
        addView(this.imPhoto, layoutParams2);
        this.tvName = new BoldText(getContext());
        this.tvName.setId(6426);
        this.tvName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/sf-ui-display-medium-58646be638f96.otf"));
        this.tvName.setTextColor(-1);
        this.tvName.setTextSize(2, 30.0f);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        layoutParams3.addRule(14);
        layoutParams3.setMargins(0, OtherUntil.dpToPixel(70, getContext()), 0, 0);
        addView(this.tvName, layoutParams3);
        this.tvStatus = new MediumText(getContext());
        this.tvStatus.setTextSize(2, 10.0f);
        this.tvStatus.setTextColor(-1);
        LayoutParams layoutParams4 = new LayoutParams(-2, -2);
        layoutParams4.addRule(14);
        layoutParams4.addRule(3, this.tvName.getId());
        addView(this.tvStatus, layoutParams4);
        ViewAdd viewAdd = new ViewAdd(getContext());
        this.viewAdd = viewAdd;
        viewAdd.setVisibility(GONE);
        this.viewAdd.setItfAddCall(this);
        int i2 = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d);
        LayoutParams layoutParams5 = new LayoutParams(-1, i2 * 3);
        layoutParams5.addRule(12);
        layoutParams5.setMargins(0, 0, 0, i2 / 4);
        addView(this.viewAdd, layoutParams5);
        ViewModePixel viewModePixel = new ViewModePixel(getContext());
        this.viewModePixel = viewModePixel;
        viewModePixel.setVisibility(GONE);
        this.viewModePixel.setModeCallResult(this);
        LayoutParams layoutParams6 = new LayoutParams(-1, -2);
        layoutParams6.addRule(12);
        int i3 = i2 * 5;
        layoutParams6.setMargins(0, 0, 0, i3 / 2);
        addView(this.viewModePixel, layoutParams6);
        PadPixel padPixel = new PadPixel(getContext());
        this.padPixel = padPixel;
        padPixel.setVisibility(GONE);
        this.padPixel.setItfPadResult(this);
        LayoutParams layoutParams7 = new LayoutParams(-1, -2);
        layoutParams7.addRule(12);
        addView(this.padPixel, layoutParams7);
        ImageView imageView = new ImageView(getContext());
        this.imEnd = imageView;
        imageView.setVisibility(GONE);
        this.imEnd.setId(125244);
        this.imEnd.setOnClickListener(this);
        this.imEnd.setImageResource(R.drawable.ic_end_call);
        LayoutParams layoutParams8 = new LayoutParams(i2, i2);
        layoutParams8.addRule(14);
        layoutParams8.addRule(12);
        layoutParams8.setMargins(0, 0, 0, i3 / 4);
        addView(this.imEnd, layoutParams8);
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

            this.viewAdd.setVisibility(VISIBLE);

        } else {
            this.viewAdd.setVisibility(INVISIBLE);

        }
        if (i2 == 0) {
            this.imEnd.setVisibility(VISIBLE);
            this.viewModePixel.setVisibility(VISIBLE);

        } else {
            this.imEnd.setVisibility(INVISIBLE);
            this.viewModePixel.setVisibility(INVISIBLE);
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
    public void onClick(View view) {
        if (this.callManager == null) {
            OtherUntil.senBroad(getContext(), 7);
        } else {
            this.callManager.hangup();
        }
    }

    @Override 
    public void onModeClick(int i) {
        if (i == 22) {
            this.padPixel.setVisibility(VISIBLE);
            this.padPixel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_up));
        } else if (i == 21) {
            this.viewModePixel.onMute(!this.mAudioManager.isMicrophoneMute());
            TelecomAdapter.getInstance().muteSpeaker(this.mAudioManager);
        } else if (i == 23) {
            this.viewModePixel.onSpeaker(!this.mAudioManager.isSpeakerphoneOn());
            TelecomAdapter.getInstance().switchSpeaker(this.mAudioManager);
        } else if (i == 24) {
            if (this.callManager != null) {
                this.callManager.holdAndPlay();
                this.viewModePixel.onHold(this.callManager.isHold());
            }
        } else if (i == 26) {
            getContext().startActivity(new Intent(getContext(), VideoActivity.class));
        } else if (i == 25) {
            this.screenResult.onRecorder();
        }
    }

    @Override 
    public void onTextResult(String str) {
        this.screenResult.onNum(str);
    }
}

package com.demo.example.OS.screen.s20;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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


public class ScreenS20 extends BaseScreen implements ModeCallResult, View.OnClickListener, ITFAddCall, ITFPadResult {
    private ViewAddCallGalaxy addCallGalaxy;
    private ImageView imEnd;
    private PadGalaxy padGalaxy;
    private ViewCallModeS20 viewCallModeS20;

    public ScreenS20(Context context) {
        super(context);
        init();
    }

    public ScreenS20(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ScreenS20(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        View view = new View(getContext());
        view.setId(123321);
        LayoutParams layoutParams = new LayoutParams(-1, 1);
        layoutParams.addRule(15);
        addView(view, layoutParams);
        ViewCallModeS20 viewCallModeS20 = new ViewCallModeS20(getContext());
        this.viewCallModeS20 = viewCallModeS20;
        viewCallModeS20.setId(9826);
        this.viewCallModeS20.setModeCallResult(this);
        LayoutParams layoutParams2 = new LayoutParams(-1, -2);
        layoutParams2.addRule(3, view.getId());
        addView(this.viewCallModeS20, layoutParams2);
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
        this.imPhoto = new RoundedImageView(getContext());
        this.imPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.imPhoto.setCornerRadius(10.0f);
        this.imPhoto.setBorderWidth(5.0f);
        this.imPhoto.setBorderColor(-1);
        this.imPhoto.setOval(true);
        int i = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 38.6d) / 100.0d);
        LayoutParams layoutParams5 = new LayoutParams(i, i);
        layoutParams5.addRule(14);
        layoutParams5.addRule(3, this.tvName.getId());
        layoutParams5.setMargins(0, OtherUntil.dpToPixel(35, getContext()), 0, 0);
        addView(this.imPhoto, layoutParams5);
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams6 = new LayoutParams(-1, -2);
        layoutParams6.addRule(2, view.getId());
        layoutParams6.setMargins(0, 0, 0, -((int) (((((double) getResources().getDisplayMetrics().widthPixels) * 7.2d) * 4.0d) / 100.0d)));
        addView(linearLayout, layoutParams6);
        PadGalaxy padGalaxy = new PadGalaxy(getContext());
        this.padGalaxy = padGalaxy;
        padGalaxy.setVisibility(INVISIBLE);
        this.padGalaxy.setPadGalaxyResult(this);
        linearLayout.addView(this.padGalaxy, new LinearLayout.LayoutParams(-1, -2));
        ImageView imageView = new ImageView(getContext());
        this.imEnd = imageView;
        imageView.setImageResource(R.drawable.ic_end_call_galaxy);
        this.imEnd.setOnClickListener(this);
        LayoutParams layoutParams7 = new LayoutParams((int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d), -1);
        layoutParams7.addRule(14);
        layoutParams7.addRule(3, this.viewCallModeS20.getId());
        layoutParams7.setMargins(0, 0, 0, OtherUntil.dpToPixel(40, getContext()));
        addView(this.imEnd, layoutParams7);
        ViewAddCallGalaxy viewAddCallGalaxy = new ViewAddCallGalaxy(getContext());
        this.addCallGalaxy = viewAddCallGalaxy;
        viewAddCallGalaxy.setAddCallGalaxyResult(this);
        LayoutParams layoutParams8 = new LayoutParams(-1, getResources().getDisplayMetrics().heightPixels / 2);
        layoutParams8.addRule(12);
        addView(this.addCallGalaxy, layoutParams8);
    }

    @Override 
    public void onModeClick(int i) {
        if (i == 22) {
            this.viewCallModeS20.showPad();
            if (this.viewCallModeS20.isShowPad()) {
                this.imPhoto.setVisibility(INVISIBLE);
                this.imPhoto.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
                this.padGalaxy.setVisibility(VISIBLE);
                this.padGalaxy.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_up));
                return;
            }
            this.padGalaxy.setVisibility(INVISIBLE);
            this.padGalaxy.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_down));
            this.imPhoto.setVisibility(VISIBLE);
            this.imPhoto.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
        } else if (i == 21) {
            this.viewCallModeS20.onMute(!this.mAudioManager.isMicrophoneMute());
            TelecomAdapter.getInstance().muteSpeaker(this.mAudioManager);
        } else if (i == 23) {
            this.viewCallModeS20.onSpeaker(!this.mAudioManager.isSpeakerphoneOn());
            TelecomAdapter.getInstance().switchSpeaker(this.mAudioManager);
        } else if (i == 24) {
            if (this.callManager != null) {
                this.callManager.holdAndPlay();
                this.viewCallModeS20.onHold(this.callManager.isHold());
            }
        } else if (i == 26) {
            getContext().startActivity(new Intent(getContext(), VideoActivity.class));
        } else if (i == 25) {
            this.screenResult.onRecorder();
        }
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

            this.addCallGalaxy.setVisibility(VISIBLE);

        } else {
            this.addCallGalaxy.setVisibility(INVISIBLE);

        }
        if (i2 == 0) {
            this.imEnd.setVisibility(VISIBLE);
            this.viewCallModeS20.setVisibility(VISIBLE);

        } else {
            this.imEnd.setVisibility(INVISIBLE);
            this.viewCallModeS20.setVisibility(INVISIBLE);
        }


    }

    @Override 
    public void onTextResult(String str) {
        this.screenResult.onNum(str);
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
}

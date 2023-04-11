package com.demo.example.OS.screen.iphone;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.demo.example.R;
import com.demo.example.OS.VideoActivity;
import com.demo.example.OS.custom.BoldText;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.screen.BaseScreen;
import com.demo.example.OS.screen.ITFAddCall;
import com.demo.example.OS.screen.ModeCallResult;
import com.demo.example.OS.service.TelecomAdapter;
import com.demo.example.OS.until.OtherUntil;
import com.makeramen.roundedimageview.RoundedImageView;


public class ScreenIphone extends BaseScreen implements View.OnClickListener, PadNum.PadResult, ModeCallResult, ITFAddCall {
    private ImageView imEnd;
    private LinearLayout llRem;
    private LinearLayout llSms;
    private PadNum padNum;
    private ViewAddCall viewAddCall;
    private ViewCallMode viewCallMode;

    @Override 
    public void onCancel() {
    }

    public ScreenIphone(Context context) {
        super(context);
        init();
    }

    public ScreenIphone(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ScreenIphone(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        int i = getResources().getDisplayMetrics().widthPixels / 5;
        int i2 = (int) ((((float) getResources().getDisplayMetrics().widthPixels) * 7.2f) / 100.0f);
        int i3 = (int) ((((float) getResources().getDisplayMetrics().widthPixels) * 4.3f) / 100.0f);
        View view = new View(getContext());
        view.setId(1993);
        LayoutParams layoutParams = new LayoutParams(i3, i3);
        layoutParams.addRule(13);
        addView(view, layoutParams);
        ViewAddCall viewAddCall = new ViewAddCall(getContext());
        this.viewAddCall = viewAddCall;
        viewAddCall.setVisibility(GONE);
        this.viewAddCall.setAddCallResult(this);
        LayoutParams layoutParams2 = new LayoutParams(-1, i);
        layoutParams2.addRule(3, view.getId());
        int i4 = i2 * 2;
        int i5 = i * 2;
        int i6 = (i3 * 2) + i5;
        layoutParams2.setMargins(i4, i6, i4, 0);
        addView(this.viewAddCall, layoutParams2);
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.llRem = linearLayout;
        linearLayout.setVisibility(GONE);
        this.llRem.setOnClickListener(this);
        this.llRem.setGravity(81);
        this.llRem.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams3 = new LayoutParams(i, -2);
        layoutParams3.addRule(3, view.getId());
        double d = (double) i;
        int i7 = (int) (((((double) i3) * 4.5d) + d) - ((double) i4));
        layoutParams3.setMargins(i4, i7, 0, 0);
        addView(this.llRem, layoutParams3);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_clock);
        int i8 = (int) (((float) i3) * 1.1f);
        this.llRem.addView(imageView, new LinearLayout.LayoutParams(i8, i2));
        MediumText mediumText = new MediumText(getContext());
        mediumText.setTextColor(-1);
        mediumText.setSingleLine();
        mediumText.setTextSize(2, 10.0f);
        mediumText.setText(getResources().getString(R.string.reminds_me));
        this.llRem.addView(mediumText, new LinearLayout.LayoutParams(-2, -2));
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        this.llSms = linearLayout2;
        linearLayout2.setVisibility(GONE);
        this.llSms.setOnClickListener(this);
        this.llSms.setGravity(81);
        this.llSms.setOrientation(LinearLayout.VERTICAL);
        LayoutParams layoutParams4 = new LayoutParams(i, -2);
        layoutParams4.addRule(3, view.getId());
        layoutParams4.addRule(11);
        layoutParams4.setMargins(0, i7, i4, 0);
        addView(this.llSms, layoutParams4);
        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageResource(R.drawable.ic_message);
        this.llSms.addView(imageView2, new LinearLayout.LayoutParams(i8, i2));
        MediumText mediumText2 = new MediumText(getContext());
        mediumText2.setTextColor(-1);
        mediumText2.setSingleLine();
        mediumText2.setTextSize(2, 10.0f);
        mediumText2.setText(getResources().getString(R.string.message));
        this.llSms.addView(mediumText2, new LinearLayout.LayoutParams(-2, -2));
        PadNum padNum = new PadNum(getContext());
        this.padNum = padNum;
        padNum.setVisibility(GONE);
        this.padNum.setPadResult(this);
        addView(this.padNum, new LayoutParams(-1, -1));
        ImageView imageView3 = new ImageView(getContext());
        this.imEnd = imageView3;
        imageView3.setVisibility(GONE);
        this.imEnd.setOnClickListener(this);
        this.imEnd.setImageResource(R.drawable.ic_end_call);
        LayoutParams layoutParams5 = new LayoutParams(i, i);
        layoutParams5.addRule(3, view.getId());
        layoutParams5.addRule(14);
        layoutParams5.setMargins(0, i6, 0, 0);
        addView(this.imEnd, layoutParams5);
        ViewCallMode viewCallMode = new ViewCallMode(getContext());
        this.viewCallMode = viewCallMode;
        viewCallMode.setVisibility(GONE);
        this.viewCallMode.setModeCallResult(this);
        addView(this.viewCallMode, new LayoutParams(-1, -1));
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        layoutParams6.addRule(2, view.getId());
        layoutParams6.setMargins(0, 0, 0, i5 + i3);
        addView(relativeLayout, layoutParams6);
        View view2 = new View(getContext());
        view2.setId(123456);
        LayoutParams layoutParams7 = new LayoutParams(-1, i3);
        layoutParams7.addRule(15);
        relativeLayout.addView(view2, layoutParams7);
        this.tvStatus = new MediumText(getContext());
        this.tvStatus.setId(121214);
        this.tvStatus.setTextColor(-1);
        this.tvStatus.setGravity(1);
        this.tvStatus.setTextSize(2, 13.0f);
        LayoutParams layoutParams8 = new LayoutParams(-1, -2);
        layoutParams8.addRule(3, view2.getId());
        layoutParams8.setMargins(i3, 0, i3, 0);
        relativeLayout.addView(this.tvStatus, layoutParams8);
        this.imPhoto = new RoundedImageView(getContext());
        this.imPhoto.setOval(true);
        this.imPhoto.setImageResource(R.drawable.icon200);
        this.imPhoto.setId(12485);
        int i9 = (int) (d * 0.9d);
        LayoutParams layoutParams9 = new LayoutParams(i9, i9);
        layoutParams9.addRule(8, this.tvStatus.getId());
        layoutParams9.addRule(11);
        layoutParams9.setMargins(0, 0, i3, i3 / 3);
        relativeLayout.addView(this.imPhoto, layoutParams9);
        this.tvName = new BoldText(getContext());
        this.tvName.setTextColor(-1);
        this.tvName.setSingleLine();
        this.tvName.setTextSize(2, 25.0f);
        this.tvName.setGravity(1);
        LayoutParams layoutParams10 = new LayoutParams(-1, -2);
        layoutParams10.addRule(2, this.tvStatus.getId());
        layoutParams10.addRule(0, this.imPhoto.getId());
        layoutParams10.setMargins(i3, 0, i3, 0);
        relativeLayout.addView(this.tvName, layoutParams10);
    }

    @Override 
    public void onAddCall() {
        if (this.callManager != null) {
            this.callManager.answer();
        } else {
            OtherUntil.senBroad(getContext(), 4);
        }
    }

    @Override 
    public void onClick(View view) {
        if (view != this.imEnd) {
            return;
        }
        if (this.callManager != null) {
            this.callManager.hangup();
        } else {
            OtherUntil.senBroad(getContext(), 7);
        }
    }

    @Override 
    public void onViewClick(boolean z, String str) {
        if (z) {
            this.viewCallMode.setVisibility(View.VISIBLE);
            this.padNum.setVisibility(GONE);
            this.viewCallMode.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
            this.padNum.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out));
            return;
        }
        this.screenResult.onNum(str);
    }

    @Override 
    public void onModeClick(int i) {
        if (i == 22) {
            this.viewCallMode.setVisibility(GONE);
            this.padNum.setVisibility(View.VISIBLE);
            this.viewCallMode.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out));
            this.padNum.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in));
        } else if (i == 21) {
            this.viewCallMode.onMute(!this.mAudioManager.isMicrophoneMute());
            TelecomAdapter.getInstance().muteSpeaker(this.mAudioManager);
        } else if (i == 23) {
            this.viewCallMode.onSpeaker(!this.mAudioManager.isSpeakerphoneOn());
            TelecomAdapter.getInstance().switchSpeaker(this.mAudioManager);
        } else if (i == 24) {
            if (this.callManager != null) {
                this.callManager.holdAndPlay();
                this.viewCallMode.onHold(this.callManager.isHold());
            }
        } else if (i == 26) {
            getContext().startActivity(new Intent(getContext(), VideoActivity.class));
        } else if (i == 25) {
            this.screenResult.onRecorder();
        }
    }

    @Override 
    public void updateLayout(boolean z) {
        int i;
        int i2 = 0;
        if (z) {
            i = 8;
        } else {
            i = 0;
            i2 = 8;
        }

        if (i == 0) {
            this.imEnd.setVisibility(VISIBLE);
            this.viewCallMode.setVisibility(VISIBLE);
        } else {
            this.imEnd.setVisibility(GONE);
            this.viewCallMode.setVisibility(GONE);
        }


        if (i2 == 0) {
            this.viewAddCall.setVisibility(VISIBLE);
            this.llRem.setVisibility(VISIBLE);
            this.llSms.setVisibility(VISIBLE);
        } else {
            this.viewAddCall.setVisibility(GONE);
            this.llRem.setVisibility(GONE);
            this.llSms.setVisibility(GONE);
        }


        this.padNum.setVisibility(GONE);
    }
}

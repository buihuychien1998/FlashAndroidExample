package com.demo.example.OS.screen.s20;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.screen.ModeCallResult;
import com.demo.example.OS.until.OtherUntil;


public class ViewCallModeS20 extends LinearLayout implements View.OnClickListener {
    private ImageView imHold;
    private ImageView imMute;
    private ImageView imRec;
    private ImageView imSpeaker;
    private LinearLayout ll1;
    private ModeCallResult modeCallResult;
    private boolean showPad;
    private TextView tvRec;

    public void setModeCallResult(ModeCallResult modeCallResult) {
        this.modeCallResult = modeCallResult;
    }

    public ViewCallModeS20(Context context) {
        super(context);
        init();
    }

    private void init() {
        int i = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 7.2d) / 100.0d);
        int i2 = i * 4;
        setOrientation(VERTICAL);
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.ll1 = linearLayout;
        linearLayout.setOrientation(HORIZONTAL);
        this.ll1.setGravity(1);
        this.ll1.setWeightSum(4.0f);
        addView(this.ll1, new LayoutParams(-1, i2));
        addViewC(i, 26, this.ll1, R.drawable.ic_contact_galaxy, getResources().getString(R.string.contacts));
        addViewC(i, 24, this.ll1, R.drawable.ic_hold_galaxy, getResources().getString(R.string.hold));
        addViewC(i, 25, this.ll1, R.drawable.ic_rec, getResources().getString(R.string.rec));
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(HORIZONTAL);
        linearLayout2.setGravity(1);
        linearLayout2.setWeightSum(4.0f);
        addView(linearLayout2, new LayoutParams(-1, i2));
        addViewC(i, 23, linearLayout2, R.drawable.ic_volume, getResources().getString(R.string.speaker));
        addViewC(i, 21, linearLayout2, R.drawable.ic_muted, getResources().getString(R.string.mute));
        addViewC(i, 22, linearLayout2, R.drawable.ic_pad_galaxy, getResources().getString(R.string.keypad));
    }

    private void addViewC(int i, int i2, LinearLayout linearLayout, int i3, String str) {
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setId(i2);
        linearLayout2.setGravity(17);
        linearLayout2.setOrientation(VERTICAL);
        linearLayout.addView(linearLayout2, new LayoutParams(0, -1, 1.0f));
        ImageView imageView = new ImageView(getContext());
        if (i2 == 24) {
            this.imHold = imageView;
        } else if (i2 == 23) {
            this.imSpeaker = imageView;
        } else if (i2 == 21) {
            this.imMute = imageView;
        } else if (i2 == 25) {
            this.imRec = imageView;
        }
        imageView.setImageResource(i3);
        linearLayout2.addView(imageView, new LayoutParams(i, i));
        MediumText mediumText = new MediumText(getContext());
        if (i2 == 25) {
            this.tvRec = mediumText;
        }
        mediumText.setText(str);
        mediumText.setTextColor(-1);
        mediumText.setTextSize(2, 10.0f);
        mediumText.setPadding(0, 10, 0, 0);
        linearLayout2.addView(mediumText, new LayoutParams(-2, -2));
        linearLayout2.setOnClickListener(this);
    }

    public void onMute(boolean z) {
        if (z) {
            this.imMute.setImageResource(R.drawable.ic_muted_press);
        } else {
            this.imMute.setImageResource(R.drawable.ic_muted);
        }
    }

    public void onSpeaker(boolean z) {
        if (z) {
            this.imSpeaker.setImageResource(R.drawable.ic_volume_press);
        } else {
            this.imSpeaker.setImageResource(R.drawable.ic_volume);
        }
    }

    public void onHold(boolean z) {
        if (z) {
            this.imHold.setImageResource(R.drawable.ic_hold_galaxy_press);
        } else {
            this.imHold.setImageResource(R.drawable.ic_hold_galaxy);
        }
    }

    public void showPad() {
        boolean z = !this.showPad;
        this.showPad = z;
        if (z) {
            this.ll1.setVisibility(INVISIBLE);
            this.ll1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            return;
        }
        this.ll1.setVisibility(VISIBLE);
        this.ll1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
    }

    public boolean isShowPad() {
        return this.showPad;
    }

    @Override 
    public void onClick(View view) {
        if (view.getId() == 25) {
            if (Build.VERSION.SDK_INT >= 29 && !OtherUntil.isAccessibilitySettingsOn(getContext())) {
                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                intent.addFlags(1342177280);
                getContext().startActivity(intent);
                return;
            } else if (!this.tvRec.getText().toString().equals(getContext().getString(R.string.rec_on))) {
                this.tvRec.setText(R.string.rec_on);
                this.imRec.setImageResource(R.drawable.ic_rec_on);
            } else {
                return;
            }
        }
        this.modeCallResult.onModeClick(view.getId());
    }
}

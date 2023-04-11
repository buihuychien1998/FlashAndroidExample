package com.demo.example.OS.screen.mate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.screen.ModeCallResult;
import com.demo.example.OS.until.OtherUntil;


public class ViewModeMate extends LinearLayout implements View.OnClickListener {
    private ImageView imHold;
    private ImageView imMute;
    private ImageView imRec;
    private ImageView imSpeaker;
    private LinearLayout ll1;
    private ModeCallResult modeCallResult;
    private boolean rec;

    public void setModeCallResult(ModeCallResult modeCallResult) {
        this.modeCallResult = modeCallResult;
    }

    public ViewModeMate(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        int i = (getResources().getDisplayMetrics().widthPixels * 19) / 100;
        int i2 = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 7.2d) / 100.0d);
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.ll1 = linearLayout;
        linearLayout.setGravity(17);
        this.ll1.setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(-1, i);
        layoutParams.setMargins(0, 0, 0, i / 4);
        addView(this.ll1, layoutParams);
        addViewC(i2, 25, this.ll1, R.drawable.ic_rec);
        addViewC(i2, 24, this.ll1, R.drawable.ic_hold_galaxy);
        addViewC(i2, 23, this.ll1, R.drawable.ic_volume);
        addViewC(i2, 21, this.ll1, R.drawable.ic_muted);
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setGravity(17);
        linearLayout2.setOrientation(HORIZONTAL);
        addView(linearLayout2, new LayoutParams(-1, i));
        addViewC(i2, 26, linearLayout2, R.drawable.ic_contact_galaxy);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_end_call);
        imageView.setOnClickListener(this);
        imageView.setId(3010);
        linearLayout2.addView(imageView, new LayoutParams(0, -1, 1.0f));
        addViewC(i2, 22, linearLayout2, R.drawable.ic_pad_galaxy);
    }

    private void addViewC(int i, int i2, LinearLayout linearLayout, int i3) {
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setBackgroundResource(R.drawable.sel_tran);
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

    @Override 
    public void onClick(View view) {
        if (view.getId() == 22) {
            if (this.ll1.getVisibility() == VISIBLE) {
                this.ll1.setVisibility(INVISIBLE);
                this.ll1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
            } else {
                this.ll1.setVisibility(VISIBLE);
                this.ll1.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            }
        } else if (view.getId() == 25) {
            if (!this.rec) {
                if (Build.VERSION.SDK_INT < 29 || OtherUntil.isAccessibilitySettingsOn(getContext())) {
                    this.rec = true;
                    this.imRec.setImageResource(R.drawable.ic_rec_on);
                } else {
                    Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                    intent.addFlags(1342177280);
                    getContext().startActivity(intent);
                    return;
                }
            } else {
                return;
            }
        }
        this.modeCallResult.onModeClick(view.getId());
    }
}

package com.demo.example.OS.screen.pixel;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.screen.ModeCallResult;
import com.demo.example.OS.until.OtherUntil;


public class ViewModePixel extends LinearLayout implements View.OnClickListener {
    private ImageView imHold;
    private ImageView imMute;
    private ImageView imRec;
    private ImageView imSpeaker;
    private ModeCallResult modeCallResult;
    private boolean rec;

    public void setModeCallResult(ModeCallResult modeCallResult) {
        this.modeCallResult = modeCallResult;
    }

    public ViewModePixel(Context context) {
        super(context);
        init();
    }

    private void init() {
        int i = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 7.2d) / 100.0d);
        int i2 = i * 2;
        setOrientation(VERTICAL);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setGravity(1);
        linearLayout.setWeightSum(4.0f);
        addView(linearLayout, new LayoutParams(-1, i2));
        addViewC(i, 26, linearLayout, R.drawable.ic_contact_galaxy);
        addViewC(i, 24, linearLayout, R.drawable.ic_hold_galaxy);
        addViewC(i, 25, linearLayout, R.drawable.ic_rec);
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(HORIZONTAL);
        linearLayout2.setGravity(1);
        linearLayout2.setWeightSum(4.0f);
        addView(linearLayout2, new LayoutParams(-1, i2));
        addViewC(i, 23, linearLayout2, R.drawable.ic_volume);
        addViewC(i, 21, linearLayout2, R.drawable.ic_muted);
        addViewC(i, 22, linearLayout2, R.drawable.ic_pad_galaxy);
    }

    private void addViewC(int i, int i2, LinearLayout linearLayout, int i3) {
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setBackgroundResource(R.drawable.sel_tran);
        linearLayout2.setId(i2);
        linearLayout2.setGravity(17);
        linearLayout2.setOrientation(1);
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
        ModeCallResult modeCallResult = this.modeCallResult;
        if (modeCallResult != null) {
            modeCallResult.onModeClick(view.getId());
            if (view.getId() == 25 && !this.rec) {
                if (Build.VERSION.SDK_INT < 29 || OtherUntil.isAccessibilitySettingsOn(getContext())) {
                    this.rec = true;
                    this.imRec.setImageResource(R.drawable.ic_rec_on);
                    return;
                }
                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                intent.addFlags(1342177280);
                getContext().startActivity(intent);
            }
        }
    }
}

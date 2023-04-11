package com.demo.example.OS.screen.iphone;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.example.R;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.screen.ModeCallResult;
import com.demo.example.OS.until.OtherUntil;


public class ViewCallMode extends RelativeLayout implements View.OnClickListener {
    private ImageView imHold;
    private ImageView imMute;
    private ImageView imRec;
    private ImageView imSpeaker;
    private ModeCallResult modeCallResult;
    private TextView tvRec;

    public void setModeCallResult(ModeCallResult modeCallResult) {
        this.modeCallResult = modeCallResult;
    }

    public ViewCallMode(Context context) {
        super(context);
        init();
    }

    public ViewCallMode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ViewCallMode(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        int i = getResources().getDisplayMetrics().widthPixels / 5;
        int i2 = (int) ((((float) getResources().getDisplayMetrics().widthPixels) * 7.2f) / 100.0f);
        int i3 = (int) ((((float) getResources().getDisplayMetrics().widthPixels) * 4.3f) / 100.0f);
        View view = new View(getContext());
        view.setId(20);
        LayoutParams layoutParams = new LayoutParams(i3, i3);
        layoutParams.addRule(13);
        addView(view, layoutParams);
        this.imRec = imNum(25);
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(14);
        layoutParams2.addRule(3, view.getId());
        addView(this.imRec, layoutParams2);
        ImageView imNum = imNum(26);
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(1, this.imRec.getId());
        layoutParams3.addRule(3, view.getId());
        layoutParams3.setMargins(i2, 0, 0, 0);
        addView(imNum, layoutParams3);
        this.imHold = imNum(24);
        LayoutParams layoutParams4 = new LayoutParams(i, i);
        layoutParams4.addRule(0, this.imRec.getId());
        layoutParams4.addRule(3, view.getId());
        layoutParams4.setMargins(0, 0, i2, 0);
        addView(this.imHold, layoutParams4);
        initTv(getResources().getString(R.string.rec), this.imRec, -1, false);
        initTv(getResources().getString(R.string.contacts), imNum, 122, false);
        initTv(getResources().getString(R.string.hold), this.imHold, 121, false);
        initTv(getResources().getString(R.string.mute), this.imHold, 11, true);
        initTv(getResources().getString(R.string.keypad), this.imRec, 12, true);
        initTv(getResources().getString(R.string.speaker), imNum, 13, true);
        this.imMute = imNum(21);
        LayoutParams layoutParams5 = new LayoutParams(i, i);
        layoutParams5.addRule(5, 11);
        layoutParams5.addRule(2, 11);
        addView(this.imMute, layoutParams5);
        ImageView imNum2 = imNum(22);
        LayoutParams layoutParams6 = new LayoutParams(i, i);
        layoutParams6.addRule(5, 12);
        layoutParams6.addRule(2, 12);
        addView(imNum2, layoutParams6);
        this.imSpeaker = imNum(23);
        LayoutParams layoutParams7 = new LayoutParams(i, i);
        layoutParams7.addRule(5, 13);
        layoutParams7.addRule(2, 13);
        addView(this.imSpeaker, layoutParams7);
    }

    private ImageView imNum(int i) {
        int dimension = ((int) getResources().getDimension(R.dimen.size_add_call)) / 4;
        ImageView imageView = new ImageView(getContext());
        imageView.setPadding(dimension, dimension, dimension, dimension);
        imageView.setOnClickListener(this);
        switch (i) {
            case 21:
                imageView.setImageResource(R.drawable.ic_muted);
                break;
            case 22:
                imageView.setImageResource(R.drawable.ic_key);
                break;
            case 23:
                imageView.setImageResource(R.drawable.ic_volume);
                break;
            case 24:
                imageView.setImageResource(R.drawable.ic_hold);
                break;
            case 25:
                imageView.setImageResource(R.drawable.ic_rec);
                break;
            case 26:
                imageView.setImageResource(R.drawable.ic_contact);
                break;
        }
        imageView.setId(i);
        imageView.setBackground(OtherUntil.selNum(getContext()));
        return imageView;
    }

    public void onMute(boolean z) {
        if (z) {
            this.imMute.setBackgroundResource(R.drawable.bg_while);
            this.imMute.setColorFilter(-16777216);
            return;
        }
        this.imMute.clearColorFilter();
        this.imMute.setBackground(OtherUntil.selNum(getContext()));
    }

    public void onSpeaker(boolean z) {
        if (z) {
            this.imSpeaker.setBackgroundResource(R.drawable.bg_while);
            this.imSpeaker.setColorFilter(-16777216);
            return;
        }
        this.imSpeaker.clearColorFilter();
        this.imSpeaker.setBackground(OtherUntil.selNum(getContext()));
    }

    public void onHold(boolean z) {
        if (z) {
            this.imHold.setBackgroundResource(R.drawable.bg_while);
            this.imHold.setColorFilter(-16777216);
            return;
        }
        this.imHold.clearColorFilter();
        this.imHold.setBackground(OtherUntil.selNum(getContext()));
    }

    private void initTv(String str, ImageView imageView, int i, boolean z) {
        MediumText mediumText = new MediumText(getContext());
        if (i != -1) {
            mediumText.setId(i);
        } else {
            this.tvRec = mediumText;
        }
        mediumText.setText(str);
        mediumText.setTextColor(-1);
        mediumText.setTextSize(2, 10.5f);
        mediumText.setGravity(17);
        mediumText.setLines(2);
        mediumText.setEllipsize(TextUtils.TruncateAt.END);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(5, imageView.getId());
        layoutParams.addRule(7, imageView.getId());
        if (!z) {
            layoutParams.addRule(3, imageView.getId());
        } else {
            layoutParams.addRule(2, 20);
        }
        addView(mediumText, layoutParams);
    }

    @Override 
    public void onClick(View view) {
        if (this.modeCallResult != null) {
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
}

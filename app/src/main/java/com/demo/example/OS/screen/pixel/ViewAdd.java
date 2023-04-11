package com.demo.example.OS.screen.pixel;

import android.content.Context;
import android.widget.RelativeLayout;

import com.demo.example.R;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.screen.ITFAddCall;


public class ViewAdd extends RelativeLayout {
    private ViewAddCallPixel viewAddCallPixel;

    public void setItfAddCall(ITFAddCall iTFAddCall) {
        this.viewAddCallPixel.setItfAddCall(iTFAddCall);
    }

    public ViewAdd(Context context) {
        super(context);
        init();
    }

    private void init() {
        ViewAddCallPixel viewAddCallPixel = new ViewAddCallPixel(getContext());
        this.viewAddCallPixel = viewAddCallPixel;
        viewAddCallPixel.setId(123321);
        int i = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d);
        LayoutParams layoutParams = new LayoutParams(i, -1);
        layoutParams.addRule(14);
        addView(this.viewAddCallPixel, layoutParams);
        MediumText initText = initText(getResources().getString(R.string.swipe_up_to_answer));
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.addRule(14);
        int i2 = i / 4;
        layoutParams2.setMargins(0, i2, 0, 0);
        addView(initText, layoutParams2);
        MediumText initText2 = initText(getResources().getString(R.string.swipe_down_to_reject));
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        layoutParams3.addRule(14);
        layoutParams3.addRule(12);
        layoutParams3.setMargins(0, 0, 0, i2);
        addView(initText2, layoutParams3);
    }

    private MediumText initText(String str) {
        MediumText mediumText = new MediumText(getContext());
        mediumText.setTextColor(-1);
        mediumText.setAlpha(0.8f);
        mediumText.setText(str);
        mediumText.setTextSize(2, 9.0f);
        return mediumText;
    }
}

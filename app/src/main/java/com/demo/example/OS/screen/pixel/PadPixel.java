package com.demo.example.OS.screen.pixel;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.custom.BoldText;
import com.demo.example.OS.screen.ITFPadResult;
import com.demo.example.OS.until.OtherUntil;


public class PadPixel extends LinearLayout implements View.OnClickListener {
    private boolean isDark;
    private ITFPadResult itfPadResult;

    public void setItfPadResult(ITFPadResult iTFPadResult) {
        this.itfPadResult = iTFPadResult;
    }

    public PadPixel(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setBackgroundColor(-1);
        int i = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d);
        setPadding(0, i / 2, 0, (i * 5) / 4);
        setOrientation(HORIZONTAL);
        LinearLayout initLL = initLL();
        addViewNum(initLL, 1);
        addViewNum(initLL, 2);
        addViewNum(initLL, 3);
        LinearLayout initLL2 = initLL();
        addViewNum(initLL2, 4);
        addViewNum(initLL2, 5);
        addViewNum(initLL2, 6);
        LinearLayout initLL3 = initLL();
        addViewNum(initLL3, 7);
        addViewNum(initLL3, 8);
        addViewNum(initLL3, 9);
        LinearLayout initLL4 = initLL();
        addViewNum(initLL4, -1);
        addViewNum(initLL4, 0);
        addViewNum(initLL4, -2);
        LinearLayout initLL5 = initLL();
        initLL5.addView(new View(getContext()), new LayoutParams(0, i, 2.0f));
        addViewNum(initLL5, -3);
    }

    private LinearLayout initLL() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setGravity(17);
        linearLayout.setWeightSum(4.0f);
        addView(linearLayout, new LayoutParams(-1, -2));
        return linearLayout;
    }

    private void addViewNum(LinearLayout linearLayout, int i) {
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOnClickListener(this);
        linearLayout2.setOrientation(VERTICAL);
        linearLayout2.setGravity(1);
        linearLayout2.setId(i + 60);
        int dpToPixel = OtherUntil.dpToPixel(3, getContext());
        linearLayout2.setPadding(dpToPixel, dpToPixel, dpToPixel, dpToPixel);
        linearLayout.addView(linearLayout2, new LayoutParams(0, -2, 1.0f));
        BoldText boldText = new BoldText(getContext());
        boldText.setTextSize(2, 22.0f);
        if (this.isDark) {
            boldText.setTextColor(-1);
        } else {
            boldText.setTextColor(-16777216);
        }
        if (i == -1) {
            boldText.setText("*");
        } else if (i == -2) {
            boldText.setText("#");
        } else if (i >= 0) {
            boldText.setText(i + "");
        } else {
            boldText.setTextSize(2, 9.0f);
            boldText.setText(getResources().getString(R.string.hide));
            boldText.setGravity(17);
            linearLayout2.addView(boldText, new LayoutParams(-1, (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d)));
            return;
        }
        linearLayout2.setBackgroundResource(R.drawable.sel_tran);
        linearLayout2.addView(boldText, new LayoutParams(-2, -2));
    }

    @Override 
    public void onClick(View view) {
        if (view != this) {
            switch (view.getId()) {
                case 57:
                    setVisibility(GONE);
                    startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_down));
                    return;
                case 58:
                    this.itfPadResult.onTextResult("#");
                    return;
                case 59:
                    this.itfPadResult.onTextResult("*");
                    return;
                default:
                    ITFPadResult iTFPadResult = this.itfPadResult;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(view.getId() - 60);
                    iTFPadResult.onTextResult(sb.toString());
                    return;
            }
        }
    }
}

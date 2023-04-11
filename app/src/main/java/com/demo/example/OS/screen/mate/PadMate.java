package com.demo.example.OS.screen.mate;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.custom.BoldText;
import com.demo.example.OS.screen.ITFPadResult;
import com.demo.example.OS.until.OtherUntil;


public class PadMate extends LinearLayout implements View.OnClickListener {
    private ITFPadResult itfPadResult;

    public void setItfPadResult(ITFPadResult iTFPadResult) {
        this.itfPadResult = iTFPadResult;
    }

    public PadMate(Context context) {
        super(context);
        init();
    }

    private void init() {
        int i = getResources().getDisplayMetrics().widthPixels;
        setOrientation(1);
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
    }

    private LinearLayout initLL() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        linearLayout.setWeightSum(4.0f);
        addView(linearLayout, new LayoutParams(-1, -2));
        return linearLayout;
    }

    private void addViewNum(LinearLayout linearLayout, int i) {
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOnClickListener(this);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(1);
        linearLayout2.setId(i + 60);
        int dpToPixel = OtherUntil.dpToPixel(3, getContext());
        linearLayout2.setPadding(dpToPixel, dpToPixel, dpToPixel, dpToPixel);
        linearLayout.addView(linearLayout2, new LayoutParams(0, -2, 1.0f));
        BoldText boldText = new BoldText(getContext());
        boldText.setTextSize(2, 22.0f);
        boldText.setTextColor(-1);
        if (i == -1) {
            boldText.setText("*");
        } else if (i == -2) {
            boldText.setText("#");
        } else if (i >= 0) {
            boldText.setText(i + "");
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

package com.demo.example.OS.screen.s20;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.custom.BoldText;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.screen.ITFPadResult;
import com.demo.example.OS.until.OtherUntil;


public class PadGalaxy extends LinearLayout implements View.OnClickListener {
    private ITFPadResult padGalaxyResult;

    public void setPadGalaxyResult(ITFPadResult iTFPadResult) {
        this.padGalaxyResult = iTFPadResult;
    }

    public PadGalaxy(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = new View(getContext());
        view.setBackgroundColor(-1);
        LayoutParams layoutParams = new LayoutParams(-1, OtherUntil.dpToPixel(2, getContext()));
        layoutParams.setMargins(OtherUntil.dpToPixel(25, getContext()), 0, OtherUntil.dpToPixel(25, getContext()), OtherUntil.dpToPixel(5, getContext()));
        addView(view, layoutParams);
        LinearLayout initLL = initLL();
        addViewNum(initLL, 1, "");
        addViewNum(initLL, 2, "ABC");
        addViewNum(initLL, 3, "DEF");
        LinearLayout initLL2 = initLL();
        addViewNum(initLL2, 4, "GHI");
        addViewNum(initLL2, 5, "JKL");
        addViewNum(initLL2, 6, "MNO");
        LinearLayout initLL3 = initLL();
        addViewNum(initLL3, 7, "PQRS");
        addViewNum(initLL3, 8, "TUV");
        addViewNum(initLL3, 9, "WXYZ");
        LinearLayout initLL4 = initLL();
        addViewNum(initLL4, -1, "*");
        addViewNum(initLL4, 0, "+");
        addViewNum(initLL4, -2, "#");
        View view2 = new View(getContext());
        view2.setBackgroundColor(-1);
        LayoutParams layoutParams2 = new LayoutParams(-1, OtherUntil.dpToPixel(2, getContext()));
        layoutParams2.setMargins(OtherUntil.dpToPixel(25, getContext()), 0, OtherUntil.dpToPixel(25, getContext()), 0);
        addView(view2, layoutParams2);
    }

    private LinearLayout initLL() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setGravity(17);
        linearLayout.setWeightSum(4.0f);
        addView(linearLayout, new LayoutParams(-1, -2));
        return linearLayout;
    }

    private void addViewNum(LinearLayout linearLayout, int i, String str) {
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setBackgroundResource(R.drawable.sel_tran);
        linearLayout2.setOnClickListener(this);
        linearLayout2.setOrientation(VERTICAL);
        linearLayout2.setGravity(1);
        linearLayout2.setId(i + 60);
        linearLayout.addView(linearLayout2, new LayoutParams(0, -2, 1.0f));
        BoldText boldText = new BoldText(getContext());
        boldText.setTextColor(-1);
        boldText.setTextSize(2, 22.0f);
        linearLayout2.addView(boldText, new LayoutParams(-2, -2));
        if (i == -1) {
            boldText.setText("*");
        } else if (i == -2) {
            boldText.setText("#");
        } else {
            boldText.setText(i + "");
            MediumText mediumText = new MediumText(getContext());
            mediumText.setText(str);
            mediumText.setTextColor(-1);
            mediumText.setTextSize(2, 9.0f);
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.setMargins(0, -OtherUntil.dpToPixel(8, getContext()), 0, OtherUntil.dpToPixel(5, getContext()));
            linearLayout2.addView(mediumText, layoutParams);
        }
    }

    @Override 
    public void onClick(View view) {
        int id = view.getId();
        if (id == -2) {
            this.padGalaxyResult.onTextResult("#");
        } else if (id != -1) {
            ITFPadResult iTFPadResult = this.padGalaxyResult;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(view.getId() - 60);
            iTFPadResult.onTextResult(sb.toString());
        } else {
            this.padGalaxyResult.onTextResult("*");
        }
    }
}

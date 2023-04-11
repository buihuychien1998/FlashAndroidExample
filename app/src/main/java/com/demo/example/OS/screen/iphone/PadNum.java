package com.demo.example.OS.screen.iphone;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.example.R;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.until.OtherUntil;


public class PadNum extends RelativeLayout implements View.OnClickListener {
    private PadResult padResult;

    
    public interface PadResult {
        void onViewClick(boolean z, String str);
    }

    public void setPadResult(PadResult padResult) {
        this.padResult = padResult;
    }

    public PadNum(Context context) {
        super(context);
        init();
    }

    private void init() {
        int i = getResources().getDisplayMetrics().widthPixels / 5;
        int i2 = (int) ((((float) getResources().getDisplayMetrics().widthPixels) * 7.2f) / 100.0f);
        int i3 = (int) ((((float) getResources().getDisplayMetrics().widthPixels) * 4.3f) / 100.0f);
        View view = new View(getContext());
        view.setId(1);
        LayoutParams layoutParams = new LayoutParams(i3, i3);
        layoutParams.addRule(13);
        addView(view, layoutParams);
        ImageView imNum = imNum(65);
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(14);
        layoutParams2.addRule(8, view.getId());
        layoutParams2.setMargins(i2, i3, i2, i3);
        addView(imNum, layoutParams2);
        ImageView imNum2 = imNum(62);
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(14);
        layoutParams3.addRule(2, imNum.getId());
        addView(imNum2, layoutParams3);
        ImageView imNum3 = imNum(61);
        LayoutParams layoutParams4 = new LayoutParams(i, i);
        layoutParams4.addRule(16, imNum.getId());
        layoutParams4.addRule(2, imNum.getId());
        addView(imNum3, layoutParams4);
        ImageView imNum4 = imNum(63);
        LayoutParams layoutParams5 = new LayoutParams(i, i);
        layoutParams5.addRule(1, imNum.getId());
        layoutParams5.addRule(2, imNum.getId());
        addView(imNum4, layoutParams5);
        ImageView imNum5 = imNum(64);
        LayoutParams layoutParams6 = new LayoutParams(i, i);
        layoutParams6.addRule(0, imNum.getId());
        layoutParams6.addRule(6, imNum.getId());
        addView(imNum5, layoutParams6);
        ImageView imNum6 = imNum(66);
        LayoutParams layoutParams7 = new LayoutParams(i, i);
        layoutParams7.addRule(1, imNum.getId());
        layoutParams7.addRule(6, imNum.getId());
        addView(imNum6, layoutParams7);
        ImageView imNum7 = imNum(68);
        LayoutParams layoutParams8 = new LayoutParams(i, i);
        layoutParams8.addRule(14);
        layoutParams8.addRule(3, imNum.getId());
        addView(imNum7, layoutParams8);
        ImageView imNum8 = imNum(67);
        LayoutParams layoutParams9 = new LayoutParams(i, i);
        layoutParams9.addRule(0, imNum.getId());
        layoutParams9.addRule(3, imNum.getId());
        addView(imNum8, layoutParams9);
        ImageView imNum9 = imNum(69);
        LayoutParams layoutParams10 = new LayoutParams(i, i);
        layoutParams10.addRule(1, imNum.getId());
        layoutParams10.addRule(3, imNum.getId());
        addView(imNum9, layoutParams10);
        ImageView imNum10 = imNum(60);
        LayoutParams layoutParams11 = new LayoutParams(i, i);
        layoutParams11.addRule(14);
        layoutParams11.addRule(3, imNum7.getId());
        layoutParams11.setMargins(0, i3, 0, i3);
        addView(imNum10, layoutParams11);
        ImageView imNum11 = imNum(70);
        LayoutParams layoutParams12 = new LayoutParams(i, i);
        layoutParams12.addRule(0, imNum.getId());
        layoutParams12.addRule(6, imNum10.getId());
        addView(imNum11, layoutParams12);
        ImageView imNum12 = imNum(71);
        LayoutParams layoutParams13 = new LayoutParams(i, i);
        layoutParams13.addRule(1, imNum.getId());
        layoutParams13.addRule(6, imNum10.getId());
        addView(imNum12, layoutParams13);
        MediumText mediumText = new MediumText(getContext());
        mediumText.setId(72);
        mediumText.setOnClickListener(this);
        mediumText.setText(getResources().getString(R.string.hide));
        mediumText.setTextColor(-1);
        mediumText.setTextSize(2, 10.0f);
        mediumText.setGravity(17);
        LayoutParams layoutParams14 = new LayoutParams(i, i);
        layoutParams14.addRule(3, imNum10.getId());
        layoutParams14.addRule(1, imNum.getId());
        addView(mediumText, layoutParams14);
    }

    private ImageView imNum(int i) {
        ImageView imageView = new ImageView(getContext());
        switch (i) {
            case 60:
                imageView.setImageResource(R.drawable.num0);
                break;
            case 61:
                imageView.setImageResource(R.drawable.num1);
                break;
            case 62:
                imageView.setImageResource(R.drawable.num2);
                break;
            case 63:
                imageView.setImageResource(R.drawable.num3);
                break;
            case 64:
                imageView.setImageResource(R.drawable.num4);
                break;
            case 65:
                imageView.setImageResource(R.drawable.num5);
                break;
            case 66:
                imageView.setImageResource(R.drawable.num6);
                break;
            case 67:
                imageView.setImageResource(R.drawable.num7);
                break;
            case 68:
                imageView.setImageResource(R.drawable.num8);
                break;
            case 69:
                imageView.setImageResource(R.drawable.num9);
                break;
            case 70:
                imageView.setImageResource(R.drawable.num_star);
                break;
            case 71:
                imageView.setImageResource(R.drawable.num_th);
                break;
        }
        imageView.setId(i);
        imageView.setBackground(OtherUntil.selNum(getContext()));
        imageView.setOnClickListener(this);
        return imageView;
    }

    @Override 
    public void onClick(View view) {
        if (this.padResult != null) {
            switch (view.getId()) {
                case 70:
                    this.padResult.onViewClick(false, "*");
                    return;
                case 71:
                    this.padResult.onViewClick(false, "#");
                    return;
                case 72:
                    this.padResult.onViewClick(true, "");
                    return;
                default:
                    PadResult padResult = this.padResult;
                    StringBuilder sb = new StringBuilder();
                    sb.append(view.getId() - 60);
                    sb.append("");
                    padResult.onViewClick(false, sb.toString());
                    return;
            }
        }
    }
}

package com.demo.example.OS.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.OS.custom.MediumText;
import com.demo.example.OS.until.OtherUntil;


public abstract class BaseDialogIOS extends Dialog implements View.OnClickListener {
    LinearLayout llAdd;
    private LinearLayout llDialog;
    private View vDialog;

    abstract void action(int i);

    
    public BaseDialogIOS(Context context) {
        super(context, R.style.Theme_Dialog);
        requestWindowFeature(1);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(17170445);
        }
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_ios);
        this.llAdd = (LinearLayout) findViewById(R.id.ll_add);
        this.llDialog = (LinearLayout) findViewById(R.id.ll_dialog);
        findViewById(R.id.tv_cancel_dialog).setOnClickListener(this);
        View findViewById = findViewById(R.id.v_dialog);
        this.vDialog = findViewById;
        findViewById.setOnClickListener(this);
        OtherUntil.anim((View) this.llDialog, (int) R.anim.show_dialog, false);
        OtherUntil.anim(this.vDialog, (int) R.anim.fade_in, false);
    }

    @Override 
    public void onClick(View view) {
        hideDialog(view.getId());
    }

    public void hideDialog(final int i) {
        OtherUntil.anim((View) this.llDialog, (int) R.anim.hide_dialog, true);
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { 
            @Override 
            public void onAnimationRepeat(Animation animation) {
            }

            @Override 
            public void onAnimationStart(Animation animation) {
            }

            @Override 
            public void onAnimationEnd(Animation animation) {
                BaseDialogIOS.this.action(i);
                BaseDialogIOS.this.vDialog.setVisibility(View.GONE);
                BaseDialogIOS.this.cancel();
            }
        });
        this.vDialog.startAnimation(loadAnimation);
    }

    void addText(int i, String str, int i2) {
        this.llAdd.addView(createText(i, str, i2, 16), new LinearLayout.LayoutParams(-1, -2));
    }

    void addText(int i, String str, int i2, int i3) {
        this.llAdd.addView(createText(i, str, i2, i3), new LinearLayout.LayoutParams(-1, -2));
    }

    void addDivider() {
        this.llAdd.addView(viewDivider(), new LinearLayout.LayoutParams(-1, 1));
    }

    private MediumText createText(int i, String str, int i2, int i3) {
        MediumText mediumText = new MediumText(getContext());
        mediumText.setGravity(1);
        mediumText.setId(i);
        mediumText.setText(str);
        mediumText.setTextColor(i2);
        mediumText.setTextSize(2, (float) i3);
        int dimension = (int) getContext().getResources().getDimension(R.dimen._8dp);
        mediumText.setPadding(0, dimension, 0, dimension);
        mediumText.setOnClickListener(this);
        return mediumText;
    }

    private View viewDivider() {
        View view = new View(getContext());
        view.setBackgroundColor(getContext().getResources().getColor(R.color.divider));
        return view;
    }
}

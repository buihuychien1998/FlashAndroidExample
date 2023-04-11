package com.demo.example.OS.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.example.R;


public class LoadingDialog extends Dialog {
    private ImageView im;
    private TextView tv;

    public LoadingDialog(Context context) {
        super(context);
        requestWindowFeature(1);
        setCancelable(false);
    }

    @Override 
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_loading);
        this.tv = (TextView) findViewById(R.id.tv_dialog);
        this.im = (ImageView) findViewById(R.id.im);
    }

    public void showWithText(String str) {
        show();
        setText(str);
    }

    public void setText(String str) {
        TextView textView = this.tv;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override 
    public void show() {
        super.show();
        if (this.im != null) {
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(2000);
            rotateAnimation.setRepeatCount(-1);
            this.im.startAnimation(rotateAnimation);
        }
    }

    @Override 
    public void cancel() {
        super.cancel();
        ImageView imageView = this.im;
        if (imageView != null) {
            imageView.clearAnimation();
        }
    }
}

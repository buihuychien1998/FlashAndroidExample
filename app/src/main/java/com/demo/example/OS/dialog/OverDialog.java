package com.demo.example.OS.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.demo.example.R;


public class OverDialog extends Dialog implements View.OnClickListener {
    private final OverResult overResult;

    
    public interface OverResult {
        void onPay();
    }

    public OverDialog(Context context, OverResult overResult) {
        super(context);
        this.overResult = overResult;
        requestWindowFeature(1);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(17170445);
        }
    }

    @Override 
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_over);
        findViewById(R.id.tv_btn).setOnClickListener(this);
        findViewById(R.id.tv_ads).setOnClickListener(this);
    }

    @Override 
    public void onClick(View view) {
        cancel();
        if (view.getId() == R.id.tv_btn) {
            this.overResult.onPay();
        }
    }
}

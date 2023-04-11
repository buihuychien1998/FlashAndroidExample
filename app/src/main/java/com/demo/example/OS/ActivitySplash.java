package com.demo.example.OS;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.example.OS.until.OtherUntil;



public class ActivitySplash extends AppCompatActivity {


    
    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (OtherUntil.checkPer(this)) {
            finish();
        } else {
            gotoMainActivity();
        }

    }

    
    public void gotoMainActivity() {
        startActivity(new Intent(this, VideoActivity.class));
        finish();
    }
}

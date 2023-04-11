package com.demo.example.OS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.demo.example.R;

import java.util.Random;


public class ActivitySettingFake extends AppCompatActivity {
    private EditText edt;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder(ActivitySettingFake.this.edt.getText().toString());
            if (sb.length() == 0) {
                for (int i = 0; i < 8; i++) {
                    sb.append(new Random().nextInt(10));
                }
            }
            Intent intent = new Intent(ActivitySettingFake.this, ActivityFakeCall.class);
            intent.setFlags(268435456);
            intent.putExtra("num", sb.toString());
            intent.putExtra(NotificationCompat.CATEGORY_STATUS, 2);
            ActivitySettingFake.this.startActivity(intent);
        }
    };
    private int sec;
    private TextView tvSec;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setNavigationBarColor(getColor(R.color.colorNavi));
        getWindow().setStatusBarColor(-1);
        setContentView(R.layout.activity_setting_fake);
        init();

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
        adAdmob.FullscreenAd(this);

    }

    private void init() {
        this.edt = (EditText) findViewById(R.id.edt_name);
        TextView textView = (TextView) findViewById(R.id.tv_sec);
        this.tvSec = textView;
        textView.setText(this.sec + "s");
        ((SeekBar) findViewById(R.id.sb)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                ActivitySettingFake.this.sec = i;
                TextView textView2 = ActivitySettingFake.this.tvSec;
                textView2.setText(ActivitySettingFake.this.sec + "s");
            }
        });
    }

    public void onClickFake(View view) {
        new Handler().postDelayed(this.runnable, (long) (this.sec * 1000));
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        startActivity(intent);
    }
}

package com.demo.example.OS;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.example.R;
import com.demo.example.OS.until.MyShare;


public class ShowActivity extends AppCompatActivity {
    private String path;
    private VideoView vv;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(1792);
        getWindow().setNavigationBarColor(0);
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        window.setStatusBarColor(0);
        setContentView(R.layout.activity_show);
        this.path = getIntent().getStringExtra("data");
        initView();
    }

    private void initView() {
        VideoView videoView = (VideoView) findViewById(R.id.vv);
        this.vv = videoView;
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0.0f, 0.0f);
            }
        });
        this.vv.setVideoURI(Uri.parse(this.path));
        this.vv.start();
    }

    


    
    @Override 
    public void onStop() {
        super.onStop();
        if (this.vv.isPlaying()) {
            this.vv.stopPlayback();
        }
    }

    public void apply(View view) {
        MyShare.putPhoto(this, this.path);
        Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show();
        finish();
    }
}

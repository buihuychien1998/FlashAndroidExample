package com.demo.example.OS.screen.iphone;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.demo.example.R;
import com.demo.example.OS.screen.ITFAddCall;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


public class ViewAddCall extends RelativeLayout {
    private ITFAddCall addCallResult;
    private Handler handler;
    private ImageView imCall;
    private int size;
    private int start;
    private ShimmerTextView tv;
    private boolean up;
    private View vBg;
    private int touch = 0;
    private Runnable runnable = new Runnable() { 
        @Override 
        public void run() {
            int i = 20;
            if (ViewAddCall.this.up) {
                int width = (ViewAddCall.this.getWidth() - ViewAddCall.this.touch) / 2;
                if (width >= 20) {
                    i = width;
                }
                ViewAddCall.access$112(ViewAddCall.this, i);
                if (ViewAddCall.this.touch >= ViewAddCall.this.getWidth() - ViewAddCall.this.size) {
                    ViewAddCall viewAddCall = ViewAddCall.this;
                    viewAddCall.touch = viewAddCall.getWidth() - ViewAddCall.this.size;
                    ViewAddCall.this.update();
                    if (ViewAddCall.this.addCallResult != null) {
                        ViewAddCall.this.addCallResult.onAddCall();
                        ViewAddCall.this.touch = 0;
                        return;
                    }
                    return;
                }
                ViewAddCall.this.update();
                ViewAddCall.this.handler.postDelayed(this, 15);
                return;
            }
            int i2 = ViewAddCall.this.touch / 2;
            if (i2 >= 20) {
                i = i2;
            }
            ViewAddCall.access$120(ViewAddCall.this, i);
            if (ViewAddCall.this.touch <= 0) {
                ViewAddCall.this.touch = 0;
                ViewAddCall.this.update();
                return;
            }
            ViewAddCall.this.update();
            ViewAddCall.this.handler.postDelayed(this, 15);
        }
    };

    static  int access$112(ViewAddCall viewAddCall, int i) {
        int i2 = viewAddCall.touch + i;
        viewAddCall.touch = i2;
        return i2;
    }

    static  int access$120(ViewAddCall viewAddCall, int i) {
        int i2 = viewAddCall.touch - i;
        viewAddCall.touch = i2;
        return i2;
    }

    public void setAddCallResult(ITFAddCall iTFAddCall) {
        this.addCallResult = iTFAddCall;
    }

    public ViewAddCall(Context context) {
        super(context);
        init();
    }

    public ViewAddCall(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ViewAddCall(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        int i = getResources().getDisplayMetrics().widthPixels / 5;
        this.size = i;
        int i2 = (int) ((((float) i) * 5.6f) / 100.0f);
        View view = new View(getContext());
        this.vBg = view;
        view.setBackgroundResource(R.drawable.bg_view_no);
        addView(this.vBg, new LayoutParams(-1, -1));
        ShimmerTextView shimmerTextView = new ShimmerTextView(getContext());
        this.tv = shimmerTextView;
        shimmerTextView.setTextColor(-16777216);
        this.tv.setText(getResources().getString(R.string.slide_to_answer));
        this.tv.setTextSize(2, 15.0f);
        this.tv.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/sf-ui-display-medium-58646be638f96.otf"));
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(2500).setStartDelay(1000);
        shimmer.start(this.tv);
        this.tv.setGravity(17);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(this.size, 0, 0, 0);
        addView(this.tv, layoutParams);
        ImageView imageView = new ImageView(getContext());
        this.imCall = imageView;
        imageView.setPadding(i2, i2, i2, i2);
        this.imCall.setImageResource(R.drawable.ic_call);
        View view2 = this.imCall;
        int i3 = this.size;
        addView(view2, new LayoutParams(i3, i3));
        this.handler = new Handler();
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        boolean z = false;
        if (action == 0) {
            this.start = (int) motionEvent.getX();
            this.touch = 0;
            update();
            this.handler.removeCallbacks(this.runnable);
        } else if (action != 1) {
            if (action == 2 && this.start < this.size) {
                int x = (int) (motionEvent.getX() - ((float) this.start));
                this.touch = x;
                if (x < 0) {
                    this.touch = 0;
                } else if (x > getWidth() - this.size) {
                    this.touch = getWidth() - this.size;
                }
                update();
            }
        } else if (this.start < this.size) {
            if (this.touch >= (getWidth() * 2) / 5) {
                z = true;
            }
            this.up = z;
            this.handler.post(this.runnable);
        }
        return true;
    }

    
    public void update() {
        if (this.touch == 0) {
            if (this.tv.getVisibility() == 8) {
                this.vBg.setBackgroundResource(R.drawable.bg_view_no);
                this.tv.setVisibility(View.VISIBLE);
            }
        } else if (this.tv.getVisibility() == 0) {
            this.vBg.setBackgroundResource(R.drawable.bg_view_y);
            this.tv.setVisibility(GONE);
        }
        LayoutParams layoutParams = (LayoutParams) this.vBg.getLayoutParams();
        layoutParams.setMargins(this.touch, 0, 0, 0);
        this.vBg.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = (LayoutParams) this.imCall.getLayoutParams();
        layoutParams2.setMargins(this.touch, 0, 0, 0);
        this.imCall.setLayoutParams(layoutParams2);
    }

    public void setTextContent(String str) {
        this.tv.setText(str);
    }
}

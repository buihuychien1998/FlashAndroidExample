package com.demo.example.OS.screen.pixel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.demo.example.R;
import com.demo.example.OS.screen.ITFAddCall;


public class ViewAddCallPixel extends View {
    private Bitmap bm;
    private Bitmap bm1;
    private Bitmap bm2;
    private Handler handler;
    private ITFAddCall itfAddCall;
    private Paint p;
    private Paint p1;
    private Paint p2;
    private Rect rect;
    private float run;
    private Runnable runnable = new Runnable() { 
        @Override 
        public void run() {
            ViewAddCallPixel.this.handler.postDelayed(this, 30);
            if (ViewAddCallPixel.this.tren) {
                if (ViewAddCallPixel.this.up) {
                    ViewAddCallPixel viewAddCallPixel = ViewAddCallPixel.this;
                    ViewAddCallPixel.access$316(viewAddCallPixel, (((float) (viewAddCallPixel.size / 2)) - ViewAddCallPixel.this.run) / 6.0f);
                    if (ViewAddCallPixel.this.run >= ((float) ((ViewAddCallPixel.this.size / 2) - 1))) {
                        ViewAddCallPixel viewAddCallPixel2 = ViewAddCallPixel.this;
                        viewAddCallPixel2.run = (float) ((viewAddCallPixel2.size / 2) - 1);
                        ViewAddCallPixel.this.up = false;
                    }
                } else {
                    ViewAddCallPixel.access$324(ViewAddCallPixel.this, 2.0f);
                    if (ViewAddCallPixel.this.run < 0.0f) {
                        ViewAddCallPixel.this.run = 0.0f;
                        ViewAddCallPixel.this.handler.removeCallbacks(this);
                        ViewAddCallPixel.this.handler.postDelayed(this, 800);
                        ViewAddCallPixel.this.tren = false;
                    }
                }
            } else if (!ViewAddCallPixel.this.up) {
                ViewAddCallPixel viewAddCallPixel3 = ViewAddCallPixel.this;
                ViewAddCallPixel.access$316(viewAddCallPixel3, (((float) ((-viewAddCallPixel3.size) / 2)) - ViewAddCallPixel.this.run) / 6.0f);
                if (ViewAddCallPixel.this.run <= ((float) (((-ViewAddCallPixel.this.size) / 2) + 1))) {
                    ViewAddCallPixel viewAddCallPixel4 = ViewAddCallPixel.this;
                    viewAddCallPixel4.run = (float) (((-viewAddCallPixel4.size) / 2) + 1);
                    ViewAddCallPixel.this.up = true;
                }
            } else {
                ViewAddCallPixel.access$316(ViewAddCallPixel.this, 2.0f);
                if (ViewAddCallPixel.this.run > 0.0f) {
                    ViewAddCallPixel.this.run = 0.0f;
                    ViewAddCallPixel.this.handler.removeCallbacks(this);
                    ViewAddCallPixel.this.handler.postDelayed(this, 800);
                    ViewAddCallPixel.this.tren = true;
                }
            }
            if (ViewAddCallPixel.this.rect != null) {
                ViewAddCallPixel.this.updateRect();
                ViewAddCallPixel.this.invalidate();
            }
        }
    };
    private int size;
    private float start;
    private boolean touch;
    private boolean tren;
    private boolean up;

    static  float access$316(ViewAddCallPixel viewAddCallPixel, float f) {
        float f2 = viewAddCallPixel.run + f;
        viewAddCallPixel.run = f2;
        return f2;
    }

    static  float access$324(ViewAddCallPixel viewAddCallPixel, float f) {
        float f2 = viewAddCallPixel.run - f;
        viewAddCallPixel.run = f2;
        return f2;
    }

    public void setItfAddCall(ITFAddCall iTFAddCall) {
        this.itfAddCall = iTFAddCall;
    }

    public ViewAddCallPixel(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.up = true;
        this.tren = true;
        this.size = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d);
        this.run = 0.0f;
        this.p = new Paint(1);
        this.p2 = new Paint(1);
        this.p1 = new Paint(1);
        this.bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_call);
        this.bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_call_pad);
        this.bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_end_call);
        this.p2.setAlpha(0);
        this.p1.setAlpha(0);
        this.handler = new Handler();
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.rect == null) {
            this.rect = new Rect((getWidth() - this.size) / 2, (getHeight() - this.size) / 2, (getWidth() + this.size) / 2, (getHeight() + this.size) / 2);
            this.handler.postDelayed(this.runnable, 800);
        }
        if (!this.touch) {
            canvas.drawBitmap(this.bm, (Rect) null, this.rect, (Paint) null);
            return;
        }
        if (this.p.getAlpha() > 2) {
            canvas.drawBitmap(this.bm, (Rect) null, this.rect, this.p);
        }
        if (this.p1.getAlpha() != 0) {
            canvas.drawBitmap(this.bm1, (Rect) null, this.rect, this.p1);
        }
        if (this.p2.getAlpha() != 0) {
            canvas.drawBitmap(this.bm2, (Rect) null, this.rect, this.p2);
        }
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        boolean z = false;
        if (action == 0) {
            if (motionEvent.getY() > ((float) ((getHeight() - this.size) / 2)) && motionEvent.getY() < ((float) ((getHeight() + this.size) / 2))) {
                z = true;
            }
            this.touch = z;
            if (z) {
                this.start = motionEvent.getY();
                this.tren = true;
                this.up = true;
                this.run = 0.0f;
                updateRect();
                stopAnim();
            }
        } else if (action != 1) {
            if (action == 2 && this.touch) {
                float y = this.start - motionEvent.getY();
                this.run = y;
                int i = this.size;
                if (y > ((float) i)) {
                    this.run = (float) i;
                } else if (y < ((float) (-i))) {
                    this.run = (float) (-i);
                }
                updateRect();
                if (this.run > 0.0f) {
                    this.p2.setAlpha(0);
                    this.p1.setAlpha((int) ((this.run * 255.0f) / ((float) this.size)));
                    this.p.setAlpha((int) (255.0f - ((this.run * 255.0f) / ((float) this.size))));
                } else {
                    this.p1.setAlpha(0);
                    this.p2.setAlpha((int) ((this.run * -255.0f) / ((float) this.size)));
                    this.p.setAlpha((int) (((this.run * 255.0f) / ((float) this.size)) + 255.0f));
                }
            }
        } else if (this.touch) {
            float f = this.run;
            int i2 = this.size;
            if (f == ((float) i2)) {
                this.itfAddCall.onAddCall();
            } else if (f == ((float) (-i2))) {
                this.itfAddCall.onCancel();
            } else {
                this.p.setAlpha(255);
                this.p1.setAlpha(0);
                this.p2.setAlpha(0);
                this.touch = false;
                this.tren = true;
                this.up = true;
                this.run = 0.0f;
                updateRect();
                this.handler.postDelayed(this.runnable, 800);
            }
        }
        invalidate();
        return true;
    }

    private void stopAnim() {
        this.handler.removeCallbacks(this.runnable);
    }

    
    public void updateRect() {
        int i = (int) this.run;
        this.rect.set((getWidth() - this.size) / 2, ((getHeight() - this.size) / 2) - i, (getWidth() + this.size) / 2, ((getHeight() + this.size) / 2) - i);
    }
}

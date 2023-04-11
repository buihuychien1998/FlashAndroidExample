package com.demo.example.OS.screen.s20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.example.R;
import com.demo.example.OS.screen.ITFAddCall;
import com.demo.example.OS.until.OtherUntil;


public class ViewAddCallGalaxy extends View {
    private ITFAddCall addCallGalaxyResult;
    private Bitmap bm1;
    private Bitmap bm2;
    private Handler handler;
    private Paint paint;
    private Rect r1;
    private Rect r2;
    private float radius;
    private Runnable runnable = new Runnable() { 
        @Override 
        public void run() {
            if (((double) ViewAddCallGalaxy.this.radius) < ((((double) ViewAddCallGalaxy.this.getResources().getDisplayMetrics().widthPixels) * 22.7d) / 100.0d) / 2.0d) {
                ViewAddCallGalaxy.this.handler.postDelayed(this, 30);
                ViewAddCallGalaxy.access$016(ViewAddCallGalaxy.this, 1.0f);
                ViewAddCallGalaxy.this.paint.setAlpha(100);
            } else {
                ViewAddCallGalaxy.this.handler.postDelayed(this, 35);
                int alpha = ViewAddCallGalaxy.this.paint.getAlpha() - 4;
                if (alpha <= 0) {
                    ViewAddCallGalaxy viewAddCallGalaxy = ViewAddCallGalaxy.this;
                    viewAddCallGalaxy.radius = (float) (viewAddCallGalaxy.size / 2);
                } else {
                    ViewAddCallGalaxy.this.paint.setAlpha(alpha);
                }
            }
            ViewAddCallGalaxy.this.invalidate();
        }
    };
    private int size;
    private boolean t1;
    private boolean t2;
    private int x1;
    private int x2;
    private int y;

    static  float access$016(ViewAddCallGalaxy viewAddCallGalaxy, float f) {
        float f2 = viewAddCallGalaxy.radius + f;
        viewAddCallGalaxy.radius = f2;
        return f2;
    }

    public ViewAddCallGalaxy(Context context) {
        super(context);
        init();
    }

    public ViewAddCallGalaxy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ViewAddCallGalaxy(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void setAddCallGalaxyResult(ITFAddCall iTFAddCall) {
        this.addCallGalaxyResult = iTFAddCall;
    }

    private void init() {
        this.bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_call);
        this.bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_end_call_galaxy);
        int i = getResources().getDisplayMetrics().widthPixels;
        this.size = (int) ((((double) getResources().getDisplayMetrics().widthPixels) * 16.7d) / 100.0d);
        int i2 = this.size;
        this.radius = (float) (i2 / 2);
        this.x1 = i2;
        this.x2 = i - i2;
        this.y = (((getResources().getDisplayMetrics().heightPixels / 2) + ((int) ((((double) getResources().getDisplayMetrics().widthPixels) * 57.6d) / 100.0d))) / 2) - OtherUntil.dpToPixel(40, getContext());
        int i3 = this.x1;
        int i4 = this.size;
        int i5 = this.y;
        this.r1 = new Rect(i3 - (i4 / 2), i5 - (i4 / 2), i3 + (i4 / 2), i5 + (i4 / 2));
        int i6 = this.x2;
        int i7 = this.size;
        int i8 = this.y;
        this.r2 = new Rect(i6 - (i7 / 2), i8 - (i7 / 2), i6 + (i7 / 2), i8 + (i7 / 2));
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(-1);
        this.paint.setAlpha(100);
        Handler handler = new Handler();
        this.handler = handler;
        handler.post(this.runnable);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.t1 && !this.t2) {
            this.paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float) this.x1, (float) this.y, this.radius, this.paint);
            canvas.drawCircle((float) this.x2, (float) this.y, this.radius, this.paint);
        } else if (this.paint.getAlpha() > 0) {
            float f = this.radius;
            float f2 = 2.0f * f;
            if (f2 < ((float) (this.size * 2))) {
                this.paint.setStyle(Paint.Style.FILL);
                f = f2;
            } else {
                this.paint.setStyle(Paint.Style.STROKE);
                Paint paint = this.paint;
                int i = this.size;
                paint.setStrokeWidth(((float) (i * 2)) * (1.0f - ((this.radius - ((float) i)) / ((float) i))));
            }
            if (this.t1) {
                canvas.drawCircle((float) this.x1, (float) this.y, f, this.paint);
            } else {
                canvas.drawCircle((float) this.x2, (float) this.y, f, this.paint);
            }
        }
        canvas.drawBitmap(this.bm1, (Rect) null, this.r1, (Paint) null);
        canvas.drawBitmap(this.bm2, (Rect) null, this.r2, (Paint) null);
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int action = motionEvent.getAction();
        boolean z2 = false;
        if (action == 0) {
            if (motionEvent.getY() > ((float) (this.y - (this.size / 2))) && motionEvent.getY() < ((float) (this.y + (this.size / 2)))) {
                float x = motionEvent.getX();
                int i = this.x1;
                int i2 = this.size;
                this.t1 = x > ((float) (i - (i2 / 2))) && x < ((float) (i + (i2 / 2)));
                int i3 = this.x2;
                if (x > ((float) (i3 - (i2 / 2))) && x < ((float) (i3 + (i2 / 2)))) {
                    z2 = true;
                }
                this.t2 = z2;
            }
            if (this.t1 || this.t2) {
                this.radius = 0.0f;
                invalidate();
                stopAnim();
            }
        } else if (action != 1) {
            if (action == 2 && ((z = this.t1) || this.t2)) {
                if (z) {
                    this.radius = (float) Math.sqrt(Math.pow((double) (motionEvent.getX() - ((float) this.x1)), 2.0d) + Math.pow((double) (motionEvent.getY() - ((float) this.y)), 2.0d));
                } else {
                    this.radius = (float) Math.sqrt(Math.pow((double) (motionEvent.getX() - ((float) this.x2)), 2.0d) + Math.pow((double) (motionEvent.getY() - ((float) this.y)), 2.0d));
                }
                float f = this.radius;
                int i4 = this.size;
                if (f > ((float) (i4 * 2))) {
                    this.radius = (float) (i4 * 2);
                    this.paint.setAlpha(0);
                } else {
                    this.paint.setAlpha(100);
                }
            }
        } else if (this.t1 || this.t2) {
            if (this.paint.getAlpha() != 0) {
                this.t2 = false;
                this.t1 = false;
                this.radius = (float) (this.size / 2);
                invalidate();
                startAnim();
            } else if (this.t1) {
                this.addCallGalaxyResult.onAddCall();
            } else {
                this.addCallGalaxyResult.onCancel();
            }
        }
        invalidate();
        return true;
    }

    private void startAnim() {
        stopAnim();
        this.handler.post(this.runnable);
    }

    private void stopAnim() {
        this.handler.removeCallbacks(this.runnable);
    }
}

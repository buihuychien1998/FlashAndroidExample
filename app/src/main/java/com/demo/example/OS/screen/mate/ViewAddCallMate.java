package com.demo.example.OS.screen.mate;

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
import com.demo.example.OS.until.OtherUntil;


public class ViewAddCallMate extends View {
    private Bitmap bm1;
    private Bitmap bm2;
    private int c;
    private Handler handler;
    private ITFAddCall itfAddCall;
    private Paint pDot;
    private Paint paint;
    private Rect r1;
    private Rect r2;
    private int run;
    private Runnable runnable = new Runnable() { 
        @Override 
        public void run() {
            ViewAddCallMate.this.handler.postDelayed(this, 200);
            ViewAddCallMate.access$108(ViewAddCallMate.this);
            if (ViewAddCallMate.this.run == 5) {
                ViewAddCallMate.this.run = 0;
            }
            ViewAddCallMate.this.invalidate();
        }
    };
    private int size;
    private int step;
    private boolean touch;

    static  int access$108(ViewAddCallMate viewAddCallMate) {
        int i = viewAddCallMate.run;
        viewAddCallMate.run = i + 1;
        return i;
    }

    public void setItfAddCall(ITFAddCall iTFAddCall) {
        this.itfAddCall = iTFAddCall;
    }

    public ViewAddCallMate(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.c = 0;
        this.handler = new Handler();
        Paint paint = new Paint(1);
        this.pDot = paint;
        paint.setColor(-1);
        this.pDot.setStyle(Paint.Style.FILL);
        this.pDot.setStrokeCap(Paint.Cap.ROUND);
        this.pDot.setStrokeJoin(Paint.Join.ROUND);
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(3.0f);
        this.paint.setColor(-1);
        this.size = (getResources().getDisplayMetrics().widthPixels * 18) / 100;
        this.bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_add_call_mate);
        this.bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_end_call_mate);
        this.handler.post(this.runnable);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.r1 == null) {
            int height = getHeight();
            int i = this.size;
            this.r1 = new Rect(0, (height - i) / 2, i, (getHeight() + this.size) / 2);
            this.r2 = new Rect(getWidth() - this.size, (getHeight() - this.size) / 2, getWidth(), (getHeight() + this.size) / 2);
            int i2 = this.size;
            this.step = (((getWidth() / 2) - (i2 / 2)) - i2) / 6;
        }
        if (!this.touch) {
            for (int i3 = 0; i3 < 5; i3++) {
                if (this.run == i3) {
                    this.pDot.setStrokeWidth((float) OtherUntil.dpToPixel(6, getContext()));
                } else {
                    this.pDot.setStrokeWidth((float) OtherUntil.dpToPixel(3, getContext()));
                }
                canvas.drawPoint((float) ((getWidth() / 2) + ((this.size * 3) / 4) + (this.step * i3)), (float) (getHeight() / 2), this.pDot);
                canvas.drawPoint((float) (((getWidth() / 2) - ((this.size * 3) / 4)) - (this.step * i3)), (float) (getHeight() / 2), this.pDot);
            }
        }
        canvas.drawCircle((float) ((getWidth() / 2) + this.c), (float) (getHeight() / 2), (float) (this.size / 2), this.paint);
        canvas.drawBitmap(this.bm1, (Rect) null, this.r1, (Paint) null);
        canvas.drawBitmap(this.bm2, (Rect) null, this.r2, (Paint) null);
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            boolean z = motionEvent.getX() > ((float) ((getWidth() - this.size) / 2)) && motionEvent.getX() < ((float) ((getWidth() + this.size) / 2));
            this.touch = z;
            if (z) {
                this.run = 0;
                this.handler.removeCallbacks(this.runnable);
            }
        } else if (action != 1) {
            if (action == 2 && this.touch) {
                int x = (int) (motionEvent.getX() - ((float) (getWidth() / 2)));
                this.c = x;
                if (x < (-(getWidth() - this.size)) / 2) {
                    this.c = (-(getWidth() - this.size)) / 2;
                } else if (this.c > (getWidth() - this.size) / 2) {
                    this.c = (getWidth() - this.size) / 2;
                }
            }
        } else if (this.c == (-(getWidth() - this.size)) / 2) {
            this.itfAddCall.onAddCall();
        } else if (this.c == (getWidth() - this.size) / 2) {
            this.itfAddCall.onCancel();
        } else {
            this.c = 0;
            this.touch = false;
            this.handler.post(this.runnable);
        }
        invalidate();
        return true;
    }
}

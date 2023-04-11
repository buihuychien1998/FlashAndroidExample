package com.demo.example.OS.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.example.R;

import java.io.IOException;


public class AvatarPeople extends RelativeLayout {
    private ImageView im;
    private TextView tv;

    public AvatarPeople(Context context) {
        super(context);
        init();
    }

    public AvatarPeople(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AvatarPeople(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.im = new ImageView(getContext());
        TextView textView = new TextView(getContext());
        this.tv = textView;
        textView.setBackgroundResource(R.drawable.bg_no_contact);
        this.tv.setGravity(17);
        this.tv.setTextColor(-1);
        this.tv.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/sf-ui-display-semibold-58646eddcae92.otf"));
        this.tv.setTextSize(2, 13.0f);
        addView(this.im, new LayoutParams(-1, -1));
        addView(this.tv, new LayoutParams(-1, -1));
    }

    public void setImage(int i) {
        this.im.setVisibility(View.VISIBLE);
        this.tv.setVisibility(GONE);
        this.im.setImageResource(i);
    }

    public void setImage(String str, String str2) {
        if (str != null && !str.isEmpty()) {
            this.im.setVisibility(View.VISIBLE);
            this.tv.setVisibility(GONE);
            try {
                this.im.setImageBitmap(getCroppedBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(str))));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (str2 == null || str2.isEmpty()) {
            str2 = "?";
        }
        this.im.setVisibility(GONE);
        this.tv.setVisibility(View.VISIBLE);
        String substring = str2.substring(0, 1);
        if (str2.contains(" ") && str2.indexOf(" ") < str2.length() - 1) {
            substring = substring + str2.substring(str2.indexOf(" ") + 1, str2.indexOf(" ") + 2);
        }
        this.tv.setText(substring.toUpperCase());
    }

    private Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2), (float) (bitmap.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public void setTextSize(float f) {
        this.tv.setTextSize(2, f);
    }
}

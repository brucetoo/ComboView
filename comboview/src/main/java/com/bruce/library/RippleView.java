package com.bruce.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Bruce too
 * On 2016/3/25
 * At 14:12
 */
public class RippleView extends View {

    private float centerX;
    private float centerY;
    private float radius;
    private float toRadius;
    private int color;
    private Paint paint;

    public void settingParams(float centerX,float centerY,float radius,int color){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.color = color;
        invalidate();
    }

    private RectF rectF;
    public void settingParams(RectF rectF, float radius, int color){
        this.rectF = rectF;
        this.radius = radius;
        this.color = color;
        invalidate();
    }

    public RippleView(Context context) {
        super(context);
        init();
    }

    private void init() {
//        setVisibility(INVISIBLE);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setColor(color);

        if(null != rectF) {
            canvas.drawArc(rectF, 0, 360, true, paint);
        }else {
            canvas.drawCircle(centerX, centerY, radius, paint);
        }
    }

}

package com.bruce.library;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by Bruce too
 * On 2016/3/28
 * At 11:14
 */
public abstract class LimitClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 400;
    private long mLastClickTime;

    public abstract void onLimitClick(View v);

    @Override
    public void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;
        onLimitClick(v);
    }
}

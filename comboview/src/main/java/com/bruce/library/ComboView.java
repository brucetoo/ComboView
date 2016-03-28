package com.bruce.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by Bruce too
 * On 2016/3/25
 * At 9:51
 */
public class ComboView extends RelativeLayout {

    private String fromText;
    private String toText;
    private
    @DrawableRes
    int fromIcon;
    private
    @DrawableRes
    int toIcon;
    private int fromCornerRadius;
    private int toCornerRadius;
    private int fromWidth;
    private int toWidth;
    private int fromHeight;
    private int toHeight;
    private int fromColor = Color.parseColor("#ff0099cc");
    private int toColor = Color.parseColor("#ff00719b");
    private int fromColorPressed = Color.parseColor("#ff0099cc");
    private int toColorPressed = Color.parseColor("#ff00719b");
    private int morphDuration = 300;
    private int circleDuration = 5000;
    private int rippleDuration = 300;
    private int padding = 1;
    private int textSize = 16;
    private int textColor = Color.parseColor("#ffffff");
    private int fromStrokeWidth = 1;
    private int toStrokeWidth = 1;
    private int fromStrokeColor = fromColor;
    private int toStrokeColor = toColor;
    private ComboClickListener comboClickListener;

    public ComboView(Context context) {
        super(context);
        init();
    }

    public ComboView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ComboView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ComboView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private MorphingButton morphingButton;
    private RippleView rippleView;
    private Paint strokePaint = new Paint();

    private void init() {
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
//        setPadding(0, 0, dip2px(getContext(), 10), dip2px(getContext(), 10));

        rippleView = new RippleView(getContext());
        LayoutParams rippleParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(rippleView, rippleParams);

        morphingButton = new MorphingButton(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        params.addRule(ALIGN_PARENT_BOTTOM,TRUE);
//        params.addRule(ALIGN_PARENT_RIGHT,TRUE);
        morphingButton.setPadding(0,0,0,0);
        params.addRule(CENTER_IN_PARENT,TRUE);
        addView(morphingButton, params);

        morphingButton.setOnClickListener(btnClick);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
    }

    private boolean isDrawProgress;

    private OnClickListener btnClick = new LimitClickListener() {
        @Override
        public void onLimitClick(View v) {
            if (!isDrawProgress) {
                comboClickListener.onNormalClick();
                morphToCircle(morphDuration, new MorphingAnimation.Listener() {
                    @Override
                    public void onAnimationEnd() {
                        isDrawProgress = true;
                        strokePaint.setColor(toStrokeColor);
                        strokePaint.setStrokeWidth(toStrokeWidth);
                        drawProgress();
                    }
                });
            } else {//redraw progress when still draw circle
                comboClickListener.onComboClick();
                startRipple();
                setCurrentProgress(-90);
                drawProgress();
            }
        }
    };

    private ObjectAnimator progressAnimator;

    private float currentProgress;

    public float getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }
    RectF rectF;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initRect();

        canvas.drawArc(rectF, -90, currentProgress, false, strokePaint);
    }

    private void initRect() {
        rectF = new RectF();
        rectF.left = morphingButton.getLeft() - padding;
        rectF.top = morphingButton.getTop() - padding;
        rectF.right = morphingButton.getRight() + padding;
        rectF.bottom = morphingButton.getBottom() + padding;
    }

    private void drawProgress() {

//        setPadding(padding, padding, padding, padding);
        if (progressAnimator == null) {
            progressAnimator = ObjectAnimator.ofFloat(this, "currentProgress", 0, 360);
            progressAnimator.setDuration(circleDuration);
            progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isDrawProgress = false;
                strokePaint.setColor(getResources().getColor(android.R.color.transparent));
                setCurrentProgress(0);
                morphToSquare(morphDuration);
            }
        });

        progressAnimator.start();
    }


    public void settingMorphParams(ComboView.Params params) {
        this.fromText = params.fromText;
        this.toText = params.toText;
        this.fromIcon = params.fromIcon;
        this.toIcon = params.toIcon;
        this.fromCornerRadius = params.fromCornerRadius;
        this.toCornerRadius = params.toCornerRadius;
        this.fromWidth = params.fromWidth;
        this.toWidth = params.toWidth;
        this.fromHeight = params.fromHeight;
        this.toHeight = params.toHeight;
        this.fromColor = params.fromColor;
        this.toColor = params.toColor;
        this.fromColorPressed = params.fromColorPressed;
        this.toColorPressed = params.toColorPressed;
        this.morphDuration = params.morphDuration;
        this.circleDuration = params.circleDuration;
        this.rippleDuration = params.rippleDuration;
        this.padding = params.padding;
        this.textColor = params.textColor;
        this.textSize = params.textSize;
        this.fromStrokeWidth = params.fromStrokeWidth;
        this.toStrokeWidth = params.toStrokeWidth;
        this.fromStrokeColor = params.fromStrokeColor;
        this.toStrokeColor = params.toStrokeColor;
        this.comboClickListener = params.comboClickListener;
        morphToSquare(0);//init duration=0  no animation
        morphingButton.setTextSize(textSize);
        morphingButton.setTextColor(textColor);
    }

    private void morphToSquare(int morphDuration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(morphDuration)
                .cornerRadius(fromCornerRadius)
                .width(fromWidth)
                .height(fromHeight)
                .color(fromColor)
                .colorPressed(fromColorPressed)
                .text(fromText);
        morphingButton.morph(square);
    }

    private void morphToCircle(int morphDuration, MorphingAnimation.Listener listener) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(morphDuration)
                .cornerRadius(toCornerRadius)
                .width(toWidth)
                .height(toHeight)
                .color(toColor)
                .colorPressed(toColorPressed)
                .animationListener(listener)
                .text(toText);
        morphingButton.morph(circle);
    }


    AnimatorSet animatorSet;

    private void startRipple() {

//        rippleView.settingParams(morphingButton.getLeft()+ toWidth / 2 - padding, morphingButton.getTop() + toWidth / 2 - padding
//                , toWidth / 2, toStrokeColor);
//        rippleView.settingParams(morphingButton.getLeft() + morphingButton.getWidth() / 2, morphingButton.getTop() + morphingButton.getHeight() / 2
//                , toWidth / 2, toStrokeColor);
        rippleView.settingParams(rectF,toWidth / 2,toStrokeColor);

        if (animatorSet == null) {
            animatorSet = new AnimatorSet();
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "scaleX", 1.0f, 4.0f);
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "scaleY", 1.0f, 4.0f);
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "alpha", 1, 0);

            animatorSet.setDuration(rippleDuration);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

            animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rippleView.setScaleX(1);
                rippleView.setScaleY(1);
                rippleView.setAlpha(1);
                rippleView.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                rippleView.setVisibility(VISIBLE);
            }
        });
        animatorSet.start();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static class Params {

        private String fromText;
        private String toText;
        private
        @DrawableRes
        int fromIcon;
        private
        @DrawableRes
        int toIcon;
        private int fromCornerRadius;
        private int toCornerRadius;
        private int fromWidth;
        private int toWidth;
        private int fromHeight;
        private int toHeight;
        private int fromColor;
        private int toColor;
        private int fromColorPressed;
        private int toColorPressed;
        private int morphDuration;
        private int circleDuration;
        private int rippleDuration;
        private int padding;
        private int textSize;
        private int textColor;
        private int fromStrokeWidth = 1;
        private int toStrokeWidth = 1;
        private int fromStrokeColor = fromColor;
        private int toStrokeColor = toColor;
        private ComboClickListener comboClickListener;

        private Params() {

        }

        public static Params create() {
            return new Params();
        }

        public Params text(@NonNull String fromText, String toText) {
            this.fromText = fromText;
            this.toText = toText;
            return this;
        }

        public Params textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Params textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Params padding(int padding) {
            this.padding = padding;
            return this;
        }

        public Params icon(@DrawableRes int fromIcon, @DrawableRes int toIcon) {
            this.fromIcon = fromIcon;
            this.toIcon = toIcon;
            return this;
        }

        public Params cornerRadius(int fromCornerRadius, int toCornerRadius) {
            this.fromCornerRadius = fromCornerRadius;
            this.toCornerRadius = toCornerRadius;
            return this;
        }

        public Params width(int fromWidth, int toWidth) {
            this.fromWidth = fromWidth;
            this.toWidth = toWidth;
            return this;
        }

        public Params height(int fromHeight, int toHeight) {
            this.fromHeight = fromHeight;
            this.toHeight = toHeight;
            return this;
        }

        public Params color(int fromColor, int toColor) {
            this.fromColor = fromColor;
            this.toColor = toColor;
            return this;
        }

        public Params colorPressed(int fromColorPressed, int toColorPressed) {
            this.fromColorPressed = fromColorPressed;
            this.toColorPressed = toColorPressed;
            return this;
        }

        public Params morphDuration(int morphDuration) {
            this.morphDuration = morphDuration;
            return this;
        }

        public Params circleDuration(int circleDuration){
            this.circleDuration = circleDuration;
            return this;
        }

        public Params rippleDuration(int rippleDuration){
            this.rippleDuration = rippleDuration;
            return this;
        }

        public Params strokeWidth(int fromStrokeWidth, int toStrokeWidth) {
            this.fromStrokeWidth = fromStrokeWidth;
            this.toStrokeWidth = toStrokeWidth;
            return this;
        }

        public Params strokeColor(int fromStrokeColor, int toStrokeColor) {
            this.fromStrokeColor = fromStrokeColor;
            this.toStrokeColor = toStrokeColor;
            return this;
        }

        public Params comboClickListener(ComboClickListener comboClickListener){
            this.comboClickListener = comboClickListener;
            return this;
        }

    }

    public interface ComboClickListener{

        void onComboClick();

        void onNormalClick();
    }
}

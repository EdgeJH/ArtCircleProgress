package com.edge.artcircle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by user1 on 2018-06-08.
 */

public class ArtCircleProgress extends View {
    private ValueAnimator mTimerAnimator;
    private Paint shadowPaint, progressPaint, progressBackPaint, textPaint;
    private RectF artRect;
    private int width;
    private int height;
    private boolean isAnimate;
    private int midPointX;
    private int midPointY;
    private float progressWidth;
    private int progressStartColor;
    private int progressEndColor;
    private int shadowColor;
    private int progress;
    private int maxProgress;
    private float shadowRadius;
    private int textColor;
    private float textSize;
    private Shader gradient;
    private int animateDuration;
    private float mCircleSweepAngle;
    private Path progressPath = new Path();
    private Path progressBackPath = new Path();
    private int circleBackGroundColor;
    private boolean isOutMode = false;
    private float ratio = 0;
    private int xPos, yPos;
    private boolean textShow;
    private int progressBackColor;

    public ArtCircleProgress(Context context) {
        super(context);
    }

    public ArtCircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArtCircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(widthMeasureSpec);
        midPointX = width / 2;
        midPointY = height / 2;
        setProgressPaint();
        if (textShow){
            setTextPaint();
            xPos = (width / 2);
            yPos = (int) ((height / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        }

        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!isOutMode) {
            canvas.drawCircle(midPointX, midPointY, midPointX - shadowRadius, shadowPaint);
            progressPath.reset();
            progressPath.arcTo(artRect, 135f, mCircleSweepAngle);
            progressBackPath.reset();
            progressBackPath.arcTo(artRect, 135f, 270f);

        } else {

            progressPath.reset();
            progressPath.arcTo(artRect, 135f, mCircleSweepAngle);
            progressBackPath.reset();
            progressBackPath.arcTo(artRect, 135f, 270f);
            canvas.drawCircle(midPointX, midPointY, midPointX - shadowRadius*2f - progressWidth, shadowPaint);
        }

        if (textShow){
            canvas.drawText((int) ((mCircleSweepAngle / 270f / ratio) * (float) progress) + "%", xPos, yPos, textPaint);
        }
        canvas.drawPath(progressBackPath, progressBackPaint);
        canvas.drawPath(progressPath, progressPaint);
        super.onDraw(canvas);
    }

    private void init(Context context, AttributeSet attr) {

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.ArtCircleProgress);
        shadowColor = typedArray.getColor(R.styleable.ArtCircleProgress_shadowColor, ContextCompat.getColor(context, R.color.white_gray));
        progressStartColor = typedArray.getColor(R.styleable.ArtCircleProgress_progressStartColor, ContextCompat.getColor(context, R.color.yellow));
        progressEndColor = typedArray.getColor(R.styleable.ArtCircleProgress_progressEndColor, ContextCompat.getColor(context, R.color.green));
        progressWidth = typedArray.getDimension(R.styleable.ArtCircleProgress_progressWidth, 15f);
        shadowRadius = typedArray.getDimension(R.styleable.ArtCircleProgress_shadowRadius, 20f);
        isAnimate = typedArray.getBoolean(R.styleable.ArtCircleProgress_animateLoading, true);
        progress = typedArray.getInteger(R.styleable.ArtCircleProgress_progress, 100);
        maxProgress = typedArray.getInteger(R.styleable.ArtCircleProgress_maxProgress, 100);
        animateDuration = typedArray.getInteger(R.styleable.ArtCircleProgress_animateDuration, 2000);
        isOutMode = typedArray.getBoolean(R.styleable.ArtCircleProgress_outMode, false);
        textColor = typedArray.getColor(R.styleable.ArtCircleProgress_textColor, ContextCompat.getColor(context, R.color.dark_gray));
        circleBackGroundColor =typedArray.getColor(R.styleable.ArtCircleProgress_circleBackgroundColor, Color.WHITE);
        progressBackColor = typedArray.getColor(R.styleable.ArtCircleProgress_progressBackColor,ContextCompat.getColor(context,R.color.white_gray));
        textSize = typedArray.getDimension(R.styleable.ArtCircleProgress_textSize, 50f);
        textShow = typedArray.getBoolean(R.styleable.ArtCircleProgress_textShow,true);

        ratio = (float) progress / (float) maxProgress;
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(circleBackGroundColor);
        shadowPaint.setShadowLayer(shadowRadius, 3f, 3f, shadowColor);
        setLayerType(LAYER_TYPE_SOFTWARE, shadowPaint);
        progressBackPaint = new Paint();
        progressBackPaint.setColor(progressBackColor);
        progressBackPaint.setStrokeCap(Paint.Cap.ROUND);
        progressBackPaint.setStyle(Paint.Style.STROKE);
        progressBackPaint.setStrokeWidth(progressWidth);

        if (isAnimate) {
            initAnimation();
        } else {
            mCircleSweepAngle = 270f * ratio;
        }
    }

    private void setTextPaint() {
        textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
    }

    private void setProgressPaint() {
        if (!isOutMode) {
            float yStartPoint = shadowRadius + midPointY* 0.2f;
            float xStartPoint = shadowRadius + midPointX * 0.2f;
            artRect = new RectF(xStartPoint, yStartPoint, width - xStartPoint, height - yStartPoint);
        } else {
            artRect = new RectF(shadowRadius*1.5f, shadowRadius*1.5f, width-shadowRadius*1.5f , height - shadowRadius*1.5f);
        }

        gradient = new SweepGradient(midPointX, midPointY, progressStartColor, progressEndColor);
        Matrix gradientMatrix = new Matrix();
        gradientMatrix.preRotate(90f, midPointX, midPointY);
        gradient.setLocalMatrix(gradientMatrix);
        progressPaint = new Paint();
        progressPaint.setShader(gradient);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(Color.BLACK);


    }

    private void initAnimation() {
        if (mTimerAnimator!=null&&mTimerAnimator.isRunning()){
            mTimerAnimator.cancel();
        }
        mTimerAnimator = ValueAnimator.ofFloat(0f, 1f);
        mTimerAnimator.setDuration(animateDuration);
        mTimerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mTimerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawProgress((float) animation.getAnimatedValue());
            }
        });
        mTimerAnimator.start();
    }

    private void drawProgress(float animateValue) {
        mCircleSweepAngle = 270f * ratio * animateValue;

        invalidate();
    }

    public void setProgress(int progress){
        this.progress =progress;
        invalidate();
    }
    public void setMaxProgress(int maxProgress){
        this.maxProgress = maxProgress;
        invalidate();
    }
}

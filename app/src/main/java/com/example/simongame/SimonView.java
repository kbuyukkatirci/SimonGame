package com.example.simongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SimonView extends View {

    private OnSimonClickListener onSimonClickListener;
    private Paint paint;
    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private boolean isFlashing = false;
    private int flashColor = -1;
    private int width, height;

    public SimonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int halfWidth = width / 2;
        int halfHeight = height / 2;

        if (flashColor == 0) {
            paint.setColor(Color.RED);
            canvas.drawRect(0, 0, halfWidth, halfHeight, paint);
        } else if (flashColor == 1) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(halfWidth, 0, width, halfHeight, paint);
        } else if (flashColor == 2) {
            paint.setColor(Color.BLUE);
            canvas.drawRect(0, halfHeight, halfWidth, height, paint);
        } else if (flashColor == 3) {
            paint.setColor(Color.YELLOW);
            canvas.drawRect(halfWidth, halfHeight, width, height, paint);
        } else {
            paint.setColor(colors[0]);
            canvas.drawRect(0, 0, halfWidth, halfHeight, paint);

            paint.setColor(colors[1]);
            canvas.drawRect(halfWidth, 0, width, halfHeight, paint);

            paint.setColor(colors[2]);
            canvas.drawRect(0, halfHeight, halfWidth, height, paint);

            paint.setColor(colors[3]);
            canvas.drawRect(halfWidth, halfHeight, width, height, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isFlashing) {
            return false;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int colorClicked = -1;


            if (x < width / 2 && y < height / 2) {
                colorClicked = 0; // Red
            } else if (x >= width / 2 && y < height / 2) {
                colorClicked = 1; // Green
            } else if (x < width / 2 && y >= height / 2) {
                colorClicked = 2; // Blue
            } else if (x >= width / 2 && y >= height / 2) {
                colorClicked = 3; // Yellow
            }

            if (onSimonClickListener != null && colorClicked != -1) {
                onSimonClickListener.onSimonClick(colorClicked);
            }
        }
        return true;
    }

    public void flashColor(final int color) {
        if (isFlashing) return;

        flashColor = color;
        isFlashing = true;
        invalidate();

        postDelayed(new Runnable() {
            @Override
            public void run() {
                flashColor = -1;
                isFlashing = false;
                invalidate();
            }
        }, 500);
    }

    public boolean isFlashing() {
        return isFlashing;
    }

    public void setOnSimonClickListener(OnSimonClickListener listener) {
        this.onSimonClickListener = listener;
    }

    public interface OnSimonClickListener {
        void onSimonClick(int color);
    }
}

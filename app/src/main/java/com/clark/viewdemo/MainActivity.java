package com.clark.viewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private TextView textView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.content).setOnTouchListener(this);
        textView = (TextView) findViewById(R.id.tv);
        assert textView != null;
        textView.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int top = textView.getTop();
                int left = textView.getLeft();
                int right = textView.getRight();
                int bottom = textView.getBottom();
                int width = right - left;
                int height = bottom - top;
                int x = (int) e1.getX();
                int y = (int) e1.getY();
//                x = (int) (x + distanceX);
//                y = (int) (y +distanceY);
                textView.scrollTo(x, y);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int top = textView.getTop();
        int left = textView.getLeft();
        int right = textView.getRight();
        int bottom = textView.getBottom();
        float translationX = textView.getTranslationX();
        float translationY = textView.getTranslationY();
        Log.i("haha", "getTop():" + top);
        Log.i("haha", "getLeft():" + left);
        Log.i("haha", "getRight():" + right);
        Log.i("haha", "getBottom():" + bottom);
        Log.i("haha", "getTranslationX():" + translationX);
        Log.i("haha", "getTranslationY():" + translationY);
    }

    float rawX = 0f;
    float rawY = 0f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.content:
                VelocityTracker velocityTracker = VelocityTracker.obtain();
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();
                Log.i("haha", "getXVelocity():" + xVelocity);
                Log.i("haha", "getYVelocity():" + yVelocity);
                break;
            case R.id.tv:
//                boolean b = mGestureDetector.onTouchEvent(event);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rawX = event.getRawX();
                        rawY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        int top = textView.getTop();
                        int left = textView.getLeft();
                        int right = textView.getRight();
                        int bottom = textView.getBottom();

                        float currentX = event.getRawX();
                        float currentY = event.getRawY();

                        int distanceX = (int) (currentX - rawX);
                        int distanceY = (int) (currentY - rawY);

                        top = top + distanceY;
                        left = left + distanceX;
                        right = right + distanceX;
                        bottom = bottom + distanceY;

                        textView.layout(left, top, right, bottom);
                        rawX = event.getRawX();
                        rawY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                break;
        }
        return true;
    }
}

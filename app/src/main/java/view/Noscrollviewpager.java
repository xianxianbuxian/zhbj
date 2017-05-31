package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jack on 2017/5/30.
 */

public class Noscrollviewpager extends ViewPager {
    public Noscrollviewpager(Context context) {
        super(context);
    }

    public Noscrollviewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //触摸时什么都不做 从而实现对滑动事件的禁用
        return true;
    }
//事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//不拦截子控件的事件
    }
}

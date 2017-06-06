package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jack on 2017/5/31.
 */

public class TopNewsviewpager extends ViewPager {

    private int startx;
    private int starty;

    public TopNewsviewpager(Context context) {
        super(context);
    }

    public TopNewsviewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 1上下滑动拦截
     * 2向右滑动并且是第一个页面的时候拦截
     * 3向左滑动的时候到最后一个页面的时候需要拦截
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startx = (int) ev.getX();
                starty = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                int endx= (int) ev.getX();
                int endy= (int) ev.getY();
                int dx=endx-startx;
                int dy=endy-starty;
                //判断绝对值
                if (Math.abs(dy)<Math.abs(dx)){
                    int currentItem = getCurrentItem();//当前的页面是那一个
                    //左右滑动
                    if(dx>0){
                        //向右滑动
                        if(currentItem==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else {
                        //向左滑动
                        int count = getAdapter().getCount();//item总数
                        if (count-1==currentItem){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else {
                    //上下滑动 需要拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

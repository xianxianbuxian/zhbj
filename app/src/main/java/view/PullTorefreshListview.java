package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import jack.example.com.zhbj.R;

/**
 * 下拉刷新的listview
 * Created by jack on 2017/6/1.
 */

public class PullTorefreshListview extends ListView implements AbsListView.OnScrollListener {

    public View mHeaderview;
    private int measuredHeight;
    private int startx;
    private int starty = -1;
    private int dey;
    private static final int STATE_PULL_TO_REFRSH = 1;
    private static final int STATE_RELEASE_TO_REFRSH = 2;
    private static final int STATE_REFRSHING = 3;
    private int mCurrentState = STATE_PULL_TO_REFRSH;
    private TextView tv_title;
    private TextView tv_time;
    private ImageView iv_arrow;
    private RotateAnimation animup;
    private RotateAnimation animdowm;
    private ProgressBar pb;
    private int mFootmesasureH;
    private View footview;

    public PullTorefreshListview(Context context) {
        super(context);
        initheaderview();
        inintfootview();
    }

    public PullTorefreshListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initheaderview();
        inintfootview();
    }

    public PullTorefreshListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initheaderview();
        inintfootview();
    }

    /**
     * 初始化头布局
     */
    public void initheaderview() {
        mHeaderview = View.inflate(getContext(), R.layout.pull_to_refalsh, null);
        this.addHeaderView(mHeaderview);
        tv_title = (TextView) mHeaderview.findViewById(R.id.tv_list_pull_refalsh);
        tv_time = (TextView) mHeaderview.findViewById(R.id.tv_list_pull_time);
        iv_arrow = (ImageView) mHeaderview.findViewById(R.id.iv_arrow);
        pb = (ProgressBar) mHeaderview.findViewById(R.id.pb_loding);
        //隐藏头布局 先测量才能得到
        mHeaderview.measure(0, 0);
        measuredHeight = mHeaderview.getMeasuredHeight();
        mHeaderview.setPadding(0, -measuredHeight, 0, 0);
        initanim();
        setcurrentTime();

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                starty = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //防止第二个头布局的viewpage消费了触摸事件
                if (starty == -1) {
                    starty = (int) ev.getY();
                }
                if (mCurrentState == STATE_REFRSHING) {
                    break;
                }
                int movey = (int) ev.getY();
                dey = movey - starty;
                //下拉条件
                if (dey > 0 && getFirstVisiblePosition() == 0) {
                    int pdingtop = dey - measuredHeight; //pading隐藏距离
                    mHeaderview.setPadding(0, pdingtop, 0, 0);
                    if (pdingtop > 0 && mCurrentState != STATE_RELEASE_TO_REFRSH) {
                        mCurrentState = STATE_RELEASE_TO_REFRSH;
                        refreshstate();
                    } else if (pdingtop < 0 && mCurrentState != STATE_PULL_TO_REFRSH) {
                        mCurrentState = STATE_PULL_TO_REFRSH;
                        refreshstate();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                starty = -1;
                if (mCurrentState == STATE_RELEASE_TO_REFRSH) {
                    mCurrentState = STATE_REFRSHING;
                    refreshstate();
                    //完整展示头布局
                    mHeaderview.setPadding(0, 0, 0, 0);
                    if (mLinser != null) {
                        mLinser.refreshfinsh();
                    }

                } else if (mCurrentState == STATE_PULL_TO_REFRSH) {
                    mHeaderview.setPadding(0, -measuredHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void datagoback() {
        if(!islodingmore){
            mCurrentState = STATE_PULL_TO_REFRSH;
            mHeaderview.setPadding(0, -measuredHeight, 0, 0);
            tv_title.setText("下拉刷新");
            iv_arrow.clearAnimation();//清除动画不然隐藏不了
            pb.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
            setcurrentTime();
        }else {
            footview.setPadding(0, 0, 0, -mFootmesasureH);
            islodingmore=false;
        }


    }

    private void refreshstate() {
        switch (mCurrentState) {
            case STATE_PULL_TO_REFRSH:
                tv_title.setText("下拉刷新");
                pb.setVisibility(View.INVISIBLE);
                iv_arrow.setVisibility(View.VISIBLE);
                iv_arrow.startAnimation(animdowm);
                break;
            case STATE_RELEASE_TO_REFRSH:
                tv_title.setText("松开刷新");
                pb.setVisibility(View.INVISIBLE);
                iv_arrow.setVisibility(View.VISIBLE);
                iv_arrow.startAnimation(animup);
                break;
            case STATE_REFRSHING:
                tv_title.setText("正在刷新...");
                iv_arrow.clearAnimation();//清除动画不然隐藏不了
                pb.setVisibility(View.VISIBLE);
                iv_arrow.setVisibility(View.INVISIBLE);
                break;
        }

    }

    /**
     * 初始化动画
     */
    private void initanim() {
        animup = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animup.setDuration(200);
        animup.setFillAfter(true);
        animdowm = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animup.setDuration(200);
        animup.setFillAfter(true);
    }

    private onpullrefreshdata mLinser;

    public void setonpullrefreshdata(onpullrefreshdata linsner) {
        mLinser = linsner;
    }


    public interface onpullrefreshdata {
        void refreshfinsh();
        void onlodingmore();
    }

    public void setcurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String time = format.format(new Date());
        tv_time.setText(time);
    }

    public void inintfootview() {
        footview = View.inflate(getContext(), R.layout.foot_view, null);
        this.addFooterView(footview);
        footview.measure(0, 0);
        mFootmesasureH = footview.getMeasuredHeight();
        footview.setPadding(0, 0, 0, -mFootmesasureH);
        setOnScrollListener(this);
    }
          private boolean islodingmore;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
                   if (getLastVisiblePosition()==getCount()-1&& scrollState==SCROLL_STATE_IDLE&&!islodingmore) {
                           footview.setPadding(0, 0, 0, 0);
                       setSelection(getCount()-1);//将listview显示在最后一个item上 从而展示加载更多 不用滑动
                       islodingmore=true;
                       mLinser.onlodingmore();

                   }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
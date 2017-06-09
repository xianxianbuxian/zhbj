package base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import jack.example.com.zhbj.MainActivity;
import jack.example.com.zhbj.R;

/**
 * 五个标签页的基类
 * Created by jack on 2017/5/30.
 */

public class Basepager {
    public Activity mActivity;

    public ImageButton ibMenu;

    public TextView tvTitle;

    public FrameLayout flBase;
    public final View mRootview;//当前页面的布局对象
    public ImageButton btnphoto;

    //构造方法
    public Basepager(Activity activity) {
        mActivity = activity;
        mRootview = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.basepage, null);
        ibMenu = (ImageButton) view.findViewById(R.id.ib_menu);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        flBase = (FrameLayout) view.findViewById(R.id.fl_base);
        btnphoto = (ImageButton) view.findViewById(R.id.btn_photo);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }

    private void toggle() {
        MainActivity mainui = (MainActivity) mActivity;
        SlidingMenu menu = mainui.getSlidingMenu();
        menu.toggle();//如果开关 ，如果关就开
    }

    public void initData() {

    }
}
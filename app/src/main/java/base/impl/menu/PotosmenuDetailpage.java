package base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import base.BaseMenuDetailPager;

/**
 * 图片 详情页
 * Created by jack on 2017/5/30.
 */

public class PotosmenuDetailpage extends BaseMenuDetailPager {
    public PotosmenuDetailpage(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView view=new TextView(mActivity);
        view.setText("组图详情");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        return view;
    }
}

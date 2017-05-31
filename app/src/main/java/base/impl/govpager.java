package base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import base.Basepager;

/**
 * 首页
 * Created by jack on 2017/5/30.
 */

public class govpager extends Basepager {
    public govpager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
       //给真布局填充布局
        TextView view=new TextView(mActivity);
        view.setText("政务");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        flBase.addView(view);
        tvTitle.setText("人口管理");
        ibMenu.setVisibility(View.VISIBLE);
    }
}

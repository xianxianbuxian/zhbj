package base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import base.BaseMenuDetailPager;
import domain.NewsMenu;

/**
 * Created by jack on 2017/5/31.
 */

public class tabdetailpager extends BaseMenuDetailPager{
       private NewsMenu.NewstabData mNewstabData;
    private TextView view;

    public tabdetailpager(Activity activity, NewsMenu.NewstabData newstabData) {
        super(activity);
        mNewstabData=newstabData;
    }

    @Override
    public View initView() {
        view = new TextView(mActivity);
     //   view.setText(mNewstabData.title); 此处空指针 父类先调用initView
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void initData() {
        view.setText(mNewstabData.title);
    }
}

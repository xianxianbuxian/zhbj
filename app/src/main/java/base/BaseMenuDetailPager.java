package base;

import android.app.Activity;
import android.view.View;

/**
 * 菜单详情页
 * Created by jack on 2017/5/30.
 */

public abstract class BaseMenuDetailPager {
    public  Activity mActivity;
    public View mRootview;//菜单详情页根布局
    public BaseMenuDetailPager(Activity activity){
        mActivity=activity;
       mRootview= initView();
    }
    public void initData(){

    }
    public abstract View initView();
}

package base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import base.BaseMenuDetailPager;
import base.Basepager;
import base.impl.menu.IntermenuDetailpage;
import base.impl.menu.NewsmenuDetailpage;
import base.impl.menu.PotosmenuDetailpage;
import base.impl.menu.TopicmenuDetailpage;
import domain.NewsMenu;
import fragment.Leftmenufragment;
import global.globalconstant;
import jack.example.com.zhbj.MainActivity;
import uitl.cacheuitls;

/**
 * 首页
 * Created by jack on 2017/5/30.
 */

public class newspager extends Basepager {

    private NewsMenu mNewsData;

    public newspager(Activity activity) {
        super(activity);
    }
 private ArrayList<BaseMenuDetailPager> mMenuDetailPager;
    @Override
    public void initData() {
        //给真布局填充布局

        tvTitle.setText("新闻中心");
        ibMenu.setVisibility(View.VISIBLE);
        //判断缓存是否存在
        String cache = cacheuitls.getcache(globalconstant.CATEGORY_URL, mActivity);

        if (!TextUtils.isEmpty(cache)) {
            Log.e("++", "发现缓存");
            processDate(cache);
        } else {
            getdataformserver();
        }
        //请求服务器
        getdataformserver();

    }
 public  void setCurrenDetailPager(int position){
     BaseMenuDetailPager pager = mMenuDetailPager.get(position);//当前应该加载的页面 position得知
     View view = pager.mRootview;//当前布局
     //清除之前的布局
     flBase.removeAllViews();
     flBase.addView(view);//给真布局添加布局
     //初始化数据
     pager.initData();
     tvTitle.setText(mNewsData.data.get(position).title);
     //如果是图组页面，需要切换显示按钮
     if(pager instanceof PotosmenuDetailpage){
         btnphoto.setVisibility(View.VISIBLE);
     } else {
         //隐藏按钮
         btnphoto.setVisibility(View.GONE);
     }

 }
    private void processDate(String json) {
        Gson gson = new Gson();
        mNewsData = gson.fromJson(json, NewsMenu.class);
        Log.e("+++", "" + mNewsData);
        //获取侧边栏的对象
        MainActivity mainUi = (MainActivity) mActivity;
        Leftmenufragment leftfragment = mainUi.getleftMenuFragment();
        leftfragment.setMenuData(mNewsData.data);
         //初始化4个菜单详情页
        mMenuDetailPager=new ArrayList<>();
        mMenuDetailPager.add(new NewsmenuDetailpage(mActivity,mNewsData.data.get(0).children));
        mMenuDetailPager.add(new TopicmenuDetailpage(mActivity));
        mMenuDetailPager.add(new PotosmenuDetailpage(mActivity,btnphoto));
        mMenuDetailPager.add(new IntermenuDetailpage(mActivity));
       setCurrenDetailPager(0);//将新闻详情设置为详情页
    }

    public void getdataformserver() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, globalconstant.CATEGORY_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("++", result);
                processDate(result);
                cacheuitls.setcache(globalconstant.CATEGORY_URL, result, mActivity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
}

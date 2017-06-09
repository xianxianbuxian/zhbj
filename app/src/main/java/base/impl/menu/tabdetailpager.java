package base.impl.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import base.BaseMenuDetailPager;
import domain.NewsMenu;
import domain.newstabbean;
import global.globalconstant;
import jack.example.com.zhbj.R;
import jack.example.com.zhbj.WebActivity;
import uitl.SPerfUitls;
import uitl.cacheuitls;
import view.PullTorefreshListview;
import view.TopNewsviewpager;

/**
 * Created by jack on 2017/5/31.
 */

public class tabdetailpager extends BaseMenuDetailPager {

    TopNewsviewpager topNewDetail;

    PullTorefreshListview lvNewsList;
    private NewsMenu.NewstabData mNewstabData;
    private final String mUrl;
    private String result;
    private ArrayList<newstabbean.TopNews> mTopNews;
    private TextView mTvtitle;
    private CirclePageIndicator idc_point;
    private ArrayList<newstabbean.NewsData> newsDatas;
    private newslistadapter mNewsAdapter;
    private String mMroreurl;
     private Handler mHander;

    public tabdetailpager(Activity activity, NewsMenu.NewstabData newstabData) {
        super(activity);
        mNewstabData = newstabData;
        mUrl = globalconstant.SERVER_URL + mNewstabData.url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        lvNewsList = (PullTorefreshListview) view.findViewById(R.id.lv_news_list);
        View headvie = View.inflate(mActivity, R.layout.list_header, null);
        topNewDetail = (TopNewsviewpager) headvie.findViewById(R.id.top_new_detail);
        mTvtitle = (TextView) headvie.findViewById(R.id.tv_titlea);
        idc_point = (CirclePageIndicator) headvie.findViewById(R.id.idc_point);
        //给listview添加头布局
        lvNewsList.addHeaderView(headvie);
        lvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int   position1=position-lvNewsList.getHeaderViewsCount();
                newstabbean.NewsData news = newsDatas.get(position1);
                //read_ids:
                String read_ids = SPerfUitls.getString(mActivity, "read_ids", "");
                if (!read_ids.contains(news.id+"")){
                    read_ids=read_ids+news.id+",";
                    SPerfUitls.setstring(mActivity,"read_ids",read_ids);
                }
                 TextView title= (TextView) view.findViewById(R.id.tv_list_title);
                title.setTextColor(Color.GRAY);
                Intent intent=new Intent(mActivity.getApplicationContext(), WebActivity.class);
                intent.putExtra("url",news.url);
                mActivity.startActivity(intent);
            }
        });
        return view;
    }

    public void getmoredata() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mMroreurl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result1 = responseInfo.result;
                Log.e("--", result);
                processData(result1, true);
                lvNewsList.datagoback();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity, "刷新失败", Toast.LENGTH_SHORT).show();
                lvNewsList.datagoback();
            }
        });

    }

    class newslistadapter extends BaseAdapter {

        private final BitmapUtils bitmapUtils;

        public newslistadapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.news_pic_default);
        }

        @Override
        public int getCount() {
            return newsDatas.size();
        }

        @Override
        public newstabbean.NewsData getItem(int position) {
            return newsDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.list_item_news, null);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_list_title);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_list_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            newstabbean.NewsData item = getItem(position);
            holder.tv_title.setText(item.title);
            holder.tv_time.setText(item.pubdate);
            String read_ids = SPerfUitls.getString(mActivity, "read_ids", "");
            //根据标记来修改标题颜色
            if (read_ids.contains(item.id+",")){
                holder.tv_title.setTextColor(Color.GRAY);
            }else {
                holder.tv_title.setTextColor(Color.BLACK);
            }
            bitmapUtils.display(holder.iv_icon, item.listimage);
            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    @Override
    public void initData() {
        String getcache = cacheuitls.getcache(mUrl, mActivity);
        if (!TextUtils.isEmpty(getcache)) {
            processData(getcache, false);
        }
        getDatafromServer();

    }

    private void getDatafromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                result = responseInfo.result;
                Log.e("--", result);
                processData(result, false);
                cacheuitls.setcache(mUrl, result, mActivity);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);

                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lvNewsList.datagoback();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity, "刷新失败", Toast.LENGTH_SHORT).show();
                lvNewsList.datagoback();

            }
        });
    }

    private void processData(String result, boolean isMore) {
        Gson gson = new Gson();
        newstabbean data = gson.fromJson(result, newstabbean.class);
        mTopNews = data.data.topnews;
        String moreurl = data.data.more;
        if (!TextUtils.isEmpty(moreurl)) {
            mMroreurl = globalconstant.SERVER_URL + moreurl;
        } else {
            mMroreurl = null;
        }
        if (!isMore) {
            topNewDetail.setAdapter(new topnewadapter());
            idc_point.setViewPager(topNewDetail);
            idc_point.setSnap(true);//快照方式展示
            //事件要设给indecator
            idc_point.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //设置标题
                    mTvtitle.setText(mTopNews.get(position).title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //手动设置第一个标题
            mTvtitle.setText(mTopNews.get(0).title);
            //默认让他选中第一个（解决页面销毁后重新初始化时，indecator任然保留上次位置的问题）
            idc_point.onPageSelected(0);
            //列表新闻
            newsDatas = data.data.news;
            if (newsDatas != null) {
                mNewsAdapter = new newslistadapter();
                lvNewsList.setAdapter(mNewsAdapter);
            }
            if(mHander==null){
                mHander=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        int currentItem = topNewDetail.getCurrentItem();
                             currentItem++;
                        if(currentItem>mTopNews.size()-1){
                            currentItem=0;//到最后一个页面跳回第一个
                        }
                        topNewDetail.setCurrentItem(currentItem);
                        mHander.sendEmptyMessageDelayed(0,3000);
                    }
                };
                mHander.sendEmptyMessageDelayed(0,3000);
                topNewDetail.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                mHander.removeCallbacksAndMessages(null);
                                break;
                            case MotionEvent.ACTION_CANCEL://取消世间。当按下viewpage后，直接滑动listview导致抬起事件无相应
                                mHander.sendEmptyMessageDelayed(0,3000);
                                break;
                            case MotionEvent.ACTION_UP:
                                mHander.sendEmptyMessageDelayed(0,3000);
                                break;
                        }
                        return false;
                    }
                });
            }
            lvNewsList.setonpullrefreshdata(new PullTorefreshListview.onpullrefreshdata() {
                @Override
                public void refreshfinsh() {
                    getDatafromServer();

                }

                @Override
                public void onlodingmore() {
                    if (mMroreurl != null) {
                        getmoredata();
                    } else {
                        Toast.makeText(mActivity, "没有更多数据", Toast.LENGTH_SHORT).show();
                        lvNewsList.datagoback();
                    }
                }
            });
        } else {
            ArrayList<newstabbean.NewsData> moreNews = data.data.news;
            newsDatas.addAll(moreNews);
            mNewsAdapter.notifyDataSetChanged();
        }

    }

    class topnewadapter extends PagerAdapter {

        private final BitmapUtils mBitmapUtils;

        public topnewadapter() {
            mBitmapUtils = new BitmapUtils(mActivity);
            //设置默认加载的图片
            mBitmapUtils.configDefaultLoadFailedImage(R.drawable.topnews_item_default);

        }

        @Override
        public int getCount() {
            return mTopNews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setBackgroundResource(R.drawable.topnews_item_default);
            view.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片缩放方式，宽高填充父控件
            String topimageurl = mTopNews.get(position).topimage;
            //下载图片-将图片设置给imageview-要避免内存溢出-使用缓存
            mBitmapUtils.display(view, topimageurl);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

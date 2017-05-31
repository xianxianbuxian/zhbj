package base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import base.BaseMenuDetailPager;
import butterknife.BindView;
import domain.NewsMenu;
import domain.newstabbean;
import global.globalconstant;
import jack.example.com.zhbj.R;

/**
 * Created by jack on 2017/5/31.
 */

public class tabdetailpager extends BaseMenuDetailPager {
    @BindView(R.id.top_new_detail)
    ViewPager topNewDetail;
    @BindView(R.id.lv_news_list)
    ListView lvNewsList;
    private NewsMenu.NewstabData mNewstabData;
    private final String mUrl;
    private String result;


    public tabdetailpager(Activity activity, NewsMenu.NewstabData newstabData) {
        super(activity);
        mNewstabData = newstabData;
        mUrl = globalconstant.SERVER_URL+mNewstabData.url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);

        return view;
    }

    @Override
    public void initData() {
 getDatafromServer();
    }

    private void getDatafromServer() {
        HttpUtils utils=new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                result = responseInfo.result;
                Log.e("--",result);
                processData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void processData(String result) {
        Gson gson=new Gson();
        newstabbean data = gson.fromJson(result, newstabbean.class);


    }

    class topnewadapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object==view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

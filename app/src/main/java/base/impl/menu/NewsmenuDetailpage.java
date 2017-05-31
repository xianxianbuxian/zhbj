package base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import base.BaseMenuDetailPager;
import butterknife.BindView;
import domain.NewsMenu;
import jack.example.com.zhbj.MainActivity;
import jack.example.com.zhbj.R;

/**
 * 新闻 详情页
 * Created by jack on 2017/5/30.
 */

public class NewsmenuDetailpage extends BaseMenuDetailPager {

    ImageButton ibIndicatorRight;
    private ArrayList<NewsMenu.NewstabData> mNewstabDatas;
    private ArrayList<tabdetailpager> mTabdetailpager;
    @BindView(R.id.vp_newsmenu_detail)
    ViewPager vpNewsmenuDetail;
    private TabPageIndicator indicator;

    public NewsmenuDetailpage(Activity activity, ArrayList<NewsMenu.NewstabData> children) {
        super(activity);
        mNewstabDatas = children;
    }

    @Override
    public void initData() {
        mTabdetailpager = new ArrayList<>();
        for (int i = 0; i < mNewstabDatas.size(); i++) {
            tabdetailpager pager = new tabdetailpager(mActivity, mNewstabDatas.get(i));
            mTabdetailpager.add(pager);
        }
        vpNewsmenuDetail.setAdapter(new newsmenuadapter());
        indicator.setViewPager(vpNewsmenuDetail);//将指示器和viewpager绑定在一起 注意必须在viewpager设置数据之后绑定
        //必须给indicator注册 不能给viewpager
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setSlidingMenuenable(true);
                } else {
                    setSlidingMenuenable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ibIndicatorRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = vpNewsmenuDetail.getCurrentItem();
                item++;
                vpNewsmenuDetail.setCurrentItem(item);
            }
        });
    }

    protected void setSlidingMenuenable(boolean enable) {
        //获取侧边栏对象
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.newsmenudetail, null);
        vpNewsmenuDetail = (ViewPager) view.findViewById(R.id.vp_newsmenu_detail);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        ibIndicatorRight= (ImageButton) view.findViewById(R.id.ib_indicator_right);
        return view;
    }

//    @OnClick(R.id.ib_indicator_right)
//    public void onViewClicked() {
//        int item = vpNewsmenuDetail.getCurrentItem();
//        item++;
//        vpNewsmenuDetail.setCurrentItem(item);
//    }

    class newsmenuadapter extends PagerAdapter {
        //指定指示器的标题
        @Override
        public CharSequence getPageTitle(int position) {
            NewsMenu.NewstabData data = mNewstabDatas.get(position);

            return data.title;
        }

        @Override
        public int getCount() {
            return mTabdetailpager.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            tabdetailpager pager = mTabdetailpager.get(position);
            View view = pager.mRootview;
            container.addView(pager.mRootview);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

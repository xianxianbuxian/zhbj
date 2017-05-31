package fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import base.Basepager;
import base.impl.govpager;
import base.impl.homepager;
import base.impl.newspager;
import base.impl.settingpager;
import base.impl.stamrtpager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jack.example.com.zhbj.MainActivity;
import jack.example.com.zhbj.R;

/**
 * Created by jack on 2017/5/29.
 */

public class Contentfragment extends BaseFragment {
    @BindView(R.id.vp_fgm_content)
    ViewPager vpFgmContent;
    Unbinder unbinder;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    private ArrayList<Basepager> mPager;

    @Override
    protected void initDate() {
        mPager = new ArrayList<>();
        mPager.add(new homepager(mActivity));
        mPager.add(new newspager(mActivity));
        mPager.add(new stamrtpager(mActivity));
        mPager.add(new govpager(mActivity));
        mPager.add(new settingpager(mActivity));
        vpFgmContent.setAdapter(new Contentadapter());
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //低栏监听
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        //参2 平滑动画
                        vpFgmContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        vpFgmContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_gov:
                        vpFgmContent.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        vpFgmContent.setCurrentItem(4, false);
                        break;
                    case R.id.rb_starmt:
                        vpFgmContent.setCurrentItem(2, false);
                        break;
                }
            }
        });
        vpFgmContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Basepager pager = mPager.get(position);
                pager.initData();
                if (position == 0 || position == mPager.size() - 1) {
                       //侧边栏的关闭
                    setSlidingMenuenable(false);
                }else {
                    //侧边栏的开启
                    setSlidingMenuenable(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.get(0).initData();//手动加载第一页数据
        setSlidingMenuenable(false);
    }

    protected void setSlidingMenuenable(boolean enable) {
        //获取侧边栏对象
            MainActivity mainUi= (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        if(enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content_menu, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class Contentadapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPager.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Basepager pager = mPager.get(position);
            // pager.initData();//初始化数据viewpager 会默认加载下一个页面 防止加载浪费
            View view = pager.mRootview;
            container.addView(view);//添加布局
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    //获取新闻中心页面
    public  newspager getnewspager(){
        newspager newsp = (newspager) mPager.get(1);
         return newsp;
    }
}

package jack.example.com.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uitl.DensityUtils;
import uitl.SPerfUitls;

/**
 * Created by jack on 2017/5/29.
 */

public class GuiderAcitity extends Activity {
    @BindView(R.id.vp_guider)
    ViewPager vpGuider;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.ll_guider)
    LinearLayout llGuider;
    @BindView(R.id.iv_redpoint)
    ImageView ivRedpoint;
    private int[] imageId;
    private ArrayList<ImageView> mImageViews;
    private int mPointdis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guider_atcivity);
        ButterKnife.bind(this);
        initData();
        vpGuider.setAdapter(new Guiderapapter());

        vpGuider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                int des = (int) (mPointdis * positionOffset + mPointdis * position);
                params.leftMargin = des;
                ivRedpoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViews.size() - 1) {
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //监听layout方法结束 在oncreat结束后采取 测量 放置 绘画
        ivRedpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //防止多次调用 移除
                ivRedpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //两个点之间的距离
                mPointdis = llGuider.getChildAt(1).getLeft() - llGuider.getChildAt(0).getLeft();
            }
        });

    }

    private void initData() {
        imageId = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        mImageViews = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            ImageView guiderimage = new ImageView(this);
            guiderimage.setBackgroundResource(imageId[i]);
            mImageViews.add(guiderimage);
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.shape_point);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                    WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                layoutParams.leftMargin = DensityUtils.dip2px(30,this);
            }
            point.setLayoutParams(layoutParams);
            llGuider.addView(point);
        }
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked(View view) {
        SPerfUitls.setboolean(getApplicationContext(),"is_firstEnter",false);
       startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    class Guiderapapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

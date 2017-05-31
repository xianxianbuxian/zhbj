package jack.example.com.zhbj;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.Contentfragment;
import fragment.Leftmenufragment;


/**
 * Created by jack on 2017/5/29.
 */

public class MainActivity extends SlidingFragmentActivity {
    @BindView(R.id.fl_main)
    FrameLayout flMain;
    private static final String TAG_CONTENT_FRAGMENT = "TAG_CONTENT_FRAGMENT";
    private static final String TAG_LEFT_FRAGMENT = "TAG_LEFT_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        //设置侧边栏
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置全屏模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置侧边栏宽度
        slidingMenu.setBehindOffset(800);
        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_main, new Contentfragment(), TAG_CONTENT_FRAGMENT);
        transaction.replace(R.id.fl_left, new Leftmenufragment(), TAG_LEFT_FRAGMENT);
        //manager.findFragmentByTag()
        transaction.commit();
    }

    //获取侧边栏fragment对象
    public Leftmenufragment getleftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Leftmenufragment leftfm = (Leftmenufragment) fm.findFragmentByTag(TAG_LEFT_FRAGMENT);
        return leftfm;
    }

    public Contentfragment getcontentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Contentfragment leftfm = (Contentfragment) fm.findFragmentByTag(TAG_CONTENT_FRAGMENT);
        return leftfm;
    }
}

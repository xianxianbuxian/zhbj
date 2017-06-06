package jack.example.com.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import uitl.SPerfUitls;

public class splshactivity extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.rl_splash)
    RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splshactivity);
       ShareSDK.initSDK(this);
        ButterKnife.bind(this);
        //旋转动画
        RotateAnimation ra=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(1000);
        ra.setFillAfter(true);
        //缩放动画
        ScaleAnimation  sa=new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
       sa.setDuration(1000);
        sa.setFillAfter(true);
        //渐变动画
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(2000);
        aa.setFillAfter(true);
        AnimationSet aniset=new AnimationSet(true);
        aniset.addAnimation(ra);
        aniset.addAnimation(aa);
        aniset.addAnimation(sa);
        rlSplash.startAnimation(aniset);
        aniset.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean is_firstEnter = SPerfUitls.getboolean(splshactivity.this, "is_firstEnter", true);
                if(is_firstEnter){
                    startActivity(new Intent(getApplicationContext(),GuiderAcitity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                //闪屏splsh页面退出栈
                  finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}

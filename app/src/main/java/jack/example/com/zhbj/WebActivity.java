package jack.example.com.zhbj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jack.example.com.sharesdk.cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by jack on 2017/6/2.
 */

public class WebActivity extends Activity {
    @BindView(R.id.ib_menu)
    ImageButton ibMenu;
    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_share)
    ImageButton ibShare;
    @BindView(R.id.iv_textsize)
    ImageButton ivTextsize;
    @BindView(R.id.ll_share_ts)
    LinearLayout llShareTs;
    @BindView(R.id.wv_news_detail)
    WebView wvNewsDetail;
    @BindView(R.id.pb_web_loding)
    ProgressBar pbWebLoding;
    private WebSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activiyt);
        ButterKnife.bind(this);

        llShareTs.setVisibility(View.VISIBLE);
        ibBack.setVisibility(View.VISIBLE);
        ibMenu.setVisibility(View.GONE);
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        wvNewsDetail.loadUrl(url);
        settings = wvNewsDetail.getSettings();
        settings.setBuiltInZoomControls(true);//显示缩放按钮
        // wvNewsDetail.setInitialScale(1024*768);
        settings.setUseWideViewPort(true);//支持双击缩放
        settings.setJavaScriptEnabled(true);//支持打开JS功能
        wvNewsDetail.setWebViewClient(new WebViewClient() {
            //网页加载开始
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbWebLoding.setVisibility(View.VISIBLE);
            }

            //网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbWebLoding.setVisibility(View.INVISIBLE);
            }

            //所有链接跳转走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);//在页面跳转页面时 强制在webview里加载
                return true;
            }
        });
//        wvNewsDetail.goBack();跳到上个页面
//        wvNewsDetail.goForward();跳到下个页面
        wvNewsDetail.setWebChromeClient(new WebChromeClient(){
            //进度发生改变 newProgress进度变化值
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
               // pbWebLoding.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //网页标题
                super.onReceivedTitle(view, title);
            }
        });
    }

    @OnClick({R.id.ib_menu, R.id.ib_back, R.id.tv_title, R.id.ib_share, R.id.iv_textsize, R.id.ll_share_ts})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ib_back:
                finish();
                break;

            case R.id.ib_share:
                showShare();
                break;
            case R.id.iv_textsize:
                shouwchoosedialog();
                break;

        }
    }
 private int mTempwich;// 记录临时被选择的字体的大小（点击确定之前）
    private int mCurrenwith=2;//最终选择的位置
    /**
     * 选择字体大小弹框
     */
    private void shouwchoosedialog() {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("字体设置");
        String[] item=new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
        alertdialog.setSingleChoiceItems(item,mCurrenwith,new  DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                 mTempwich=which;
            }
        });
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    switch (mTempwich){
                        case 0:
                           settings.setTextSize(WebSettings.TextSize.LARGEST);
                           // settings.setTextZoom();
                            break;
                        case 1:
                            settings.setTextSize(WebSettings.TextSize.LARGER);
                            break;
                        case 2:
                            settings.setTextSize(WebSettings.TextSize.NORMAL);
                            break;
                        case 3:
                            settings.setTextSize(WebSettings.TextSize.SMALLER);
                            break;
                        case 4:
                            settings.setTextSize(WebSettings.TextSize.SMALLEST);
                            break;
                    }
                    mCurrenwith=mTempwich;
            }
        });
        alertdialog.setNegativeButton("取消",null);

        alertdialog.show();
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
     //oks.setTheme(OnekeyShareTheme.);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }
}

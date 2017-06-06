package uitl;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 自定义三级缓存图片加载工具
 * Created by jack on 2017/6/6.
 */

public class MybitmapUitls {

    private final NetCacheUtils mNetCacheUtils;
      private LocalCahce mLocalCache;
    private MemoryCache mMemorycache;
    public  MybitmapUitls(){
        mMemorycache=new MemoryCache();
        mLocalCache=new LocalCahce();
        mNetCacheUtils = new NetCacheUtils( mLocalCache,mMemorycache);
    }
    public void display(ImageView imageView,String url){
        //设置默认图片
      //  imageView.setBackgroundResource();
//        1. 内存缓存 速度快, 优先读取
//        2. 本地缓存 速度其次, 内存没有,读本地
//        3. 网络缓存 速度最慢, 本地也没有,才访问网络
        Bitmap getmemorycache = mMemorycache.getmemorycache(url);
        if (getmemorycache!=null){
            imageView.setImageBitmap(getmemorycache);
        }
        Bitmap bitmap = mLocalCache.getlocalCache(url);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            mMemorycache.setmemorycache(url,bitmap);
            return;
        }
        mNetCacheUtils.getBitmapFromNet(imageView,url);
    }

}

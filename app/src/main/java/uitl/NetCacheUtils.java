package uitl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络缓存工具类
 * Created by jack on 2017/6/6.
 */

public class NetCacheUtils {
   private LocalCahce mLocalcache;
    private String url;
 private MemoryCache mMemoryCache;
    public NetCacheUtils(LocalCahce mLocalCache, MemoryCache mMemorycache) {
        mLocalcache=mLocalCache;
        mMemoryCache=mMemorycache;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        //AsyncTask异步封装工具，可以实现异步请求及主界面的更新（对线程池+handler的封装）
        new BitemapTask().execute(imageView,url);//启动asyncTASK 参数传到donibackground
    }

    /**
     * 三个泛型意义
     * 第一个泛型 donibackground 里的参数类型
     * 第二个泛型onProgressUpdate里的参数类型 用以更新
     * 第三个泛型 onPostExecute里的参数类型 以及donibackground 的返回类型
     */
    class BitemapTask extends AsyncTask<Object, Integer, Bitmap> {


        private ImageView imageView;

        //1预加载 在主线程加载
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //2正在加载  运行在子线程 (核心方法) 可以直接异步请求
        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];

         // imageView.setTag(url);
            //开始下载图片
           Bitmap bitmap=download(url);
           // publishProgress();更新进度 在 onProgressUpdate回调
            return bitmap;
        }

        //3更新进度的方法，运行在主线程(
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        //4加载结束 运行在主线程核心方法) 可以直接更新UI
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                //由于listview的重用机制导致 imageview对象可能被多个item共用，从而可能将错误的图片设置给imageview
                //所以此处需要校验

                imageView.setImageBitmap(bitmap);
                mLocalcache.setlocalCache(url,bitmap);
                mMemoryCache.setmemorycache(url,bitmap);
            }
            super.onPostExecute(bitmap);
        }

    }

    private Bitmap download(String url) {
        HttpURLConnection connection=null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);//读取超时
            connection.setConnectTimeout(5000);//链接超时
            connection.connect();
          if (connection.getResponseCode()==200){
              InputStream inputStream = connection.getInputStream();
              Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//根据输入流生成一个bitmap对象
              return  bitmap;
          }
        }
        catch (Exception e){
 return  null;
        }finally {
            if (connection!=null){
                connection.disconnect();
            }
        }

       return null;
    }

}

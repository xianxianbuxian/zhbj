package uitl;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存
 * 2.3以后 软引用 和弱引用更容易被垃圾回收机制回收
 * 使用LURcache
 * Created by jack on 2017/6/6.
 */

public class MemoryCache {
    //软引用来封装 是的垃圾回收机制可以考虑回收  SoftReference  弱引用 weakreference
//    private HashMap<String,SoftReference<Bitmap> > mMemory=new HashMap<>();
    private LruCache<String,Bitmap > mMemory;
    public  MemoryCache(){
        //lrucache 可以将最近最少使用的对象回收掉 从而保证内存不会超出范围
        long maxMemory = Runtime.getRuntime().maxMemory();//获取分配给APP的内存大小
        //lru least recentlly used 最近最少使用算法
        mMemory=new LruCache<String,Bitmap>((int) (maxMemory/8)){
            //返回每个对象的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();//返回字节的大小 计算图片大小
              //  int  byteCount= value.getRowBytes() * value.getHeight();
                return byteCount ;
            }
        };
    }
    public  void setmemorycache(String url,Bitmap bitmap){
       // mMemory.put(url,bitmap);
//        SoftReference<Bitmap>  soft=new SoftReference<Bitmap>(bitmap);//使用软引用将bitmap包装起来
//        mMemory.put(url,soft);
        mMemory.put(url,bitmap);
    }
    public     Bitmap getmemorycache(String url){
//        SoftReference<Bitmap> softReference = mMemory.get(url);
//         if (softReference!=null){
//             Bitmap bitmap = softReference.get();
//             return bitmap;
//         }
//        //Bitmap bitmap = mMemory.get(url);
        Bitmap bitmap = mMemory.get(url);
        return bitmap;
    }
}

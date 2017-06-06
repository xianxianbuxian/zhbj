package uitl;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * 内存缓存
 * Created by jack on 2017/6/6.
 */

public class MemoryCache {
    //软引用来封装 是的垃圾回收机制可以考虑回收  SoftReference  弱引用 weakreference
    private HashMap<String,SoftReference<Bitmap> > mMemory=new HashMap<>();
    public  void setmemorycache(String url,Bitmap bitmap){
       // mMemory.put(url,bitmap);
        SoftReference<Bitmap>  soft=new SoftReference<Bitmap>(bitmap);//使用软引用将bitmap包装起来
        mMemory.put(url,soft);
    }
    public     Bitmap getmemorycache(String url){
        SoftReference<Bitmap> softReference = mMemory.get(url);
         if (softReference!=null){
             Bitmap bitmap = softReference.get();
             return bitmap;
         }
        //Bitmap bitmap = mMemory.get(url);
        return null;
    }
}

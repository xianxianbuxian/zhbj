package uitl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 本地缓存
 * Created by jack on 2017/6/6.
 */

public class LocalCahce {
    private static final String LOCAL_CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/zhbj_cache";
    public  void setlocalCache(String url, Bitmap bitmap){
        File file=new File(LOCAL_CACHE_PATH);
        if (!file.exists()||file.isDirectory()) {
            file.mkdirs();//创建文件夹
        }
        try {
            String filename = MD5Encoder.encode(url);
             File cacheFile=new File(file,filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(cacheFile));//参1图片格式 参2 压缩比例0-100 参3输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public   Bitmap getlocalCache(String url){
        try {
            File cachefile=new File(LOCAL_CACHE_PATH,MD5Encoder.encode(url));
            if (cachefile.exists()){
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cachefile));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

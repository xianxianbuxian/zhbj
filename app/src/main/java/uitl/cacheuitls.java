package uitl;

import android.content.Context;

/**
 * Created by jack on 2017/5/30.
 */

public class cacheuitls {
    public static void setcache(String url, String vaule, Context cxt){
            SPerfUitls.setstring(cxt,url,vaule);
    }
    public static String getcache(String url ,Context cxt){
     return SPerfUitls.getString(cxt,url,null);

    }
}

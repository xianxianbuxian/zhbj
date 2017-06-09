package uitl;

import android.content.Context;

/**
 * Created by jack on 2017/6/8.
 */

public class DensityUtils {
     public  static  int dip2px(float dip, Context cxt){
         float density=    cxt.getResources().getDisplayMetrics().density;//设备密度
          int px= (int) (dip*density+0.5f);//四舍五入
         return  px;
     }
    public  static  float px2px(float px, Context cxt){
        float density=    cxt.getResources().getDisplayMetrics().density;//设备密度
        float dp=px/density;
        return  dp;
    }
}

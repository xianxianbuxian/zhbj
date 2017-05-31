package uitl;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jack on 2017/5/29.
 */

public class SPerfUitls {
    public static boolean getboolean(Context ctx,String key,boolean defvalue){
       SharedPreferences sp= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
      return   sp.getBoolean(key,defvalue);
    }
    public static void setboolean(Context ctx,String key,boolean value){
        SharedPreferences sp= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
       sp.edit().putBoolean(key,value).apply();
    }
    public static String getString(Context ctx,String key,String defvalue){
        SharedPreferences sp= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return   sp.getString(key,defvalue);
    }
    public static void setstring(Context ctx,String key,String value){
        SharedPreferences sp= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();
    }
    public static int getint(Context ctx,String key,int defvalue){
        SharedPreferences sp= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return   sp.getInt(key,defvalue);
    }
    public static void setint(Context ctx,String key,int value){
        SharedPreferences sp= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).apply();
    }
}

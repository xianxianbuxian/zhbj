package domain;

import java.util.ArrayList;

/**
 * 组图对象
 * Created by jack on 2017/6/5.
 */

public class PhotoBean {
    public PhotoData data;

    public  class PhotoData {
        public ArrayList<PhoneNews> news;
    }
    public class  PhoneNews{
        public int id;
        public String listimage;
        public String title;
    }
}

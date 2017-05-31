package domain;

import java.util.ArrayList;

/**
 * Created by jack on 2017/5/30.
 */

public class NewsMenu {
    public  int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsmenuData> data;
    //侧边栏对象
    public  class NewsmenuData{
        public int id;

        @Override
        public String toString() {
            return "NewsmenuData{" +
                    "children=" + children +
                    '}';
        }

        public String title;
        public int type;
        public  ArrayList<NewstabData> children;
    }
    //页签对象
    public class NewstabData{
        public int id;
        public String title;

        @Override
        public String toString() {
            return "NewstabData{" +
                    "title='" + title + '\'' +
                    '}';
        }

        public int type;
        public String url;
    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "data=" + data +
                '}';
    }
}

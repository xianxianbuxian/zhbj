package base.impl.menu;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import base.BaseMenuDetailPager;
import butterknife.BindView;
import domain.PhotoBean;
import global.globalconstant;
import jack.example.com.zhbj.R;
import uitl.cacheuitls;

/**
 * 图片 详情页
 * Created by jack on 2017/5/30.
 */

public class PotosmenuDetailpage extends BaseMenuDetailPager {
    @BindView(R.id.lv_photo)
    ListView lvPhoto;
    @BindView(R.id.gv_photo)
    GridView gvPhoto;
    private ArrayList<PhotoBean.PhoneNews> mNews;
     private boolean islistview=true;//标记当前是否是listview
    private ImageButton mBtnphoto;
    public PotosmenuDetailpage(Activity activity, ImageButton btnphoto) {
        super(activity);
        mBtnphoto=btnphoto;
        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(islistview){
                 lvPhoto.setVisibility(View.GONE);

                 gvPhoto.setVisibility(View.VISIBLE);
                 mBtnphoto.setImageResource(R.drawable.icon_pic_list_type);
                 islistview=false;
             }else {
                 lvPhoto.setVisibility(View.VISIBLE);
                 gvPhoto.setVisibility(View.GONE);
                 mBtnphoto.setImageResource(R.drawable.icon_pic_grid_type);
                 islistview=true;
             }
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_photo, null);
        lvPhoto= (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto= (GridView) view.findViewById(R.id.gv_photo);
        return view;
    }

    @Override
    public void initData() {
        String getcache = cacheuitls.getcache(globalconstant.PHOTO_URL, mActivity);
        if (!TextUtils.isEmpty(getcache)){
            progessDate(getcache);
        }
        getdatafromserver();
    }

    public void getdatafromserver() {
        HttpUtils utils=new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, globalconstant.PHOTO_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                progessDate(result);
                cacheuitls.setcache(globalconstant.PHOTO_URL,result,mActivity);
            }


            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void progessDate(String result) {
        Gson gson=new Gson();
        PhotoBean data = gson.fromJson(result, PhotoBean.class);
        mNews = data.data.news;
        lvPhoto.setAdapter(new PhotoAdapter());
        gvPhoto.setAdapter(new PhotoAdapter());//gridview和listview item布局一样所以公用一个
    }
    class PhotoAdapter extends BaseAdapter{

        private final BitmapUtils bitmapUtils;

        public  PhotoAdapter(){
            bitmapUtils = new BitmapUtils(mActivity);
     bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }
        @Override
        public int getCount() {
            return mNews.size();
        }

        @Override
        public PhotoBean.PhoneNews getItem(int position) {
            return mNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewholder holder;
            if(convertView==null){
                convertView= View.inflate(mActivity, R.layout.list_photo, null);
                holder=new viewholder();
                holder.iv_photo= (ImageView) convertView.findViewById(R.id.iv_list_photo);
                holder.tv_title= (TextView) convertView.findViewById(R.id.tv_photo_title);
                convertView.setTag(holder);
            }else {
                holder= (viewholder) convertView.getTag();
            }
            PhotoBean.PhoneNews item = getItem(position);
            holder.tv_title.setText(item.title);
            bitmapUtils.display(holder.iv_photo,item.listimage);
            return convertView;
        }
    }
    public class  viewholder{
        ImageView iv_photo;
        TextView tv_title;
    }
}

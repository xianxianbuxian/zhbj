package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import base.impl.newspager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import domain.NewsMenu;
import jack.example.com.zhbj.MainActivity;
import jack.example.com.zhbj.R;

/**
 * Created by jack on 2017/5/29.
 */

public class  Leftmenufragment extends BaseFragment {
    @BindView(R.id.lv_list)
    ListView lvList;
    Unbinder unbinder;
    private ArrayList<NewsMenu.NewsmenuData> mNesmenuData;
    private int mCurrenPos;//被选中的item的位置
    private leftmenuadapter mLeftmenuadapter;

    @Override
    protected void initDate() {


    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_letf_menu, null);
        return view;
    }

    //给侧边栏设置数据
    public void setMenuData(ArrayList<NewsMenu.NewsmenuData> data) {
        mCurrenPos=0;
        //更新页面
        mNesmenuData = data;
        mLeftmenuadapter = new leftmenuadapter();
        lvList.setAdapter(mLeftmenuadapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrenPos=position;
                mLeftmenuadapter.notifyDataSetChanged();
                toggle();//开关启侧边栏
                  //点击侧边栏按钮开启不同的内容
               setCurrenDetailpager(position);
            }
        });
    }

    private void setCurrenDetailpager(int position) {
        //获取新闻中心的对象
       MainActivity mainui= (MainActivity) mActivity;
        Contentfragment contentfragment = mainui.getcontentFragment();
        newspager newsp = contentfragment.getnewspager();
        newsp.setCurrenDetailPager(position);
    }

    private void toggle() {
        MainActivity mainui= (MainActivity) mActivity;
        SlidingMenu menu = mainui.getSlidingMenu();
        menu.toggle();//如果开关 ，如果关就开
    }

    class leftmenuadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNesmenuData.size();
        }

        @Override
        public NewsMenu.NewsmenuData getItem(int position) {
            return mNesmenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_leftmenu, null);
                holder = new ViewHolder(convertView.findViewById(R.id.leftmenu_title));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsMenu.NewsmenuData item = getItem(position);
            holder.leftmenuTitle.setText(item.title);
            if(mCurrenPos==position){
                holder.leftmenuTitle.setEnabled(true);
            }else {
                holder.leftmenuTitle.setEnabled(false);
            }
            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.leftmenu_title)
        TextView leftmenuTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

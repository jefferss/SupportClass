package com.example.android.supportclass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.android.supportclass.domain.ListInfor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    private Activity mActivity;
    private RadioGroup rgGroup;
    private RadioGroup rgListGroup;
    private Intent intent;
    private ViewPager mViewPager;
    private ArrayList<ImageView> mImageViewList;//集合
    //保存图片数组
    private int[] mImageIDs= new int[]{R.drawable.guild1,R.drawable.guild2,R.drawable.guild3,R.drawable.guild1,R.drawable.guild2,R.drawable.guild3};
    //首页list 1
    private List<ListInfor> infor_list;
    //首页list 2
    private List<ListInfor> courses_list;
    //首页list控件
    private ListView homeListView;
    private HomeInforListAdapter homeInforListAdapter;
    private HomeCoursesListAdapter homeCoursesListAdapter;

    //list切换判断位
    private boolean isInfor;
    //首页list切换按钮组
    private RadioGroup rg_home_list;
    private RadioButton rbnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity=this;

        rgGroup = (RadioGroup)findViewById(R.id.rg_group);
        radioGroupChanged(rgGroup);
        mViewPager= (ViewPager) findViewById(R.id.home_vp_guide);
        rgListGroup = (RadioGroup)findViewById(R.id.rg_school);
        homeListView= (ListView) findViewById(R.id.home_infor);
        rg_home_list= findViewById(R.id.rg_school);
        //拿到view
        initData();
        mViewPager.setAdapter(new GuildAdapter());//设置数据
        //list 点击事件
        listItemOnClick();
        //首页list监听方法
        radioGroupListChanged(rg_home_list);
        //判断位初始化
        isInfor=true;

    }
    private final String TAG = "PrepareActivity";
    //list点击事件方法
    public void listItemOnClick(){

        homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                if(isInfor==true){
                ListInfor listInfor =infor_list.get(i);//拿到list中点击对象
                //被点击文字变灰
                TextView tvTitle = view.findViewById(R.id.home_text_title);
                tvTitle.setTextColor(Color.GRAY);


                //跳转详情页面
               intent= new Intent(mActivity,HomeDetailActivity.class);
                intent.putExtra("homelist",i);
                mActivity.startActivity(intent);
                }else{
                    ListInfor listInfor =courses_list.get(i);//拿到list中点击对象
                    //被点击文字变灰
                    TextView tvTitle = view.findViewById(R.id.home_text_title);
                    tvTitle.setTextColor(Color.GRAY);


                    //跳转详情页面
                    intent= new Intent(mActivity,HomeCousesDetailActivity.class);
                    intent.putExtra("homelist",i);
                    mActivity.startActivity(intent);

                }
            }
        });
    }
    //初始化布局
    private void initData(){
        mImageViewList=new ArrayList<>();

        //初始化滑动图片
        for(int i=0;i<mImageIDs.length;i++){
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIDs[i]);//back 可以填充布局
            mImageViewList.add(view);
        }

        //初始化listview
        infor_list=new ArrayList();
        courses_list=new ArrayList();
       //初始化list的数据
        initListInfor4Infor();
        initListInfor4Courses();
        if(infor_list!=null){
            homeInforListAdapter= new HomeInforListAdapter();
            homeListView.setAdapter(homeInforListAdapter);
        }
    }
    //首页list infor title数据初始化方法
    public void initListInfor4Infor(){
        ListInfor listinfor=new ListInfor("Million Dollar Dinner – tickets for sale - By Otago Polytechnic             ","Jan  18");
        infor_list.add(listinfor);
        listinfor=new ListInfor("The sonic lives of whales - By Otago Polytechnic                                        ","Jan  20");
        infor_list.add(listinfor);
        listinfor=new ListInfor("Audacious win for Design student - By Otago Polytechnic                                  ","Jan 30");
        infor_list.add(listinfor);
        listinfor=new ListInfor("Student's street food pop-up - By Otago Polytechnic                                     ","Jan 30");
        infor_list.add(listinfor);
        listinfor=new ListInfor("TED x Dunedin at OP - By Otago Polytechnic                                                ","Jan 30");
        infor_list.add(listinfor);
        listinfor=new ListInfor("Making a Better World: Research and Teaching - By Otago Polytechnic                      ","Jan 30");
        infor_list.add(listinfor);


    }
    //首页list course title数据初始化方法
    public void initListInfor4Courses(){
        ListInfor listinfor=new ListInfor("New Zealand certificate in English language (level 2)","Jan  18");
        courses_list.add(listinfor);
        listinfor=new ListInfor("New Zealand Certificate in English Language (Level 3)","Jan  20");
        courses_list.add(listinfor);
        listinfor=new ListInfor("New Zealand Certificate in English Language (Level 4)","Jan 30");
        courses_list.add(listinfor);
        listinfor=new ListInfor("New Zealand Certificate in English Language (Level 5)","Jan 30");
        courses_list.add(listinfor);
        listinfor=new ListInfor("New Zealand Certificate in English Language (Level 6)","Jan 30");
        courses_list.add(listinfor);

    }
    //首页水平滑动图片适配器
    class GuildAdapter extends PagerAdapter{
        //返回item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();
        }
        //用于判断object是不是view对象
        @Override
        public boolean isViewFromObject(View view, Object object) {
         return view==object;
        }
        //初始化item布局
        public Object instantiateItem(ViewGroup container ,int position){
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }
        //销毁布局


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView(mImageViewList.get(position));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        rbnHome = (RadioButton) findViewById(R.id.rb_home);
        rbnHome.setChecked(true);
    }

    //radio group 改变监听
    public void radioGroupChanged(RadioGroup rgGroup){
        intent  = new Intent();
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        //首页

                        break;
                    case R.id.rb_word:
                        intent.setClass(MainActivity.this, WordSetListActivity.class );
                        startActivity(intent);
                        break;

                }
            }
        });
    }
    //首页list切换监听器
    //此处判断是哪一个按钮被点击决定重新初始化哪一个list
    public void radioGroupListChanged(RadioGroup rgGroup){

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.school_infor:
                        //首页
                        if(infor_list!=null){
                            homeInforListAdapter= new HomeInforListAdapter();
                            homeListView.setAdapter(homeInforListAdapter);
                            isInfor=true;
                        }
                        break;
                    case R.id.courses_outline:
                        if(courses_list!=null){
                            homeCoursesListAdapter= new HomeCoursesListAdapter();
                            homeListView.setAdapter(homeCoursesListAdapter);
                            isInfor=false;
                        }
                        break;

                }
            }
        });
    }
    //infor listView adapter
    class HomeInforListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return infor_list.size();
        }

        @Override
        public Object getItem(int i) {
            return infor_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view==null){
                view= view.inflate(mActivity ,R.layout.home_list_view,null);
                viewHolder=new ViewHolder();
                viewHolder.homeTitle=view.findViewById(R.id.home_text_title);
                viewHolder.homeDate=view.findViewById(R.id.home_text_date);
                view.setTag(viewHolder);
            }else {

                viewHolder= (ViewHolder) view.getTag();
            }
            ListInfor listinfor1 = (ListInfor) getItem(i);
            viewHolder.homeTitle.setText(listinfor1.getTitle());
            viewHolder.homeDate.setText(listinfor1.getDate());
            return view;
        }

        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
    //course listView adapter
    class HomeCoursesListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return courses_list.size();
        }

        @Override
        public Object getItem(int i) {
            return courses_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view==null){
                view= view.inflate(mActivity ,R.layout.home_list_view,null);
                viewHolder=new ViewHolder();
                viewHolder.homeTitle=view.findViewById(R.id.home_text_title);
                viewHolder.homeDate=view.findViewById(R.id.home_text_date);
                view.setTag(viewHolder);
            }else {

                viewHolder= (ViewHolder) view.getTag();
            }
            ListInfor listinfor1 = (ListInfor) getItem(i);
            viewHolder.homeTitle.setText(listinfor1.getTitle());
            viewHolder.homeDate.setText(listinfor1.getDate());
            return view;
        }

        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
    static class ViewHolder{
        public TextView homeTitle;
        public TextView homeDate;

    }}

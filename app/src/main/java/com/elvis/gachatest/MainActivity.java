package com.elvis.gachatest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //定义控件
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdpter;
    private List<Fragment> mFragments = new ArrayList<>();
    private RadioGroup mGroup;
    private RadioButton myCircle, myDisconvery, myMsg, myCenter;
    private ListView mListView;
    //侧滑栏list文本
    String strs[] = new String[]{"进击的巨人", "霹雳布袋戏", "盗墓笔记"};
    //侧滑栏list图标
    private int[] imgIds = new int[]{R.drawable.ic_leftmenu, R.drawable.ic_leftmenu, R.drawable.ic_leftmenu};

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();//初始化控件
        initEvent();//监听逻辑事件

        initViewPages();//初始化viewpager
    }


    private void initViews() {

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        //初始化RadioGroup按钮
        mGroup = (RadioGroup) findViewById(R.id.radioGroup);
        myCircle = (RadioButton) findViewById(R.id.id_tab_mycircle);
        myDisconvery = (RadioButton) findViewById(R.id.id_tab_discovery);
        myMsg = (RadioButton) findViewById(R.id.id_tab_message);
        myCenter = (RadioButton) findViewById(R.id.id_tab_setting);
        //侧滑栏
        mListView = (ListView) findViewById(R.id.id_lv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);


    }

    private void initViewPages() {
        //初始化四个布局
        Fragment01 tab01 = new Fragment01();
        Fragment02 tab02 = new Fragment02();
        Fragment03 tab03 = new Fragment03();
        Fragment04 tab04 = new Fragment04();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mFragments.add(tab03);
        mFragments.add(tab04);
        //初始化Adapter这里使用FragmentPagerAdapter
        mAdpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }


        };
        mViewPager.setAdapter(mAdpter);

    }


    public void initEvent() {
        //监听RadioGroup
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_tab_mycircle:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.id_tab_discovery:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.id_tab_message:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.id_tab_setting:
                        mViewPager.setCurrentItem(3, false);
                        break;
                    default:
                        break;
                }
            }
        });
        //监听Page滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当viewPager滑动的时候
                switch (position) {
                    case 0:
                        mGroup.check(R.id.id_tab_mycircle);
                        break;
                    case 1:
                        mGroup.check(R.id.id_tab_discovery);
                        break;
                    case 2:
                        mGroup.check(R.id.id_tab_message);
                        break;
                    case 3:
                        mGroup.check(R.id.id_tab_setting);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*设置listView的Adapter*/
        //将图片和文字加入到listview中
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("Img", imgIds[i]);
            listItem.put("text", strs[i]);
            listItems.add(listItem);
        }
        //创建SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.simpleitem, new String[]{"Img", "text"},
                new int[]{R.id.Img, R.id.textView});
        mListView.setAdapter(simpleAdapter);


        //ToolBar

        toolbar.setTitle("APP FrameWork");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawopen,
                R.string.drawclose) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("侧滑栏");

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle("APP FrameWork");
            }


        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    //optionMenus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //此处进行Item逻辑判断处理
        return super.onOptionsItemSelected(item);
    }
    //显示出optionmenu的按钮图标，使用反射让其显示出来

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {

                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return false;//关闭系统menu按键
    }

    @Override
    public void onClick(View v) {


    }


}

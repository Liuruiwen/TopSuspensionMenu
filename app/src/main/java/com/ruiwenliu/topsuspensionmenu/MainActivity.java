package com.ruiwenliu.topsuspensionmenu;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruiwenliu.rxcarouselview.CarouselView;
import com.ruiwenliu.topsuspensionmenu.adapter.CardAdapter;
import com.ruiwenliu.topsuspensionmenu.adapter.HorizontalAdapter;
import com.ruiwenliu.topsuspensionmenu.adapter.TabAdapter;
import com.ruiwenliu.topsuspensionmenu.util.GallerySnapHelper;
import com.ruiwenliu.topsuspensionmenu.weight.MoveTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 顶部悬浮菜单
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_food)
    TextView tvFood;
    @BindView(R.id.tv_fruit)
    TextView tvFruit;
    @BindView(R.id.tv_Stores)
    TextView tvStores;
    @BindView(R.id.tv_hardware)
    TextView tvHardware;
    @BindView(R.id.act_main_vp)
    ViewPager actMainVp;
    @BindView(R.id.act_main_gsv)
    CarouselView actMainGsv;
    @BindView(R.id.act_main_rv)
    RecyclerView actMainRv;
    @BindView(R.id.act_main_swipefresh)
    SwipeRefreshLayout actMainSrf;
    @BindView(R.id.act_main_abl)
    AppBarLayout actMainAbl;
    //    @BindView(R.id.act_main_iv_card)
//    ImageView ivCard;
    @BindView(R.id.act_main_tv_num)
    MoveTextView tvNum;


    private TabAdapter mAdapter = null;
    private HorizontalAdapter mHAdapter = null;
    private int countNum;

    public static final int TYPE_GOODS_ONE = 0;
    public static final int TYPE_GOODS_TWO = 1;
    public static final int TYPE_GOODS_THREE = 2;
    public static final int TYPE_GOODS_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();

    }

    @OnClick({R.id.tv_food, R.id.tv_fruit, R.id.tv_Stores, R.id.tv_hardware, R.id.act_main_tv_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_food:
                setSelectMenu(TYPE_GOODS_ONE);
                break;
            case R.id.tv_fruit:
                setSelectMenu(TYPE_GOODS_TWO);
                break;
            case R.id.tv_Stores:
                setSelectMenu(TYPE_GOODS_THREE);
                break;
            case R.id.tv_hardware:
                setSelectMenu(TYPE_GOODS_FOUR);
                break;
            case R.id.act_main_tv_num:
                startActivity(TwoWayActivity.getIntent(MainActivity.this));
                break;
        }
    }

    int num;

    /**
     * 初始化数据
     */
    private void initData() {

        actMainSrf.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        actMainSrf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mAdapter.refreshData(getListFragment());
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        actMainSrf.setRefreshing(false);//一般是请求完数据后在执行，这里只做演示
                    }
                }, 1200);

                // System.out.println(Thread.currentThread().getName());

                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        /**
         *判断是否刷新
         * 不做判断列表和刷新会有滑动冲突
         */
        actMainAbl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    actMainSrf.setEnabled(true);
                } else {
                    actMainSrf.setEnabled(false);
                }
            }
        });

        /**
         * 添加轮播数据
         */
        actMainGsv.setDotCarouselData(getListImgview(), R.drawable.yuanquan_up2, R.drawable.yuanquan_down2);
        actMainGsv.startCarousel();//开始轮播
        /**
         * 设置横向滚动数据
         */
        mHAdapter = new HorizontalAdapter(getHorizontalData());
        actMainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        actMainRv.setAdapter(mHAdapter);
        GallerySnapHelper snapHelper = new GallerySnapHelper();
        snapHelper.attachToRecyclerView(actMainRv);

        mAdapter = new TabAdapter(getSupportFragmentManager(), getListFragment());
        actMainVp.setAdapter(mAdapter);
        actMainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectMenu(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setSelectMenu(TYPE_GOODS_ONE);
    }

    private List<BaseFragment> getListFragment() {
        List<BaseFragment> list = new ArrayList<>();
        list.add(CardFragment.getInstance());
        list.add(GoodsFragment.getInstance(TYPE_GOODS_TWO));
        list.add(GoodsFragment.getInstance(TYPE_GOODS_THREE));
        list.add(GoodsFragment.getInstance(TYPE_GOODS_FOUR));
        return list;
    }

    /**
     * 设置选中menu
     */
    private void setSelectMenu(int postion) {
        if (mAdapter != null && actMainVp.getCurrentItem() != postion) {
            actMainVp.setCurrentItem(postion);
        }
        tvFood.setSelected(postion == TYPE_GOODS_ONE ? true : false);
        tvFruit.setSelected(postion == TYPE_GOODS_TWO ? true : false);
        tvStores.setSelected(postion == TYPE_GOODS_THREE ? true : false);
        tvHardware.setSelected(postion == TYPE_GOODS_FOUR ? true : false);
    }


    /**
     * 添加轮播图片
     * 这里直接把后台的数据放到view上就可以了
     *
     * @return
     */
    private List<ImageView> getListImgview() {
        List<ImageView> list = new ArrayList<>();
        list.add(setDataToPhotoView("http://imgsrc.baidu.com/forum/w=580/sign=1dc0f5cbaaec08fa260013af69ef3d4d/6c8876c2d56285350f1e83559aef76c6a6ef6325.jpg"));
        list.add(setDataToPhotoView("http://i0.hdslb.com/video/86/8642147023ca8eef937cfba25de85358.jpg"));
        list.add(setDataToPhotoView("http://04.imgmini.eastday.com/mobile/20180729/20180729080013_2e0e4f43723e01e40b81bee9e4d4d4e9_1.jpeg"));
        list.add(setDataToPhotoView("http://pic.wenwo.com/fimg/66712100936_553.jpg"));
        return list;
    }


    /**
     * 临时设置 可以写成公共方法
     *
     * @param url 地址这些数据应该是后台传给你的，这里只是做标识用
     * @return
     */
    private ImageView setDataToPhotoView(final String url) {

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800);//这里最好写成view的布局，这里为了方便简写
        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setLayoutParams(layoutParams);
        Glide.with(this).load(url).into(img);
        return img;
    }

    private List<String> getHorizontalData() {
        List<String> list = new ArrayList<>();
        list.add("http://imgsrc.baidu.com/forum/w=580/sign=1dc0f5cbaaec08fa260013af69ef3d4d/6c8876c2d56285350f1e83559aef76c6a6ef6325.jpg");
        list.add("http://i0.hdslb.com/video/86/8642147023ca8eef937cfba25de85358.jpg");
        list.add("http://04.imgmini.eastday.com/mobile/20180729/20180729080013_2e0e4f43723e01e40b81bee9e4d4d4e9_1.jpeg");
        list.add("http://pic.wenwo.com/fimg/66712100936_553.jpg");
        list.add("http://imgsrc.baidu.com/forum/w=580/sign=1dc0f5cbaaec08fa260013af69ef3d4d/6c8876c2d56285350f1e83559aef76c6a6ef6325.jpg");
        list.add("http://i0.hdslb.com/video/86/8642147023ca8eef937cfba25de85358.jpg");
        list.add("http://04.imgmini.eastday.com/mobile/20180729/20180729080013_2e0e4f43723e01e40b81bee9e4d4d4e9_1.jpeg");
        list.add("http://pic.wenwo.com/fimg/66712100936_553.jpg");
        list.add("http://imgsrc.baidu.com/forum/w=580/sign=1dc0f5cbaaec08fa260013af69ef3d4d/6c8876c2d56285350f1e83559aef76c6a6ef6325.jpg");
        list.add("http://i0.hdslb.com/video/86/8642147023ca8eef937cfba25de85358.jpg");
        list.add("http://04.imgmini.eastday.com/mobile/20180729/20180729080013_2e0e4f43723e01e40b81bee9e4d4d4e9_1.jpeg");
        list.add("http://pic.wenwo.com/fimg/66712100936_553.jpg");
        list.add("http://imgsrc.baidu.com/forum/w=580/sign=1dc0f5cbaaec08fa260013af69ef3d4d/6c8876c2d56285350f1e83559aef76c6a6ef6325.jpg");
        list.add("http://i0.hdslb.com/video/86/8642147023ca8eef937cfba25de85358.jpg");
        list.add("http://04.imgmini.eastday.com/mobile/20180729/20180729080013_2e0e4f43723e01e40b81bee9e4d4d4e9_1.jpeg");
        list.add("http://pic.wenwo.com/fimg/66712100936_553.jpg");
        return list;
    }

    /**
     * 增加数量
     *
     * @param num
     */
    public void addItem(int num) {
        countNum = countNum + num;
        tvNum.setText(String.valueOf(countNum));
    }

    /**
     * 减少数量
     *
     * @param num
     */
    public void cutItem(int num) {
        countNum = countNum - num;
        tvNum.setText(String.valueOf(countNum));
    }


}

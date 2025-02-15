package com.matrix.yukun.matrix.main_module.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.matrix.yukun.matrix.BaseFragment;
import com.matrix.yukun.matrix.MyApp;
import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.chat_module.ChatMemberActivity;
import com.matrix.yukun.matrix.download_module.DownLoadActivity;
import com.matrix.yukun.matrix.main_module.activity.HistoryTodayActivity;
import com.matrix.yukun.matrix.main_module.activity.LoginActivity;
import com.matrix.yukun.matrix.main_module.activity.MViewPagerAdapter;
import com.matrix.yukun.matrix.main_module.activity.MapActivity;
import com.matrix.yukun.matrix.main_module.activity.MyCollectActivity;
import com.matrix.yukun.matrix.main_module.activity.PersonCenterActivity;
import com.matrix.yukun.matrix.main_module.entity.EventCategrayPos;
import com.matrix.yukun.matrix.main_module.entity.EventShowSecond;
import com.matrix.yukun.matrix.main_module.entity.EventUpdateHeader;
import com.matrix.yukun.matrix.main_module.entity.UserInfo;
import com.matrix.yukun.matrix.main_module.main.SearchActivity;
import com.matrix.yukun.matrix.main_module.utils.SPUtils;
import com.matrix.yukun.matrix.main_module.utils.ScreenUtils;
import com.matrix.yukun.matrix.main_module.utils.ToastUtils;
import com.matrix.yukun.matrix.mine_module.activity.SettingActivity;
import com.matrix.yukun.matrix.mine_module.activity.ShareActivity;
import com.matrix.yukun.matrix.selfview.guideview.Guide;
import com.matrix.yukun.matrix.selfview.guideview.GuideBuilder;
import com.matrix.yukun.matrix.selfview.guideview.SimpleComponent;
import com.matrix.yukun.matrix.selfview.guideview.SimpleComponent2;
import com.matrix.yukun.matrix.tool_module.btmovie.SpecialActivity;
import com.matrix.yukun.matrix.tool_module.weather.activity.HeWeatherActivity;
import com.matrix.yukun.matrix.util.log.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
/**
 * Created by yukun on 18-1-2.
 */

public class PlayFragment extends BaseFragment {
    @BindView(R.id.iv_main)
    ImageView mIvMain;
    @BindView(R.id.rl_contain)
    RelativeLayout mRlContain;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.drawlayout)
    DrawerLayout mDrawlayout;
    @BindView(R.id.tv_weather)
    TextView mTvWeather;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.im_snow)
    ImageView mImSnow;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.im_bird)
    ImageView mImBird;
    @BindView(R.id.rl_movie)
    RelativeLayout mRlMovie;
    @BindView(R.id.im_modu)
    ImageView mImModu;
    @BindView(R.id.rl_change_modul)
    RelativeLayout mRlChangeModul;
    @BindView(R.id.im_ball)
    ImageView mImBall;
    @BindView(R.id.rl_me)
    RelativeLayout mRlMe;
    @BindView(R.id.tv_close)
    TextView mTvClose;
    @BindView(R.id.sc_contain)
    ScrollView mScrollview;
    @BindView(R.id.rl_down)
    RelativeLayout mRlDown;
    @BindView(R.id.iv_chat)
    ImageView mIvChat;
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.rl_collect)
    RelativeLayout mRlCollect;
    @BindView(R.id.rl_one_map)
    RelativeLayout mRlOneMap;
    @BindView(R.id.head)
    CircleImageView mCircleImageView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.rl_bg_special)
    TextView mTvSig;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.ll_drawable)
    LinearLayout mLayout;
    @BindView(R.id.iv_update)
    ImageView ivUpdate;
    private MViewPagerAdapter mMViewPagerAdapter;
    private String[] mStringArray;
    private List<Fragment> mFragments = new ArrayList<>();
    private VideoFragment mInstance1;
    private ImageFragment mInstance3;
    private TextFragment mInstance4;
    private VerticalVideoFragment mInstance5;
    private RecFragment mInstance;
    int count = 0;
    private JokeFragment mInstance2;

    public static PlayFragment getInstance() {
        PlayFragment playFragment = new PlayFragment();
        return playFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_play;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mStringArray = getResources().getStringArray(R.array.title);
        for (int i = 0; i < mStringArray.length; i++) {
            mTablayout.addTab(mTablayout.newTab().setText(mStringArray[i]));
        }
        setDrawableWidth();
        if (savedInstanceState != null) {
            mInstance = RecFragment.getInstance();
            mInstance1 = VideoFragment.getInstance();
            mInstance2 = JokeFragment.getInstance();
            mInstance3 = ImageFragment.getInstance();
            mInstance4 = TextFragment.getInstance();
            mInstance5 = VerticalVideoFragment.getInstance();
        } else {
            mInstance = RecFragment.getInstance();
            mInstance1 = VideoFragment.getInstance();
            mInstance2 = JokeFragment.getInstance();
            mInstance3 = ImageFragment.getInstance();
            mInstance4 = TextFragment.getInstance();
            mInstance5 = VerticalVideoFragment.getInstance();
        }
        mFragments.add(mInstance);
        mFragments.add(mInstance1);
        mFragments.add(mInstance2);
        mFragments.add(mInstance3);
        mFragments.add(mInstance4);
        mFragments.add(mInstance5);
        setAdapter();
        setListener();
        OverScrollDecoratorHelper.setUpOverScroll(mScrollview);
        mTvClose.setText("登录");
        if (MyApp.userInfo != null) {
            Glide.with(getContext()).load(MyApp.userInfo.getAvator()).into(mCircleImageView);
            mTvName.setText(MyApp.userInfo.getName());
            mTvSig.setText("签名：" + MyApp.userInfo.getSignature());
            mTvClose.setText("退出");
        }
        long guide_time = SPUtils.getInstance().getLong("guide_time");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - guide_time > 3 * 24 * 60 * 60 * 1000) {
            mIvSearch.post(new Runnable() {
                @Override
                public void run() {
                    showGuide();
                }
            });
        }
        if (TextUtils.isEmpty(SPUtils.getInstance().getString("city"))) {
            updatePosition();
        } else {
            getWeather(SPUtils.getInstance().getString("city"));
            ivUpdate.setVisibility(View.GONE);
        }
    }

    private void getWeather(String city) {
        if (getContext() != null) {
            HeWeather.getWeatherNow(getContext(), city, new HeWeather.OnResultWeatherNowBeanListener() {
                @Override
                public void onError(Throwable throwable) {
                    LogUtil.i("=======throwable", throwable.toString());
                }

                @Override
                public void onSuccess(Now now) {
                    if (Code.OK.getCode().equalsIgnoreCase(now.getStatus())) {
                        NowBase nowNow = now.getNow();
                        mTvWeather.setText(city + '\n' + "体感温度:" + nowNow.getTmp() + "℃");
                    }
                }
            });
        }
    }

    public AMapLocationClient mLocationClient = null;

    private void updatePosition() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        ivUpdate.setVisibility(View.GONE);
                        //解析定位结果
                        SPUtils.getInstance().saveString("city", aMapLocation.getCity());
                        SPUtils.getInstance().saveString("latitude", String.valueOf(aMapLocation.getLatitude()));
                        SPUtils.getInstance().saveString("longitude", String.valueOf(aMapLocation.getLongitude()));
                        getWeather(aMapLocation.getCity());
                    } else {
                        ivUpdate.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //启动定位
        mLocationClient.startLocation();
    }

    private void showGuide() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mIvSearch)
                .setAlpha(180)
                .setHighTargetCorner(10)
                .setHighTargetPadding(10);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {

            }

            @Override
            public void onDismiss() {
                showGuide2();
            }
        });

        builder.addComponent(new SimpleComponent());
        Guide guide = builder.createGuide();
        guide.show(getActivity());
    }

    private void showGuide2() {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(mIvChat)
                .setAlpha(180)
                .setHighTargetCorner(10)
                .setHighTargetPadding(10);
        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {

            }

            @Override
            public void onDismiss() {
//                showGuideView2();
            }
        });

        builder.addComponent(new SimpleComponent2());
        Guide guide = builder.createGuide();
        guide.show(getActivity());
    }


    private void setDrawableWidth() {
        ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
        layoutParams.width = ScreenUtils.instance().getWidth(getContext()) * 4 / 5;
        mLayout.setLayoutParams(layoutParams);
    }

    private void setAdapter() {
        mMViewPagerAdapter = new MViewPagerAdapter(getChildFragmentManager(), mFragments, mStringArray);
        mViewpager.setAdapter(mMViewPagerAdapter);
        mViewpager.setOffscreenPageLimit(2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventCategrayPos event) {
        /* Do something */
        if (event.pos < 1000) {
            mViewpager.setCurrentItem(event.pos);
        }
    }

    private void setListener() {
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTablayout.setScrollPosition(position, 0, true);
                EventBus.getDefault().post(new EventShowSecond(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHeader(EventUpdateHeader eventUpdateHeader) {
        if (MyApp.userInfo != null) {
            Glide.with(getContext()).load(MyApp.userInfo.getAvator()).into(mCircleImageView);
            mTvName.setText(MyApp.userInfo.getName());
            mTvSig.setText("签名：" + MyApp.userInfo.getSignature());
            mTvClose.setText("退出");
        }
    }

    @OnClick({R.id.iv_chat, R.id.iv_main, R.id.head, R.id.iv_update, R.id.tv_weather, R.id.rl_collect, R.id.rl_main, R.id.iv_search,
            R.id.rl_movie, R.id.rl_change_modul, R.id.rl_me, R.id.tv_close, R.id.rl_bg_special, R.id.iv_share, R.id.rl_down, R.id.rl_one_map})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_main) {
            if (!mDrawlayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawlayout.openDrawer(Gravity.LEFT);
            }
        } else if (i == R.id.tv_weather) {
            HeWeatherActivity.start(getContext());
            closeDrawLayout();
        } else if (i == R.id.rl_main) {
            mViewpager.setCurrentItem(0);
            closeDrawLayout();
        } else if (i == R.id.rl_movie) {//tool
            Intent intentHis = new Intent(getContext(), HistoryTodayActivity.class);
            startActivity(intentHis);
            ((Activity) getContext()).overridePendingTransition(R.anim.rotate, 0);
            closeDrawLayout();

        } else if (i == R.id.rl_change_modul) {
            closeDrawLayout();
            SettingActivity.start(getContext());
        } else if (i == R.id.rl_collect) {
            Intent intentCol = new Intent(getContext(), MyCollectActivity.class);
            startActivity(intentCol);
            ((Activity) getContext()).overridePendingTransition(R.anim.rotate, R.anim.rotate_out);
            closeDrawLayout();

        } else if (i == R.id.rl_me) {
            if (MyApp.userInfo != null) {
                Intent intentAbus = new Intent(getContext(), PersonCenterActivity.class);
                startActivity(intentAbus);
                ((Activity) getContext()).overridePendingTransition(R.anim.rotate, R.anim.rotate_out);
                closeDrawLayout();
            } else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        } else if (i == R.id.rl_one_map) {
            MapActivity.start(getContext());
            closeDrawLayout();
        } else if (i == R.id.tv_close) {//退出
            //update UI
            closeDrawLayout();
            mCircleImageView.setImageResource(R.mipmap.snail_image);
            mTvName.setText("$_$");
            mTvSig.setText(getContext().getResources().getString(R.string.title_content));
            mTvClose.setText("登录");
            DataSupport.deleteAll(UserInfo.class);
//            LeanCloudInit.getInstance().logout();
            MyApp.setUserInfo(null);
            EventUpdateHeader eventUpdateHeader = new EventUpdateHeader();
            eventUpdateHeader.setLoginOut(true);
            EventBus.getDefault().post(eventUpdateHeader);
            SPUtils.getInstance().clearKey("user");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        } else if (i == R.id.head) {
            if (MyApp.userInfo != null) {
                Intent intentAbus = new Intent(getContext(), PersonCenterActivity.class);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                    startActivity(intentAbus, ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), mCircleImageView, "shareview").toBundle());
                } else {
                    getContext().startActivity(intentAbus);
                    ((Activity) getContext()).overridePendingTransition(R.anim.rotate, R.anim.rotate_out);
                }
                closeDrawLayout();
            } else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), mCircleImageView, "shareview").toBundle());
                } else {
                    getContext().startActivity(intent);
                    ((Activity) getContext()).overridePendingTransition(R.anim.rotate, R.anim.rotate_out);
                }
            }
        } else if (i == R.id.iv_chat) {
            ChatMemberActivity.start(getContext());
            closeDrawLayout();

        } else if (i == R.id.iv_share) {
            Intent intent = new Intent(getContext(), ShareActivity.class);
            startActivity(intent);
        } else if (i == R.id.rl_bg_special) {
            count++;
            if (count == 3) {
                count = 0;
                closeDrawLayout();
                Intent intent = new Intent(getContext(), SpecialActivity.class);
                startActivity(intent);
            }
        } else if (i == R.id.rl_down) {
            Intent intentDown = new Intent(getContext(), DownLoadActivity.class);
            startActivity(intentDown);
        } else if (i == R.id.iv_search) {
            SearchActivity.start(getContext(), mIvSearch);
        } else if (i == R.id.iv_update) {
            updatePosition();
            ToastUtils.showToast("请确保位置打开位置权限");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void closeDrawLayout() {
        mDrawlayout.closeDrawer(Gravity.LEFT);
    }

    //双击退出
    private long firstTime = 0;


}

package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.PageDto;
import com.sttech.supervisor.dto.ProjectAttachDto;
import com.sttech.supervisor.dto.ProjectDetailDto;
import com.sttech.supervisor.dto.ProjectPageDto;
import com.sttech.supervisor.http.HttpManager;
import com.sttech.supervisor.http.callback.OnResultCallBack;
import com.sttech.supervisor.http.subscriber.HttpSubscriber;
import com.sttech.supervisor.ui.adapter.TabPagerAdapter;
import com.sttech.supervisor.ui.fragment.detail.CustomerAnalysis;
import com.sttech.supervisor.ui.fragment.detail.CustomerInfo;
import com.sttech.supervisor.ui.fragment.detail.DecorationInfo;
import com.sttech.supervisor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目详情界面
 * Created by 韦国旺 on 2017/5/3.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ProjectDetailActivity extends TActivity {

    public static void start(Context context, String projectId, boolean isHaveAddBtn) {
        Intent intent = new Intent(context, ProjectDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_PROJECT_ID, projectId);
        bundle.putBoolean(Constant.KEY_HAVE_ADD, isHaveAddBtn);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_project_detaiil);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private String[] mTitles;
    private ViewPager vp;
    private TabLayout tl;
    private List<Fragment> fragmentList;
    private AppBarLayout appBarLayout;
    private DecorationInfo decorationInfo;
    private CustomerInfo customerInfo;
    private CustomerAnalysis customerAnalysis;


    private void initView() {
        mTitles = getResources().getStringArray(R.array.project_detail_tab);
        boolean isHaveAddBtn = getIntent().getBooleanExtra(Constant.KEY_HAVE_ADD, false);
        if (isHaveAddBtn) {
            initNavigation(getString(R.string.project_detail), null, null, 0, R.drawable.sele_add_btn, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addProfile();
                }
            });
        } else {
            initNavigation(getString(R.string.project_detail));
        }

        vp = (ViewPager) findViewById(R.id.detail_project_vp);
        tl = (TabLayout) findViewById(R.id.detail_project_tablayout);
        appBarLayout = findById(R.id.app_bar);

        fragmentList = new ArrayList<>();
        fragmentList.add(decorationInfo = new DecorationInfo());
        fragmentList.add(customerInfo = new CustomerInfo());
        fragmentList.add(customerAnalysis = new CustomerAnalysis());

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mTitles, fragmentList);
        vp.setAdapter(tabPagerAdapter);
        vp.setOffscreenPageLimit(3);
        tl.setupWithViewPager(vp);
        tl.post(new Runnable() {
            @Override
            public void run() {
                CommonUtils.setIndicator(tl, 20, 20);
            }
        });
        setViewPagerTouchListener(vp, appBarLayout);
        setTabLayoutTabSelectedListener(tl, appBarLayout);
    }


    @BindView(R.id.detail_project_title)
    TextView projectTitle;

    @BindView(R.id.detail_project_manager)
    TextView projectManager;

    @BindView(R.id.detail_project_time)
    TextView projectTme;


    PageDto<ProjectAttachDto> attachList;

    private void initData() {
        String projectId = getIntent().getStringExtra(Constant.EXTRA_PROJECT_ID);
        HttpSubscriber httpSubscriber = new HttpSubscriber(new OnResultCallBack<ProjectPageDto>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(ProjectPageDto projectPageDto) {

                ProjectDetailDto detailDto = projectPageDto.getDetailDto();
                projectTitle.setText(detailDto.getTitle());
                projectManager.setText(detailDto.getCustomerManagerName());
                projectTme.setText(detailDto.getCreateTime());
                decorationInfo.setProjectDetail(detailDto);
                customerInfo.setProjectDetail(detailDto);
                Logger.d("detailDto value ->" + detailDto.getDecorationCountLabel());
                customerAnalysis.setProjectDetail(detailDto);
                attachList = projectPageDto.getAttachList();

            }


            @Override
            public void onError(int code, String errorMsg) {

            }

            @Override
            public void onCompleted() {
                //TODO 界面TEST


                ProjectDetailDto detailDto = new ProjectDetailDto();
                detailDto.setTitle("写字楼装潢");
                detailDto.setCreateTime("5月16日 17:00:00");
                detailDto.setCustomerManagerName("赵春来");
                detailDto.setFullProjectAddress("东风西路197号国际金融大厦");
                detailDto.setDecorationCategoryLabel("办公室");
                detailDto.setDecorationCountLabel("test1");
                detailDto.setAboutDesignLabel("test222222");

                PageDto<ProjectAttachDto> pageDto = new PageDto<>();
                pageDto.setCurrentPageNum(1);

                ProjectAttachDto projectAttachDto = new ProjectAttachDto();
                projectAttachDto.setCreateTime("2017年5月17日 10：16：00");
                List<ProjectAttachDto> projectAttachDtoList = new ArrayList<>();
                projectAttachDtoList.add(projectAttachDto);
                pageDto.setDataList(projectAttachDtoList);


                ProjectPageDto projectPageDto = new ProjectPageDto();
                projectPageDto.setDetailDto(detailDto);
                projectPageDto.setAttachList(pageDto);
                onSuccess(projectPageDto);

            }
        });
        HttpManager.getInstance().getProjectDetail(httpSubscriber, projectId);
    }


    /**
     * 添加资料
     */

    private void addProfile() {
        initPopupWindow();

    }


    private PopupWindow popupWindow;

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }


    protected void initPopupWindow() {
        View popupWindowView = getLayoutInflater().inflate(R.layout.ly_add_profile, null);
        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        //显示位置
        popupWindow.showAtLocation(popupWindowView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置背景半透明
        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        LinearLayout item2Btn = (LinearLayout) popupWindowView.findViewById(R.id.add_profile_item2);
        item2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                WriteWorkRecordActivity.start(ProjectDetailActivity.this);
            }
        });

        LinearLayout item1Btn = (LinearLayout) popupWindowView.findViewById(R.id.add_profile_item1);
        item1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                UploadDataActivity.start(ProjectDetailActivity.this, attachList);
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}

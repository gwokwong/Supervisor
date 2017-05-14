package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.adapter.ProjectDataListAdapter;
import com.sttech.supervisor.entity.ProjectData;
import com.sttech.supervisor.ui.widget.SpacesItemDecoration;
import com.sttech.supervisor.ui.widget.xrecyclerview.RecyclerItemCallback;
import com.sttech.supervisor.ui.widget.xrecyclerview.XRecyclerView;

/**
 * function : 上传资料界面
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class UploadDataActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, UploadDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_data);
        initView();
    }

    private static int position = 0;
    private XRecyclerView xRecyclerView;
    private ProjectDataListAdapter projectDataListAdapter;

    private void initView() {
        initNavigation(getString(R.string.upload_data));
        xRecyclerView = (XRecyclerView) findViewById(R.id.upload_xrecycler);
        projectDataListAdapter = new ProjectDataListAdapter(this);
        xRecyclerView.verticalLayoutManager().setAdapter(projectDataListAdapter);
        xRecyclerView.setOnRefreshListener(new XRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++)
                            projectDataListAdapter.addElement(0, new ProjectData(null, "张三" + (++position), "工作记录", "5月5日", "8:00:00", "作为出钱装修的客户与项目经理之间的沟通桥梁, 负责监督项目经理顺利实 施项目", null));
                        xRecyclerView.refreshComlete();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 2; i++)
                            projectDataListAdapter.addElement(new ProjectData(null, "李四" + (++position), "房屋原始图", "5月10日", "10:00:00", null, null));
                        xRecyclerView.refreshComlete();
                    }
                }, 300);
            }
        });

        xRecyclerView.autoRefresh();
        projectDataListAdapter.setRecItemClick(new RecyclerItemCallback<ProjectData, ProjectDataListAdapter.RecordHolder>() {
            @Override
            public void onItemClick(int position, ProjectData model, ProjectDataListAdapter.RecordHolder holder) {
                super.onItemClick(position, model, holder);
                toast("click position is " + position);
            }
        });
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(0, 30));

    }

    @Override
    public void onCreateBinding() {

    }
}

package com.sttech.supervisor.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectAttachDto;
//import com.sttech.supervisor.entity.ProjectData;
import com.sttech.supervisor.ui.adapter.ProjectDataListAdapter;
import com.sttech.supervisor.ui.fragment.TFragment;
import com.sttech.supervisor.ui.widget.AdvanceDecoration;
import com.sttech.supervisor.ui.widget.xrecyclerview.RecyclerItemCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * function :
 * Created by 韦国旺 on 2017/4/29.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class WorkRecordFragment extends TFragment {

    @Override
    public int provideContentViewId() {
        return R.layout.fr_work_record;
    }

    private List<ProjectAttachDto> projectList = new ArrayList<>();
    private ProjectDataListAdapter projectDataListAdapter;
    private RecyclerView recyclerView;
    private static int position = 0;

    public void initData() {
        for (int i = 0; i < 10; i++) {
            projectList.add(new ProjectAttachDto("", "李四", "2017年6月30号 14:00:00", "", "房屋原始图", null, null));
        }
        projectDataListAdapter = new ProjectDataListAdapter(getActivity(), projectList);
        recyclerView.setAdapter(projectDataListAdapter);
        projectDataListAdapter.setSendFailList(true);


        projectDataListAdapter.setRecItemClick(new RecyclerItemCallback<ProjectAttachDto, ProjectDataListAdapter.RecordHolder>() {
            @Override
            public void onItemClick(int position, ProjectAttachDto model, ProjectDataListAdapter.RecordHolder holder) {
                super.onItemClick(position, model, holder);
                toastInfo("click position is " + position);
            }
        });
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.work_record_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));
    }


}

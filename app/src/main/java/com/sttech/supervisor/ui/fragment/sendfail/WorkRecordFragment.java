package com.sttech.supervisor.ui.fragment.sendfail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.adapter.ProjectDataListAdapter;
import com.sttech.supervisor.entity.ProjectData;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_work_record, container, false);
        initData();
        initView(view);
        return view;
    }

    private List<ProjectData> projectList;
    //    private BaseRecyclerAdapter adapter;
    private ProjectDataListAdapter projectDataListAdapter;
    private RecyclerView recyclerView;
    private static int position = 0;

    private void initData() {
        projectList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            projectList.add(new ProjectData(null, "李四" + (++position), "房屋原始图", "5月10日", "10:00:00", null, null, 12));
//            projectList.add(new Project("创业公司装修" + (++position), "12", "写字楼", "赵先生", "广东省广州市东风西路197号"));
        }
    }

    private void initView(View view) {
//        adapter = new BaseRecyclerAdapter<ProjectData>(getActivity(), projectList, R.layout.item_work_record) {
//
//            @Override
//            public void convert(BaseRecyclerHolder holder, ProjectData item, int position, boolean isScrolling) {
//                holder.setText(R.id.name, item.getName());
//                holder.setText(R.id.date, item.getDate());
//                holder.setText(R.id.time, item.getTime());
//                holder.setText(R.id.type, item.getType());
//            }
//
//        };
        projectDataListAdapter = new ProjectDataListAdapter(getActivity(), projectList);
        recyclerView = (RecyclerView) view.findViewById(R.id.work_record_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(projectDataListAdapter);
        projectDataListAdapter.setSendFailList(true);
//        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, int position) {
//                toastInfo(position + "");
//            }
//        });
        projectDataListAdapter.setRecItemClick(new RecyclerItemCallback<ProjectData, ProjectDataListAdapter.RecordHolder>() {
            @Override
            public void onItemClick(int position, ProjectData model, ProjectDataListAdapter.RecordHolder holder) {
                super.onItemClick(position, model, holder);
                toastInfo(position + "");
            }
        });
        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));
    }


}

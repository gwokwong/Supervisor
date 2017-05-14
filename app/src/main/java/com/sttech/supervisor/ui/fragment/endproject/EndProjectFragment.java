package com.sttech.supervisor.ui.fragment.endproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.activity.ProjectDetailActivity;
import com.sttech.supervisor.ui.adapter.BaseRecyclerAdapter;
import com.sttech.supervisor.ui.adapter.BaseRecyclerHolder;
import com.sttech.supervisor.entity.Project;
import com.sttech.supervisor.ui.fragment.TFragment;
import com.sttech.supervisor.ui.widget.AdvanceDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * function :
 * Created by 韦国旺 on 2017/4/29.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class EndProjectFragment extends TFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_end_prject, container, false);
        initData();
        initView(view);
        return view;
    }

    private List<Project> projectList;
    private BaseRecyclerAdapter projectListAdapter;
    private RecyclerView recyclerView;
    private static int position = 0;

    private void initData() {
        projectList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            projectList.add(new Project("创业公司装修" + (++position), "12", "写字楼", "赵先生", "广东省广州市东风西路197号"));
        }
    }

    private void initView(View view) {
        projectListAdapter = new BaseRecyclerAdapter<Project>(getActivity(), projectList, R.layout.item_end_project) {

            @Override
            public void convert(BaseRecyclerHolder holder, Project item, int position, boolean isScrolling) {
                holder.setText(R.id.project_title, item.getTitle());
                holder.setText(R.id.project_address, item.getAddress());
                holder.setText(R.id.project_type, item.getType());
                holder.setText(R.id.project_status, "竣工");
            }
        };

        recyclerView = (RecyclerView) view.findViewById(R.id.end_project_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(projectListAdapter);
        projectListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                toastInfo(position + "");
                ProjectDetailActivity.start(getActivity(), false);
            }
        });
        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));
    }

}

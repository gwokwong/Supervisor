package com.sttech.supervisor.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectListDto;
import com.sttech.supervisor.ui.activity.ProjectDetailActivity;
import com.sttech.supervisor.ui.adapter.BaseRecyclerAdapter;
import com.sttech.supervisor.ui.adapter.BaseRecyclerHolder;
import com.sttech.supervisor.ui.widget.AdvanceDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * function : 已结束项目列表
 * Created by 韦国旺 on 2017/4/29.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class EndFragment extends TFragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fr_grabsingle_end, null);
            initView(view);// 控件初始化
        }
        return view;


//        View view = inflater.inflate(R.layout.fr_grabsingle_end, container, false);
////        initData();
//        initView(view);
//        return view;
    }

    @Override
    public int provideContentViewId() {
        return R.layout.fr_grabsingle_end;
    }

    private List<ProjectListDto> projectList = new ArrayList<>();
    private BaseRecyclerAdapter projectListAdapter;
    private RecyclerView recyclerView;
//    private static int position = 0;

    public void setProjectList(List<ProjectListDto> list) {
        Logger.d("list.size()->" + list.size());
        if (list == null) {
            Logger.d("return" + (list == null) + (projectListAdapter == null));
            return;
        } else {
            if (projectList != null && projectList.size() > 0) {
                projectList.clear();
            }
            projectList = list;
            Logger.d("setAdapter");
            setAdapter();
//            projectListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initView(View rootView) {
        recyclerView = (RecyclerView) view.findViewById(R.id.grabsingle_end_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));
    }

    //    public void initView(View view) {
//        recyclerView = (RecyclerView) view.findViewById(R.id.grabsingle_end_rv);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));
////        setAdapter();
//    }


    private void setAdapter() {
        projectListAdapter = new BaseRecyclerAdapter<ProjectListDto>(getActivity(), projectList, R.layout.item_end_project) {

            @Override
            public void convert(BaseRecyclerHolder holder, ProjectListDto item, int position, boolean isScrolling) {
                holder.setText(R.id.project_title, item.getTitle());
                holder.setText(R.id.project_address, item.getFullProjectAddress());
                holder.setText(R.id.project_type, item.getDecorationCategoryLabel());
                holder.setText(R.id.project_status, item.getStatusLabel());
            }
        };
        recyclerView.setAdapter(projectListAdapter);
        projectListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                toastInfo(position + "");
                ProjectDetailActivity.start(getActivity(), false);
            }
        });
    }
}

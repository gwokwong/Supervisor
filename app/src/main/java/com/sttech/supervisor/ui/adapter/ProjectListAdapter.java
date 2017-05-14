package com.sttech.supervisor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sttech.supervisor.R;
import com.sttech.supervisor.entity.Project;
import com.sttech.supervisor.ui.widget.xrecyclerview.RecyclerAdapter;

import java.util.List;

/**
 * function:首页项目列表
 * Created by 韦国旺 on 2017/5/6.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class ProjectListAdapter extends RecyclerAdapter<Project, ProjectListAdapter.RecViewHolder> {

    public ProjectListAdapter(Context context) {
        super(context);
    }

    public ProjectListAdapter(Context context, List<Project> data) {
        super(context, data);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, final int position) {

        holder.projectTitleTv.setText(data.get(position).getTitle());
        holder.projectAddressTv.setText(data.get(position).getAddress());
        holder.projectTypeTv.setText(data.get(position).getType());
        holder.projectManagerTv.setText(data.get(position).getManager());
        holder.workRecordCountTv.setText(data.get(position).getWorkRecordCount());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null)
                    getRecItemClick().onItemClick(position, data.get(position), holder);
            }
        });


    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        TextView projectTitleTv, projectAddressTv, projectTypeTv, projectManagerTv, workRecordCountTv;

        public RecViewHolder(View itemView) {
            super(itemView);
            projectTitleTv = (TextView) itemView.findViewById(R.id.project_title);
            projectAddressTv = (TextView) itemView.findViewById(R.id.project_address);
            projectTypeTv = (TextView) itemView.findViewById(R.id.project_type);
            projectManagerTv = (TextView) itemView.findViewById(R.id.project_manager);
            workRecordCountTv = (TextView) itemView.findViewById(R.id.work_record_count);
        }
    }

}

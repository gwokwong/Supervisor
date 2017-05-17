package com.sttech.supervisor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.MessageDto;
import com.sttech.supervisor.ui.adapter.BaseRecyclerAdapter;
import com.sttech.supervisor.ui.adapter.BaseRecyclerHolder;
import com.sttech.supervisor.ui.widget.AdvanceDecoration;
import com.sttech.supervisor.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function : 我的信息
 * Created by 韦国旺 on 2017/5/3.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class MyMsgActivity extends TActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MyMsgActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_msg);
        initData();
        initView();
    }

    private List<MessageDto> messageDtoList;
    private BaseRecyclerAdapter noticeListAdapter;

    private void initData() {
        messageDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messageDtoList.add(new MessageDto("项目信息", "4月20日18:00:00", "你已被指派为xxx项目的助理，请切实做好质量检测监督工作"));
        }
    }


    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;

    private void initView() {
        initNavigation(getString(R.string.my_message));
        appBarLayout = findById(R.id.app_bar);
        noticeListAdapter = new BaseRecyclerAdapter<MessageDto>(this, messageDtoList, R.layout.item_my_msg) {

            @Override
            public void convert(BaseRecyclerHolder holder, MessageDto item, int position, boolean isScrolling) {
                holder.setText(R.id.title, item.getTitle());
                holder.setText(R.id.time, item.getCreateTime());
                holder.setText(R.id.content, item.getContent());
            }
        };
        recyclerView = findById(R.id.msg_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noticeListAdapter);
        noticeListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                toast(position + "");
            }
        });
        recyclerView.addItemDecoration(new AdvanceDecoration(this, OrientationHelper.VERTICAL, CommonUtils.dip2px(this, 8)));
    }





}

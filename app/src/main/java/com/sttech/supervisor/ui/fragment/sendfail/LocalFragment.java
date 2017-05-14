package com.sttech.supervisor.ui.fragment.sendfail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.sttech.supervisor.R;
import com.sttech.supervisor.db.LocationInfo;
import com.sttech.supervisor.ui.adapter.BaseRecyclerAdapter;
import com.sttech.supervisor.ui.adapter.BaseRecyclerHolder;
import com.sttech.supervisor.entity.NoticeMsg;
import com.sttech.supervisor.ui.fragment.TFragment;
import com.sttech.supervisor.ui.widget.AdvanceDecoration;
import com.sttech.supervisor.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * function :
 * Created by 韦国旺 on 2017/4/29.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class LocalFragment extends TFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_common_list, container, false);
        initData();
        initView(view);
        return view;
    }

    private List<NoticeMsg> noticeMsgList;
    private BaseRecyclerAdapter noticeListAdapter;

    private void initData() {
        noticeMsgList = new ArrayList<>();
        List<LocationInfo> locationInfos = new Select().from(LocationInfo.class).queryList();

//        for (int i = 0; i < 10; i++) {
//            noticeMsgList.add(new NoticeMsg("发送失败", "4月20日", "18:00:00", "位置信息发送失败"));
//        }

        for (int i = 0; i < locationInfos.size(); i++) {
            noticeMsgList.add(new NoticeMsg("发送失败",
                    DateUtils.getFormatDateTime(new Date(locationInfos.get(i).time), "MM月dd日"),
                    DateUtils.getFormatDateTime(new Date(locationInfos.get(i).time), "HH:mm:ss"),
                    "位置信息发送失败"));
        }


    }


    private RecyclerView recyclerView;

    private void initView(View view) {
        noticeListAdapter = new BaseRecyclerAdapter<NoticeMsg>(getActivity(), noticeMsgList, R.layout.item_my_msg) {

            @Override
            public void convert(BaseRecyclerHolder holder, NoticeMsg item, int position, boolean isScrolling) {
                holder.setText(R.id.type, item.getType());
                holder.setText(R.id.date, item.getDate());
                holder.setText(R.id.time, item.getTime());
                holder.setText(R.id.body, item.getBody());
            }
        };
        recyclerView = (RecyclerView) view.findViewById(R.id.common_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noticeListAdapter);
        noticeListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                toastInfo(position + "");
            }
        });
        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));


    }


}

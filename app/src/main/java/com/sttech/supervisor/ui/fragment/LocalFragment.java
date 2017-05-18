package com.sttech.supervisor.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.sttech.supervisor.R;
import com.sttech.supervisor.db.LocationInfo;
import com.sttech.supervisor.db.LocationInfo_Table;
import com.sttech.supervisor.entity.NoticeMsg;
import com.sttech.supervisor.ui.adapter.BaseRecyclerAdapter;
import com.sttech.supervisor.ui.adapter.BaseRecyclerHolder;
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
    public int provideContentViewId() {
        return R.layout.fr_common_list;
    }

    private List<NoticeMsg> noticeMsgList;
    private BaseRecyclerAdapter noticeListAdapter;

    @Override
    public void initData() {
        noticeMsgList = new ArrayList<>();
//        List<LocationInfo> locationInfos = new Select().from(LocationInfo.class).queryList();
        List<LocationInfo> locationInfos = SQLite.select()
                .from(LocationInfo.class)
                .where()
                .orderBy(LocationInfo_Table.time, false).queryList();  //false 为降序


        for (int i = 0; i < locationInfos.size(); i++) {
            noticeMsgList.add(new NoticeMsg("发送失败",
                    DateUtils.getFormatDateTime(new Date(locationInfos.get(i).time), "MM月dd日"),
                    DateUtils.getFormatDateTime(new Date(locationInfos.get(i).time), "MM月dd日 HH:mm:ss"),
                    "位置信息发送失败"));


        }
        noticeListAdapter = new BaseRecyclerAdapter<NoticeMsg>(getActivity(), noticeMsgList, R.layout.item_location_fail) {

            @Override
            public void convert(BaseRecyclerHolder holder, NoticeMsg item, int position, boolean isScrolling) {
                holder.setText(R.id.title, item.getType());
                holder.setText(R.id.time, item.getTime());
                holder.setText(R.id.content, item.getBody());
            }
        };
        recyclerView.setAdapter(noticeListAdapter);
        noticeListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                toastInfo(position + "");
            }
        });


    }


    private RecyclerView recyclerView;

    @Override
    public void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.common_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new AdvanceDecoration(getActivity(), OrientationHelper.VERTICAL, dip2px(8)));


    }


}

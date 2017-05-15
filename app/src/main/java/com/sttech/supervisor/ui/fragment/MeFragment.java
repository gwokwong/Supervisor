package com.sttech.supervisor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.logger.Logger;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.event.MeEvent;
import com.sttech.supervisor.ui.activity.EndProjectActivity;
import com.sttech.supervisor.ui.activity.MyMsgActivity;
import com.sttech.supervisor.ui.activity.SendMsgFailActivity;
import com.sttech.supervisor.ui.activity.SignInActivity;

/**
 * function :
 * Created by 韦国旺 on 2017/4/29.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class MeFragment extends TFragment implements View.OnClickListener {

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        Logger.d("重新加载数据");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_me, container, false);
        initView(view);
        return view;
    }

    /**
     * @param type
     * @param number
     */
    public void updateCount(int type, int number) {
        Logger.d("type number" + type + "|" + number);
        if (type == MeEvent.TYPE_MY_MSG) {
            if (msgCountTv != null) {
                msgCountTv.setText(String.valueOf(number));
            } else {
                Logger.d("msgCountTv is null");
            }

        } else {
            sendFailCountTv.setText(String.valueOf(number));
        }

    }

    private RoundedImageView headImg;
    private TextView nameTv, jobTv, mobilephoneTv;
    private Button logoutBtn;
    private LinearLayout msgll, endProjectll, sendErrorll;
    private TextView msgCountTv, sendFailCountTv;

    private void initView(View view) {
        headImg = (RoundedImageView) view.findViewById(R.id.me_head);
        nameTv = (TextView) view.findViewById(R.id.me_name);
        jobTv = (TextView) view.findViewById(R.id.me_job);
        mobilephoneTv = (TextView) view.findViewById(R.id.me_mobile_phone);
        logoutBtn = (Button) view.findViewById(R.id.me_logout_btn);
        msgll = (LinearLayout) view.findViewById(R.id.me_msg);
        endProjectll = (LinearLayout) view.findViewById(R.id.me_end_project);
        sendErrorll = (LinearLayout) view.findViewById(R.id.me_send_error);
        msgCountTv = (TextView) view.findViewById(R.id.me_msg_count);
        sendFailCountTv = (TextView) view.findViewById(R.id.me_send_fail_count);

//        msgCountTv.setText("1000");

        logoutBtn.setOnClickListener(this);
        msgll.setOnClickListener(this);
        endProjectll.setOnClickListener(this);
        sendErrorll.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == logoutBtn) {
            Intent intent = new Intent(getActivity(), SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            MyApp.getInstance().setLogin(false);
            getActivity().finish();
        } else if (view == msgll) {
            MyMsgActivity.start(getActivity());
        } else if (view == endProjectll) {
            EndProjectActivity.start(getActivity());
        } else if (view == sendErrorll) {
            SendMsgFailActivity.start(getActivity());
        }
    }
}

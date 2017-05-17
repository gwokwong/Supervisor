package com.sttech.supervisor.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.sttech.supervisor.MyApp;
import com.sttech.supervisor.R;
import com.sttech.supervisor.db.MobileLoginResult;
import com.sttech.supervisor.db.MobileLoginResult_Table;
import com.sttech.supervisor.event.MeEvent;
import com.sttech.supervisor.ui.activity.EndProjectActivity;
import com.sttech.supervisor.ui.activity.MyMsgActivity;
import com.sttech.supervisor.ui.activity.SendMsgFailActivity;
import com.sttech.supervisor.ui.activity.SignInActivity;

import java.util.List;

import butterknife.BindView;

/**
 * function :
 * Created by 韦国旺 on 2017/4/29.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class MeFragment extends TFragment implements View.OnClickListener {

    @Override
    public int provideContentViewId() {
        return R.layout.fr_me;
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

    @BindView(R.id.me_head)
    RoundedImageView headImg;

    @BindView(R.id.me_name)
    TextView nameTv;

    private TextView jobTv, mobilephoneTv;

    private Button logoutBtn;
    private LinearLayout msgll, endProjectll, sendErrorll;
    private TextView msgCountTv, sendFailCountTv;

    @Override
    public void initView(View view) {
//        headImg = (RoundedImageView) view.findViewById(R.id.me_head);
//        nameTv = (TextView) view.findViewById(R.id.me_name);
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

        MobileLoginResult result = new Select().from(MobileLoginResult.class).querySingle();
//        Glide.with(getActivity())
//                .load(result.getAvatarPath())
//                .asBitmap().centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into((headImg));


    }

//    public void setUserInfo(MobileLoginResult result) {
//
//        MobileLoginResult result = new Select().from(MobileLoginResult.class).querySingle();
//        Glide.with(getActivity())
//                .load(result.getAvatarPath())
//                .asBitmap().centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into((headImg));
//    }


    @Override
    public void initData() {
        MobileLoginResult result = new Select().from(MobileLoginResult.class).querySingle();
        nameTv.setText(result.getUserName());
        Glide.with(getActivity())
                .load(result.getAvatarPath())
                .asBitmap().centerCrop()
                .placeholder(R.mipmap.cloudwalk_face_head)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((headImg));
    }

    @Override
    public void onClick(View view) {
        if (view == logoutBtn) {
            List<MobileLoginResult> results = new Select().from(MobileLoginResult.class).queryList();
            String userId = results.get(0).getUserId();
//            List<Integer> uidList = new ArrayList<>();
//            SQLCondition condition = Condition.column(MobileLoginResult_Table.userId.eq(userId));

//            List<MobileLoginResult> record =  new Select()
//                    .from(MobileLoginResult.class)
//                    .where(MobileLoginResult_Table.userId.eq(MyApp.getInstance().getUserId())).queryList();
//            Logger.d("record size-->" + record.size());
//            for (MobileLoginResult mobileLoginResult : record) {
//                mobileLoginResult.delete();
//            }

            Delete.table(MobileLoginResult.class);


//            SQLCondition condition = Condition.column(MobileLoginResult_Table.userId.getNameAlias()).eq(userId);
//
//            Delete.table(MobileLoginResult.class).(condition);



            Intent intent = new Intent(getActivity(), SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

//            MyApp.getInstance().setLogin(false);
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

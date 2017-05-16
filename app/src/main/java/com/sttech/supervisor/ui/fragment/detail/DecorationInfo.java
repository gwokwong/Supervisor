package com.sttech.supervisor.ui.fragment.detail;

import android.view.View;

import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectDetailDto;
import com.sttech.supervisor.ui.fragment.TFragment;

/**
 * function : 装修信息
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class DecorationInfo extends TFragment {

    
    @Override
    public int provideContentViewId() {
        return R.layout.fr_decoration_info;
    }

    @Override
    public void initView(View rootView) {

    }

    /**
     * 项目详情信息设置
     *
     * @param detailDto
     */
    public void setProjectDetail(ProjectDetailDto detailDto) {

    }

}

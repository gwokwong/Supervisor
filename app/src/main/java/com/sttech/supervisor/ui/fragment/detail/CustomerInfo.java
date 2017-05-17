package com.sttech.supervisor.ui.fragment.detail;

import android.view.View;
import android.widget.TextView;

import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectDetailDto;
import com.sttech.supervisor.ui.fragment.TFragment;

import butterknife.BindView;

/**
 * function : 客户信息
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class CustomerInfo extends TFragment {


    @Override
    public int provideContentViewId() {
        return R.layout.fr_customer_info;
    }

    @Override
    public void initView(View rootView) {

    }
    /**
     * 公司名称
     */
    @BindView(R.id.companyName)
    TextView companyName;

    /**
     * 注册资金
     */
    @BindView(R.id.companyStartupFund)
    TextView companyStartupFund;
    /**
     * 公司法人
     */
    @BindView(R.id.companyOwner)
    TextView companyOwner;
    /**
     * 经营期限
     */
    @BindView(R.id.runTimeLabel)
    TextView runTimeLabel;
    /**
     * 股东结构
     */
    @BindView(R.id.stockStructure)
    TextView stockStructure;
    /**
     * 经营范围
     */
    @BindView(R.id.companyScopeLabel)
    TextView companyScopeLabel;
    /**
     * 企业阶段
     */
    @BindView(R.id.companyStageLabel)
    TextView companyStageLabel;
    /**
     * 企业员工
     */
    @BindView(R.id.companyManCountLabel)
    TextView companyManCountLabel;

    /**
     * 项目详情信息设置
     *
     * @param detailDto
     */
    public void setProjectDetail(ProjectDetailDto detailDto) {
        companyName.setText(detailDto.getCompanyName());
        companyStartupFund.setText(String.valueOf(detailDto.getCompanyStartupFund()));
        companyOwner.setText(detailDto.getCompanyOwner());
        runTimeLabel.setText(detailDto.getRunTimeLabel());
        stockStructure.setText(detailDto.getStockStructure());
        companyScopeLabel.setText(detailDto.getCompanyScopeLabel());
        companyStageLabel.setText(detailDto.getCompanyStageLabel());
        companyManCountLabel.setText(detailDto.getCompanyManCountLabel());
    }

}

package com.sttech.supervisor.ui.fragment.detail;

import android.view.View;
import android.widget.TextView;

import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectDetailDto;
import com.sttech.supervisor.ui.fragment.TFragment;

import butterknife.BindView;

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
     * 项目地址-全部拼接起来
     */
    @BindView(R.id.projectFullAddress)
    TextView projectFullAddress;
    /**
     * 装修类型
     */
    @BindView(R.id.decorationCategoryLabel)
    TextView decorationCategoryLabel;
    /**
     * 装修档次
     */
    @BindView(R.id.decorationLevelLabel)
    TextView decorationLevelLabel;
    /**
     * 楼盘属性
     */
    @BindView(R.id.officeBuildingLevelLabel)
    TextView officeBuildingLevelLabel;
    /**
     * 施工面积
     */
    @BindView(R.id.constructionSpaceLabel)
    TextView constructionSpaceLabel;
    /**
     * 项目需求
     */
    @BindView(R.id.projectRequirementLabel)
    TextView projectRequirementLabel;
    /**
     * 装修预算
     */
    @BindView(R.id.budgetForProjectLabel)
    TextView budgetForProjectLabel;
    /**
     * 项目周期
     */
    @BindView(R.id.projectPeriodLabel)
    TextView projectPeriodLabel;
    /**
     * 性价比
     */
    @BindView(R.id.qualityPriceRatioLabel)
    TextView qualityPriceRatioLabel;
    /**
     * 风格要求
     */
    @BindView(R.id.decorationStyleLabel)
    TextView decorationStyleLabel;
    /**
     * 设计要求
     */
    @BindView(R.id.designRequirementLabel)
    TextView designRequirementLabel;
    /**
     * 环保要求
     */
    @BindView(R.id.environmentProtectionLabel)
    TextView environmentProtectionLabel;

    /**
     * 项目详情信息设置
     *
     * @param detailDto
     */
    public void setProjectDetail(ProjectDetailDto detailDto) {
        projectFullAddress.setText(detailDto.getFullProjectAddress());
        decorationCategoryLabel.setText(detailDto.getDecorationCategoryLabel());
        decorationLevelLabel.setText(detailDto.getDecorationLevelLabel());
        officeBuildingLevelLabel.setText(detailDto.getOfficeBuildingLevelLabel());
        constructionSpaceLabel.setText(detailDto.getConstructionSpaceLabel());
        projectRequirementLabel.setText(detailDto.getProjectRequirementLabel());
        budgetForProjectLabel.setText(detailDto.getBudgetForProjectLabel());
        projectPeriodLabel.setText(detailDto.getProjectPeriodLabel());
        qualityPriceRatioLabel.setText(detailDto.getQualityPriceRatioLabel());
        decorationStyleLabel.setText(detailDto.getDecorationStyleLabel());
        designRequirementLabel.setText(detailDto.getDesignRequirementLabel());
        environmentProtectionLabel.setText(detailDto.getEnvironmentProtectionLabel());
    }

}

package com.sttech.supervisor.ui.fragment.detail;

import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ProjectDetailDto;
import com.sttech.supervisor.ui.fragment.TFragment;

import butterknife.BindView;

/**
 * function : 客户分析
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class CustomerAnalysis extends TFragment {

    @Override
    public int provideContentViewId() {
        return R.layout.fr_customer_analysis;
    }

    @Override
    public void initView(View rootView) {

    }

    /**
     * 装修次数
     */
    @BindView(R.id.decorationCountLabel)
    TextView decorationCountLabel;

    /**
     * 设计方面
     */
    @BindView(R.id.aboutDesignLabel)
    TextView aboutDesignLabel;

    /**
     * 材料方面
     */
    @BindView(R.id.aboutMaterialLabel)
    TextView aboutMaterialLabel;

    /**
     * 施工方面
     */
    @BindView(R.id.aboutConstructionLabel)
    TextView aboutConstructionLabel;

    /**
     * 流程方面
     */
    @BindView(R.id.aboutProcessLabel)
    TextView aboutProcessLabel;

    /**
     * (关于)公装网
     */
    @BindView(R.id.aboutGongzhuangLabel)
    TextView aboutGongzhuangLabel;

    /**
     * 关于装修公司
     */
    @BindView(R.id.aboutDecorationCompanyLabel)
    TextView aboutDecorationCompanyLabel;

    /**
     * 采购标准
     */
    @BindView(R.id.buyForLabel)
    TextView buyForLabel;

    /**
     * 行业说明(所属行业)
     */
    @BindView(R.id.belongToIndustry)
    TextView belongToIndustry;

    /**
     * 项目详情信息设置
     *
     * @param detailDto
     */
    public void setProjectDetail(ProjectDetailDto detailDto) {
        Logger.d("detailDto valeu ->" + detailDto.getDecorationCountLabel());
        Logger.d("detailDto decorationCountLabel is null  ->");
        decorationCountLabel.setText(detailDto.getDecorationCountLabel());
        aboutDesignLabel.setText(detailDto.getAboutDesignLabel());
        aboutMaterialLabel.setText(detailDto.getAboutMaterialLabel());
        aboutConstructionLabel.setText(detailDto.getConstructionSpaceLabel());
        aboutProcessLabel.setText(detailDto.getAboutProcessLabel());
        aboutGongzhuangLabel.setText(detailDto.getAboutGongzhuangLabel());
        aboutDecorationCompanyLabel.setText(detailDto.getAboutDecorationCompanyLabel());
        buyForLabel.setText(detailDto.getBuyForLabel());
        belongToIndustry.setText(detailDto.getBelongToIndustry());
    }

}

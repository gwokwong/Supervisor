package com.sttech.supervisor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ImageDto;
import com.sttech.supervisor.dto.ProjectAttachDto;
import com.sttech.supervisor.ui.activity.PictureExternalPreviewActivity;
import com.sttech.supervisor.entity.LocalMedia;
import com.sttech.supervisor.entity.ProjectData;
import com.sttech.supervisor.ui.activity.PicturePreviewActivity;
import com.sttech.supervisor.ui.widget.FullyGridLayoutManager;
import com.sttech.supervisor.ui.widget.xrecyclerview.RecyclerAdapter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * function : 项目资料列表
 * Created by 韦国旺 on 2017/5/5.
 * Copyright (c) 2017 All Rights Reserved.
 */

public class ProjectDataListAdapter extends RecyclerAdapter<ProjectAttachDto, ProjectDataListAdapter.RecordHolder> {


    public ProjectDataListAdapter(Context context) {
        super(context);
    }

    public ProjectDataListAdapter(Context context, List<ProjectAttachDto> data) {
        super(context, data);
    }

    private boolean isSendFailList = false;

    public void setSendFailList(boolean sendFailList) {
        isSendFailList = sendFailList;
    }


    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_record, parent, false));
    }

    public List<ImageDto> imageList;


    @Override
    public void onBindViewHolder(final RecordHolder holder, final int position) {

        ProjectAttachDto projectAttachDto = data.get(position);
        holder.nameTv.setText(projectAttachDto.getCreateUserName());

//        holder.dateTv.setText(projectAttachDto.getCreateTime());
        holder.timeTv.setText(projectAttachDto.getCreateTime());
        holder.typeTv.setText(projectAttachDto.getCategoryLabel());

//        if (isSendFailList) {  //是否是发送失败列表
//            holder.sendFailTipsTv.setVisibility(View.VISIBLE);
//            holder.sendFailTipsTv.setText(
//                    String.format(context.getResources().getString(R.string.send_fail), projectData.getSendFailTime()));  //// TODO: 2017/5/17
//        } else {
        holder.sendFailTipsTv.setVisibility(View.INVISIBLE);
//        }

        if (TextUtils.isEmpty(projectAttachDto.getContent())) {
            holder.bodyTv.setText("");
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.recyclerView.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.ll_type);
            holder.recyclerView.setLayoutParams(params); //使layout更新
        } else {
            holder.bodyTv.setText(projectAttachDto.getContent());
        }


//        String headImgUrl = projectData.getHeadImgUrl();

        String headImgUrl = projectAttachDto.getCreateUserAvatarPath();
//        if (TextUtils.isEmpty(headImgUrl)) {
//            holder.headImg.setImageResource(R.mipmap.cloudwalk_face_head);
//        } else {
        //TODO 网络加载图片
        Glide.with(context)
                .load(headImgUrl)
                .asBitmap().centerCrop()
                .placeholder(R.mipmap.cloudwalk_face_head)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.headImg);
//        }

        final List<ImageDto> imageList = projectAttachDto.getImageList();


        if (imageList != null && imageList.size() > 0) {
            ListImageDtoAdapter adapter = new ListImageDtoAdapter(context, null);
            FullyGridLayoutManager manager = new FullyGridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setAdapter(adapter);
            adapter.setList(imageList);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(new ListImageDtoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    externalPicturePreview(context, position, imageList);
                }
            });
        } else {
            //TODO test

            final List<ImageDto> imageList2 = new ArrayList<>();
            String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sttech" + File.separator;
            imageList2.add(new ImageDto("0", basePath + "1493913988930.jpg"));
            imageList2.add(new ImageDto("0", basePath + "1493867358111.jpg"));
            ListImageDtoAdapter adapter = new ListImageDtoAdapter(context, null);
            FullyGridLayoutManager manager = new FullyGridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setAdapter(adapter);
            adapter.setList(imageList2);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener(new ListImageDtoAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    externalPicturePreview(context, position, imageList2);
                }
            });


        }


//        final List<LocalMedia> selectMedia = projectData.getProjectMedias();
//
//        if (selectMedia != null && selectMedia.size() != 0) {
//
//        } else {
//            final List<LocalMedia> newLocalMedia = new ArrayList<>();
//            if ("房屋原始图".equals(projectData.getType())) {     //TODO 测试图片
//                String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sttech" + File.separator;
//                File file1 = new File(basePath + "1493913988930.jpg");
//                File file2 = new File(basePath + "1493867358111.jpg");
//                if (file1.exists() && file2.exists()) {
//                    newLocalMedia.add(new LocalMedia(basePath + "1493913988930.jpg", false));
//                    newLocalMedia.add(new LocalMedia(basePath + "1493867358111.jpg", false));
//                    // 添加数据
//                    DataGridImageAdapter adapter = new DataGridImageAdapter(context, null);
//                    FullyGridLayoutManager manager = new FullyGridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false);
//                    holder.recyclerView.setLayoutManager(manager);
//                    holder.recyclerView.setAdapter(adapter);
//                    adapter.setList(newLocalMedia);
//                    adapter.notifyDataSetChanged();
//                    adapter.setOnItemClickListener(new DataGridImageAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(int position, View v) {
//                            // 这里可预览图片
//                            externalPicturePreview(context, position, newLocalMedia);
//                        }
//                    });
//                }
//
//            }
//
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null)
                    getRecItemClick().onItemClick(position, data.get(position), holder);
            }
        });

    }

    public class RecordHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private ImageView headImg;
        private TextView nameTv;
        private TextView typeTv;
        private TextView dateTv;
        private TextView timeTv;
        private TextView bodyTv;

        private TextView sendFailTipsTv;

        public RecordHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.record_rv);
            headImg = (ImageView) itemView.findViewById(R.id.head);
            nameTv = (TextView) itemView.findViewById(R.id.name);
            typeTv = (TextView) itemView.findViewById(R.id.type);
            dateTv = (TextView) itemView.findViewById(R.id.date);
            timeTv = (TextView) itemView.findViewById(R.id.time);
            bodyTv = (TextView) itemView.findViewById(R.id.body);
            sendFailTipsTv = (TextView) itemView.findViewById(R.id.send_fail_tips);
        }
    }
//
//    /**
//     * 外部图片预览
//     *
//     * @param position
//     * @param medias
//     */
//    public void externalPicturePreview(Context mContext, int position, List<LocalMedia> medias) {
//        if (medias != null && medias.size() > 0) {
//            Intent intent = new Intent();
//            intent.putExtra(Constant.EXTRA_PREVIEW_SELECT_LIST, (Serializable) medias);
//            intent.putExtra(Constant.EXTRA_PREVIEW_POSITION, position);
//            intent.setClass(mContext, PictureExternalPreviewActivity.class);
//            mContext.startActivity(intent);
//            ((Activity) mContext).overridePendingTransition(R.anim.toast_enter, 0);
//        }
//    }


    /**
     * 外部图片预览
     *
     * @param position
     * @param imageDtos
     */
    public void externalPicturePreview(Context mContext, int position, List<ImageDto> imageDtos) {
        if (imageDtos != null && imageDtos.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_PREVIEW_SELECT_LIST, (Serializable) imageDtos);
            intent.putExtra(Constant.EXTRA_PREVIEW_POSITION, position);
            intent.setClass(mContext, PicturePreviewActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.toast_enter, 0);
        }
    }


}

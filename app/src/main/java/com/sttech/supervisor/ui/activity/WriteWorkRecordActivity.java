package com.sttech.supervisor.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.adapter.GridImageAdapter;
import com.sttech.supervisor.entity.LocalMedia;
import com.sttech.supervisor.ui.widget.FullyGridLayoutManager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * function : 填写工作记录
 * Created by 韦国旺 on 2017/5/3.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class WriteWorkRecordActivity extends TActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, WriteWorkRecordActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_write_work_record);
        initView();
    }

    private Button takePhotoBtn;
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int maxSelectNum = 12;

    private void initView() {
        takePhotoBtn = findById(R.id.take_photo);
        takePhotoBtn.setOnClickListener(this);
        initNavigation(getString(R.string.write_work_record), null, getString(R.string.save), 0, 0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewProfile();
            }

        });


        recyclerView = findById(R.id.list_rv);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(WriteWorkRecordActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(WriteWorkRecordActivity.this, onAddPicClickListener);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

        adapter.setList(selectMedia);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                externalPicturePreview(WriteWorkRecordActivity.this, position, selectMedia);
            }
        });
    }

    /**
     * 外部图片预览
     *
     * @param position
     * @param medias
     */
    public void externalPicturePreview(Context mContext, int position, List<LocalMedia> medias) {
        if (medias != null && medias.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_PREVIEW_SELECT_LIST, (Serializable) medias);
            intent.putExtra(Constant.EXTRA_PREVIEW_POSITION, position);
            intent.setClass(mContext, PictureExternalPreviewActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.toast_enter, 0);
        }
    }


    private List<LocalMedia> selectMedia = new ArrayList<>();

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    takePhoto();
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO_REQ) {
                String fileName = data.getStringExtra("picName");
                Logger.d(fileName);
                String myJpgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sttech" + File.separator + fileName;
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2;
//                Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
                selectMedia.add(new LocalMedia(myJpgPath));
                adapter.notifyDataSetChanged();


            }

        }
    }

    private void saveNewProfile() {
        ErrorPromptActivity.start(this);
    }

    @Override
    public void onClick(View view) {
        takePhoto();
    }

    private static final int TAKE_PHOTO_REQ = 100;

    public void takePhoto() {
        startActivityForResult(new Intent(WriteWorkRecordActivity.this, TakePhotoActivity.class), TAKE_PHOTO_REQ);
    }
}

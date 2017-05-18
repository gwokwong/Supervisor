package com.sttech.supervisor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ImageDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：LeTuGolf
 * package：com.tongyu.luck.paradisegolf.adapter
 * email：893855882@qq.com
 * data：16/7/27
 */
public class ListImageDtoAdapter extends RecyclerView.Adapter<ListImageDtoAdapter.ViewHolder> {
    public final int TYPE_CAMERA = 1;
    public final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<ImageDto> list = new ArrayList<>();
    private int selectMax = 9;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;



    public interface onAddPicClickListener {
        void onAddPicClick(int type, int position);
    }

    public ListImageDtoAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<ImageDto> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
        }
    }

    @Override
    public int getItemCount() {
//        if (list.size() < selectMax) {
//            return list.size() + 1;
//        } else {
        return list.size();
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_grid_image,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(viewHolder.getAdapterPosition(), v);
                }
            });
        }
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //少于8张，显示继续添加的图标
//        if (getItemViewType(position) == TYPE_CAMERA) {
//            Logger.d("TYPE_CAMERA");
//            viewHolder.mImg.setImageResource(R.mipmap.addimg_1x);
//            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnAddPicClickListener.onAddPicClick(0, viewHolder.getAdapterPosition());
//                }
//            });
        viewHolder.ll_del.setVisibility(View.GONE);
//        } else {
//        Logger.d("NO TYPE_CAMERA");
//            viewHolder.ll_del.setVisibility(View.VISIBLE);
//            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnAddPicClickListener.onAddPicClick(1, viewHolder.getAdapterPosition());
//                }
//            });
        ImageDto imageDto = list.get(position);

        Glide.with(mContext)
                .load(imageDto.getPath())
                .asBitmap().centerCrop()
                .placeholder(R.mipmap.default_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.mImg);
//        LocalMedia media = list.get(position);
//        int type = media.getType();
//        String path = "";
//        if (media.isCut() && !media.isCompressed()) {
//            // 裁剪过
//            path = media.getCutPath();
//        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
//            path = media.getCompressPath();
//        } else {
//            // 原图
//            path = media.getPath();
//        }
//
//        if (media.isNetWorkImg()) {
//            Glide.with(mContext).load(new File(path))
//                    .into(viewHolder.mImg);
//        } else {
//            //Glide 加载网络图片
//            Glide.with(mContext)
//                    .load(path)
//                    .asBitmap().centerCrop()
//                    .placeholder(R.mipmap.default_img)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(viewHolder.mImg);
//
//        }


//            switch (type) {
//                case 1:
//                    // 图片
//                    if (media.isCompressed()) {
//                        Log.i("compress image result", new File(media.getCompressPath()).length() / 1024 + "k");
//                    }
//
//                    Glide.with(mContext)
//                            .load(path)
//                            .asBitmap().centerCrop()
//                            .placeholder(R.color.color_f6)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
////                            .into(viewHolder.mImg);
//                    Logger.d("local path is "+path);
//
//                    break;
//                case 2:
//                    // 视频
//                    Glide.with(mContext).load(path).thumbnail(0.5f).into(viewHolder.mImg);
//                    break;
//                default:
//                    break;
//            }

//        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}

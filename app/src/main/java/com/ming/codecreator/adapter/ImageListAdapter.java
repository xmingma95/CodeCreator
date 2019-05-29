package com.ming.codecreator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ming.codecreator.R;
import com.ming.codecreator.bean.ImageBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author ming
 * @version $Rev$
 * @des ${TODO}
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private ArrayList<ImageBean> imageList;
    private Context context;
    private ItemClickListener mItemClickListener ;
    public interface ItemClickListener{
        public void onItemClick(String path,Context context) ;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener ;

    }
    public ImageListAdapter(Context context,ArrayList<ImageBean> imageList){
        this.context=context;
        this.imageList=imageList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.image_recycle_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Date date=new Date(imageList.get(i).getDate());
        String s= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss",Locale.getDefault()).format(date);
        viewHolder.tv_name_item.setText(s);
        Glide.with(context)
                .load(imageList.get(i).getPath())
                .placeholder(R.mipmap.image_default)
                .into(viewHolder.iv_image_item);

        if(mItemClickListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(imageList.get(i).getPath(),context);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_image_item;
        TextView tv_name_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image_item=itemView.findViewById(R.id.iv_image_item);
            tv_name_item=itemView.findViewById(R.id.tv_name_item);
        }
    }
}

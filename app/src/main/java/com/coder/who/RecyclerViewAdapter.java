package com.coder.who;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by QiWangming on 2016/1/16.
 * Blog: www.jycoder.com
 * GitHub: msAndroid
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    //接口处理点击事件
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int pos);
    }

    private List<Item> items;
    private Context context;

    private  OnRecyclerViewItemClickListener listener = null;


    public RecyclerViewAdapter(List<Item> newses,Context context) {
        this.items = newses;
        this.context=context;
    }


    //自定义ViewHolder类
    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cardView;
        ImageView item_bgImage;
        ImageView item_hrImage;
        TextView item_desc;

        private  OnRecyclerViewItemClickListener listener = null;

        public ItemViewHolder(final View itemView,OnRecyclerViewItemClickListener listener) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_item);
            item_bgImage= (ImageView) itemView.findViewById(R.id.item_top);
            item_hrImage= (ImageView) itemView.findViewById(R.id.item_bottom_header);
            item_desc= (TextView) itemView.findViewById(R.id.item_bottom_desc);
            this.listener = listener;

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onItemClick(v,getPosition());
            }
        }


    }
    @Override
    public RecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.layout_item,viewGroup,false);
        ItemViewHolder nvh=new ItemViewHolder(v,listener);
        return nvh;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ItemViewHolder choiceViewHolder, int i) {
        final int j = i;
        // 如果设置了回调，则设置点击事件

        choiceViewHolder.item_bgImage.setImageResource(items.get(i).getBgImage());
        choiceViewHolder.item_hrImage.setImageResource(items.get(i).getHrImage());
        choiceViewHolder.item_desc.setText(items.get(i).getDesc());

        choiceViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(v,j);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
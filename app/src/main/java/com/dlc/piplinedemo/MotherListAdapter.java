package com.dlc.piplinedemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mbadr on 2/5/2018.
 */

public class MotherListAdapter extends RecyclerView.Adapter<MotherListAdapter.ItemRowHolder> implements ItemTouchHelperAdapter{

    Context context;

    ArrayList<ArrayList<String>> mItems;

    public MotherListAdapter(Context context,ArrayList<ArrayList<String>> list) {
        this.context = context;
        mItems = list;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20,20,20,20);
        v.setLayoutParams(lp);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {

        holder.title.setText("List " + (position + 1));
        holder.childList.setHasFixedSize(true);
        holder.childList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        ChildListAdapter adapter = new ChildListAdapter(context,mItems.get(position));
        holder.childList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder{
        TextView title;
        RecyclerView childList;
        public ItemRowHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_title);
            childList = itemView.findViewById(R.id.list);
        }
    }
}

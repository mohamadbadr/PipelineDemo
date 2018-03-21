package com.dlc.piplinedemo;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mbadr on 2/5/2018.
 */

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ItemRowHolder>{

    Context context;
    ArrayList<String> mItems;

    public ChildListAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.mItems = items;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20,20,20,20);
        v.setLayoutParams(lp);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {
        holder.text.setText(mItems.get(position));
        holder.itemView.setOnLongClickListener(new MyOnLongClickListener());
        holder.itemView.setTag(position);
        holder.itemView.setOnDragListener(new MyDragListener());

//        setAnimation(holder.itemView,position,-1);
    }

    int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int from,int to)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (from> to)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
//            animation.setDuration(3000);
            viewToAnimate.startAnimation(animation);
            lastPosition = to;
        }
    }

    private class MyOnLongClickListener implements View.OnLongClickListener
    {

        @Override
        public boolean onLongClick(View view) {
//            view.setVisibility(View.INVISIBLE);
            ClipData clipData = ClipData.newPlainText("","");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(clipData,shadowBuilder,view,0);
            }
            else
            {
                view.startDrag(clipData,shadowBuilder,view,0);
            }
            return true;
        }
    }


    private class MyDragListener implements View.OnDragListener
    {

        @Override
        public boolean onDrag(View destCard, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            View draggedCard;
            switch (dragEvent.getAction())
            {
                case DragEvent.ACTION_DRAG_ENTERED:
                    draggedCard = (View) dragEvent.getLocalState();
                    draggedCard.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedCard = (View) dragEvent.getLocalState();
                    draggedCard.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    draggedCard = (View) dragEvent.getLocalState();
                    draggedCard.setVisibility(View.VISIBLE);
                    int  itemOldPosition = (int) draggedCard.getTag();
                    int itemNewPosition = (int) destCard.getTag();
                    Log.i("view_tobe_replaced",draggedCard.getParent().toString());
                    RecyclerView oldRecyclerView = (RecyclerView) draggedCard.getParent();
                    ChildListAdapter oldAdapter = (ChildListAdapter) oldRecyclerView.getAdapter();
                    String data = oldAdapter.mItems.get(itemOldPosition);
                    oldAdapter.mItems.remove(itemOldPosition);

                    RecyclerView newRecyclerView = (RecyclerView) destCard.getParent();
                    ChildListAdapter newAdapter = (ChildListAdapter) newRecyclerView.getAdapter();
                    newAdapter.mItems.add(itemNewPosition,data);

                    oldAdapter.notifyDataSetChanged();
                    newAdapter.notifyDataSetChanged();
                    Log.i("data",mItems.toString());
                    break;
            }
            return true;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder{
        TextView text;

        public ItemRowHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}

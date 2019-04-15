package com.bysj.lizhunan.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.SuperViewHolder> {


    @NonNull
    @Override
    public SuperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SuperViewHolder superViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SuperViewHolder extends RecyclerView.ViewHolder {

        public SuperViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

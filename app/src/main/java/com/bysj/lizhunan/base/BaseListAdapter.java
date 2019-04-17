package com.bysj.lizhunan.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    private LayoutInflater mInflater;
    protected List<T> mDataList = new ArrayList<>();

    protected OnItemClickListener<T> mOnItemClickListener;

    public OnItemClickListener<T> getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public BaseListAdapter(Context mContext){
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(getLayoutId(), viewGroup, false);
      //  ButterKnife.bind(this, itemView);
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( BaseViewHolder viewHolder, int i) {
        onBindItemHolder(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        } else {
            return 0;
        }
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void addAll(Collection<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param orgs
     */
    public void updateListView(List<T> orgs) {
        if (orgs == null) {
            this.mDataList = new ArrayList<T>();
        } else {
            this.mDataList = orgs;
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public abstract int getLayoutId();
    public abstract void onBindItemHolder(BaseViewHolder holder, int position);

    public interface OnItemClickListener<T> {
        void onItemClick(T t, int position);
    }
}

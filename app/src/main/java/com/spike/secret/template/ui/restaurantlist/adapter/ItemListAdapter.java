package com.spike.secret.template.ui.restaurantlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spike.secret.template.R;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.utils.PhotoManager;

import java.util.List;

/**
 * Created by Shyam on 2/5/17.
 */

public class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = ItemListAdapter.class.getSimpleName();

    private final int VIEW_TYPE_RESTAURANT = 0;
    private final int VIEW_TYPE_PROGRESS_ITEM = 1;


    private List<Restaurant> mDataSet;

    private Context mCtx;

    private ListListener listener;

    public ItemListAdapter(List<Restaurant> pItems, ListListener listener, Context ctx) {
        mDataSet = pItems;
        mCtx = ctx.getApplicationContext();
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_RESTAURANT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(v);
        } else if (viewType == VIEW_TYPE_PROGRESS_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);
            return new ProgressItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Restaurant currentItem = mDataSet.get(position);
            itemViewHolder.mDetails.setText(currentItem.getAddress().getPrintable_address());
            itemViewHolder.mName.setText(currentItem.getName());
            PhotoManager.getInstance().getItemImage(currentItem.getCoverImage(), itemViewHolder.mThumb);
        } else if (holder instanceof ProgressItemViewHolder) {
            ProgressItemViewHolder loadingViewHolder = (ProgressItemViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        Restaurant currentItem = mDataSet.get(position);
        return currentItem == null ? VIEW_TYPE_PROGRESS_ITEM : VIEW_TYPE_RESTAURANT;
    }

    /**
     * ViewHolder for the progress item
     */
    public static class ProgressItemViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressItemViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    /**
     * ViewHolder for the  Item
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mName;
        public TextView mDetails;
        public ImageView mThumb;

        public ItemViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.itemName);
            mDetails = (TextView) v.findViewById(R.id.itemDate);
            mThumb = (ImageView) v.findViewById(R.id.itemThumbnail);
            v.setOnClickListener(this);
            v.setClickable(true);
            v.setLongClickable(true);
        }

        @Override
        public void onClick(View v) {
            listener.itemClicked(v, getAdapterPosition());
        }

    }

}

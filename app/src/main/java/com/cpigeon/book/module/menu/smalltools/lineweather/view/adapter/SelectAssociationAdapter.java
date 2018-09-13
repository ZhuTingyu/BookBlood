package com.cpigeon.book.module.menu.smalltools.lineweather.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpigeon.book.R;
import com.cpigeon.book.module.menu.smalltools.lineweather.model.bean.ContactModel2;
import com.jiang.android.lib.adapter.BaseAdapter;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */
public class SelectAssociationAdapter extends BaseAdapter<ContactModel2.MembersEntity2, SelectAssociationAdapter.ContactViewHolders>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, IndexAdapter {

    private List<ContactModel2.MembersEntity2> mLists;
    private Context mContext;

    public SelectAssociationAdapter(Context ct, List<ContactModel2.MembersEntity2> mLists) {
        this.mLists = mLists;
        mContext = ct;
        this.addAll(mLists);
    }

    @Override
    public void addAll(Collection<ContactModel2.MembersEntity2> collection) {
        super.addAll(collection);
        this.mLists = (List<ContactModel2.MembersEntity2>) collection;
    }

    @Override
    public ContactViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_gp_coordinate_info, viewGroup, false);
        return new ContactViewHolders(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolders contactViewHolders, int position) {
        try {
            ContactModel2.MembersEntity2 entity = getItem(position);
            contactViewHolders.mName.setText(entity.getUsername());
            contactViewHolders.item_llz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EventBus.getDefault().post(entity);
                    ((Activity) mContext).finish();
                    Log.d("xiaohls", "onEventMainThread:-----------11s ");

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getSortLetters().charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_header2, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TextView textView = (TextView) viewHolder.itemView;
        String showValue = String.valueOf(getItem(position).getSortLetters().charAt(0));
        textView.setText(showValue);
    }

    public class ContactViewHolders extends RecyclerView.ViewHolder {
        public TextView mName;
        public LinearLayout item_llz;

        public ContactViewHolders(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_gp_name);
            item_llz = (LinearLayout) itemView.findViewById(R.id.item_llz);
        }
    }
}



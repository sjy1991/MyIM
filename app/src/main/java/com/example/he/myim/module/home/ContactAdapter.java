package com.example.he.myim.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.he.myim.R;

import java.util.List;

/**
 * Created by je on 06/05/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mContactList;
    private Context mContext;
    private final LayoutInflater mInflater;

    public ContactAdapter(List<String> contactList, Context context) {
        mContactList = contactList;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_contact_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactHolder contactHolder = (ContactHolder) holder;
        String s = mContactList.get(position);
        if (position == 0) {
            contactHolder.tv_contact.setText(s);
            contactHolder.tv_init.setText(s.substring(0, 1));
        } else {
            String prefist = mContactList.get(position - 1);
            if (s.equals(prefist)) {
                contactHolder.tv_contact.setText(s);
                contactHolder.tv_init.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mContactList == null ? 0 : mContactList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        TextView tv_init;
        TextView tv_contact;

        public ContactHolder(View itemView) {
            super(itemView);
            tv_init = (TextView) itemView.findViewById(R.id.tv_init);
            tv_contact = (TextView) itemView.findViewById(R.id.tv_contact);
        }
    }
}
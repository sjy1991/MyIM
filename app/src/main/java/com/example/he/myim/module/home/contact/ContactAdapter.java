package com.example.he.myim.module.home.contact;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.he.myim.R;
import com.example.he.myim.module.interfaze.IContactable;
import com.example.he.myim.utils.StringUtils;

import java.util.List;

/**
 * Created by je on 06/05/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IContactable {
    private List<String> mContactList;
    private Context mContext;
    private final LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ContactHolder contactHolder = (ContactHolder) holder;
        final String contact = mContactList.get(position);
        String initial = StringUtils.getInitial(contact);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemLongClick(contact, position);
                }
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(contact, position);
                }
            }
        });
        if (position == 0) {
            contactHolder.tv_contact.setText(contact);
            contactHolder.tv_init.setText(initial);
        } else {
            String preFirst = StringUtils.getInitial(mContactList.get(position - 1));
            if (initial.equalsIgnoreCase(preFirst)) {
                contactHolder.tv_contact.setText(contact);
                contactHolder.tv_init.setVisibility(View.GONE);
            }else {
                contactHolder.tv_contact.setText(contact);
                contactHolder.tv_init.setText(StringUtils.getInitial(contact));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mContactList == null ? 0 : mContactList.size();
    }


    @Override
    public List<String> getDatas() {
        return mContactList;
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

    public interface OnItemClickListener{
        void onItemLongClick(String contact, int position);
        void onItemClick(String contact, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        if (onItemClickListener != null) {
            mOnItemClickListener = onItemClickListener;
        }
    }






}

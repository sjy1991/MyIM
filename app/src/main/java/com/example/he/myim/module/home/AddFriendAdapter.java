package com.example.he.myim.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.he.myim.R;
import com.example.he.myim.evenbus.User;

import java.util.List;

/**
 * Created by je on 17/05/17.
 */

public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    public void setContactList(List<String> contactList) {
        mContactList = contactList;
    }

    List<User> mUserList;
    Context mContext;
    List<String> mContactList;

    public AddFriendAdapter(List<User> userList, Context context, List<String> contactList) {
        mUserList = userList;
        mContext = context;
        mContactList = contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_search, parent, false);
        return new AddFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddFriendViewHolder viewHolder = (AddFriendViewHolder) holder;
        User user = mUserList.get(position);
        if (mContactList.contains(user.userName)) {
            viewHolder.btn_add.setText("已添加");
            viewHolder.btn_add.setEnabled(false);
        }else {
            viewHolder.btn_add.setText("添加");
        }
        viewHolder.tv_date.setText(user.date);
        viewHolder.tv_username.setText(user.userName);

    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class AddFriendViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username;
        TextView tv_date;
        Button btn_add;

        public AddFriendViewHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_date = (TextView) itemView.findViewById(R.id.tv_time);
            btn_add = (Button) itemView.findViewById(R.id.btn_add);
        }
    }

    public void clean(){
        mContactList.clear();
        mUserList.clear();
    }

}

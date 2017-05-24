package com.example.he.myim.module.home.conversation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.he.myim.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by he on 17-5-23.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationViewHolder> {

    List<EMConversation> mConversations = new ArrayList<>();

    public ConversationAdapter(List<EMConversation> conversations) {
        mConversations = conversations;
    }


    @Override
    public ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationViewHolder holder, int position) {
        // 排序 倒序
        Collections.sort(mConversations, new Comparator<EMConversation>() {
            @Override
            public int compare(EMConversation o1, EMConversation o2) {
                return (int) (o2.getLastMessage().getMsgTime()-o1.getLastMessage().getMsgTime());
            }
        });

        EMConversation emConversation = mConversations.get(position);
        int unreadMsgCount = emConversation.getUnreadMsgCount();
        EMMessage lastMessage = emConversation.getLastMessage();
        EMTextMessageBody body = (EMTextMessageBody) lastMessage.getBody();
        holder.mTv_username.setText(lastMessage.getUserName());
        holder.mTv_msg.setText(body.getMessage());
        holder.mTv_time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
        if (unreadMsgCount > 99) {
            holder.mTv_unread.setVisibility(View.VISIBLE);
            holder.mTv_unread.setText("99+");
        }else if (unreadMsgCount > 0){
            holder.mTv_unread.setVisibility(View.VISIBLE);
            holder.mTv_unread.setText(unreadMsgCount+"");
        }else {
            holder.mTv_unread.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mConversations == null ? 0 : mConversations.size();
    }


}

class ConversationViewHolder extends RecyclerView.ViewHolder {

    TextView mTv_unread;
    TextView mTv_username;
    TextView mTv_msg;
    TextView mTv_time;

    public ConversationViewHolder(View itemView) {
        super(itemView);
        mTv_time = (TextView) itemView.findViewById(R.id.tv_time);
        mTv_msg = (TextView) itemView.findViewById(R.id.tv_msg);
        mTv_username = (TextView) itemView.findViewById(R.id.tv_username);
        mTv_unread = (TextView) itemView.findViewById(R.id.tv_unread);
    }
}

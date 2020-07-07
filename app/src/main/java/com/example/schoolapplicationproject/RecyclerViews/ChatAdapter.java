package com.example.schoolapplicationproject.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapplicationproject.Model.Chat;
import com.example.schoolapplicationproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    static int MY_MESSAGE = 1;
    static int OTHER_MESSAGES = 2;

    String currentUserId;

    ArrayList<Chat> messages;
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a, dd-MM-yy");

    public ChatAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatBubble;

        if (viewType == MY_MESSAGE) {
            chatBubble = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_me, parent, false);
        } else {
            chatBubble = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_with_other, parent, false);
        }

        return new ChatHolder(chatBubble);
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if (messages.get(position).getSenderId().equals(currentUserId)) {
            return MY_MESSAGE;
        } else {
            return OTHER_MESSAGES;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        holder.bindView(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setData(ArrayList<Chat> messages) {
        this.messages = messages;
        notifyDataSetChanged();
        /*if(!messages.isEmpty()) {
            this.messages.addAll(messages);
            notifyItemRangeInserted(this.messages.size() - messages.size(), this.messages.size());
        }*/
    }

    class ChatHolder extends RecyclerView.ViewHolder {
        TextView mMessageTextView;
        TextView mDateSentTextView;
        TextView mSendernameTextView;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            mMessageTextView = itemView.findViewById(R.id.message_tv);
            mDateSentTextView = itemView.findViewById(R.id.dateSent_tv);
            mSendernameTextView = itemView.findViewById(R.id.sender_name_tv);
        }

        public void bindView(Chat message) {
            mSendernameTextView.setText(message.getSenderName());
            mMessageTextView.setText(message.getText());
            if (message.getDateSent() != null) {
                mDateSentTextView.setText(format.format(message.getDateSent()));
            } else {
                mDateSentTextView.setText(format.format(new Date()));
            }
        }
    }
}

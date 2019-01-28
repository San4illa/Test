package com.example.test.ui.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.data.model.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    private static final int RECEIVED_MESSAGE = 0;
    private static final int SENDED_MESSAGE = 1;

    private ArrayList<Message> messages;

    MessageAdapter(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case RECEIVED_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message_list_item, parent, false);
                break;
            case SENDED_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sended_message_list_item, parent, false);
                break;
            default:
                return null;

        }

        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.messageTextView.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        boolean isMyMessage = messages.get(position).isMyMessage();

        if (isMyMessage) {
            return SENDED_MESSAGE;
        } else {
            return RECEIVED_MESSAGE;
        }
    }

    class MessageAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView messageTextView;

        MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.tv_message);
        }
    }
}

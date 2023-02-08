package com.example.sayali_branch_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sayali_branch_project.models.Message;

import java.util.List;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewAdapter.RecyclerHolder>{
    List<Message> messages;
    String agent_id;
    public ChatViewAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_user, parent, false);
            return new ChatViewAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        agent_id = messages.get(position).agentId;
        if(agent_id == null){
            holder.userId.setText(messages.get(position).userId);
        }else{
            holder.userId.setText(messages.get(position).agentId);
        }
        holder.timestamp.setText(messages.get(position).timestamp);
        holder.msgBody.setText(messages.get(position).body);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView userId, timestamp, msgBody;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.message_user);
            timestamp = itemView.findViewById(R.id.message_time);
            msgBody = itemView.findViewById(R.id.message_text);
        }
    }
}

package com.example.sayali_branch_project;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sayali_branch_project.models.Message;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewAdapter.RecyclerHolder> {

    List<Message> messages;
    ArrayList<String> params = new ArrayList<>();
    String authToken;
    SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public MessageViewAdapter(List<Message> messages, String authToken) {
        this.messages = messages;
        this.authToken = authToken;
    }

    @NotNull
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_of_messages, viewGroup, false);
        return new RecyclerHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerHolder recyclerHolder, int i) {
        recyclerHolder.userId.setText(messages.get(i).userId);
        recyclerHolder.msgBody.setText(messages.get(i).body);
        recyclerHolder.timestamp.setText(messages.get(i).timestamp);

        recyclerHolder.layout.setOnClickListener(v -> {
            params.add(0, String.valueOf(messages.get(i).threadId));
            params.add(1,authToken);
            System.out.println(params.get(0));

            v.getContext().startActivity(new Intent(v.getContext(),ChatActivity.class).putStringArrayListExtra("data", params));

        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView userId, msgBody, timestamp;
        ConstraintLayout layout;

        public RecyclerHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id);
            msgBody = itemView.findViewById(R.id.msgbody);
            timestamp = itemView.findViewById(R.id.timestamp);
            layout = itemView.findViewById(R.id.layout);

            itemView.setOnTouchListener((v, event) -> {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    v.setBackgroundColor(Color.GRAY);
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    v.setBackgroundColor(Color.TRANSPARENT);
                }
                return false;
            });
        }
    }
}

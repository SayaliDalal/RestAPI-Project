package com.example.sayali_branch_project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sayali_branch_project.models.Message;
import com.example.sayali_branch_project.models.SendMessage;
import com.example.sayali_branch_project.services.APIClient;
import com.example.sayali_branch_project.services.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText msgBody;
    private Button sendBtn;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<String> ar1=getIntent().getExtras().getStringArrayList("data");
        int threadId = Integer.parseInt(ar1.get(0));
        String authToken = ar1.get(1);

        recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        msgBody = findViewById(R.id.editWriteMessage);
        sendBtn = findViewById(R.id.btnSend);
        List<Message> messageList = new ArrayList<>();

        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<List<Message>> msgApiCall = apiInterface.getMessages(authToken);
        msgApiCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NotNull Call<List<Message>> call, @NotNull Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> msg = response.body();
                    for(int j=0;j<msg.size();j++){
                        if(msg.get(j).threadId == threadId){
                            messageList.add(msg.get(j));
                        }
                    }
                    Collections.reverse(messageList);
                    ChatViewAdapter chatViewAdapter = new ChatViewAdapter(messageList);
                    recyclerView.setAdapter(chatViewAdapter);
                } else{
                    Toast.makeText(ChatActivity.this, "No Messages", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Message>> call, @NotNull Throwable t) {
                Toast.makeText(ChatActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        msgBody.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
            sendBtn.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(msgBody.getText().toString())){
                    SendMessage message = new SendMessage(threadId, msgBody.getText().toString());
                    Call<Message> sendMsgApiCall = apiInterface.sendMessage(message, authToken);
                    sendMsgApiCall.enqueue(new Callback<Message>() {

                        @Override
                        public void onResponse(@NotNull Call<Message> call, @NotNull Response<Message> response) {
                            if (response.isSuccessful()) {
                                messageList.add(response.body());
                                ChatViewAdapter chatViewAdapter = new ChatViewAdapter(messageList);
                                recyclerView.setAdapter(chatViewAdapter);
                                msgBody.setText("");
                                sendBtn.setEnabled(false);
                            }else{
                                Toast.makeText(ChatActivity.this, "Message sending failed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<Message> call, @NotNull Throwable t) {
                            Toast.makeText(ChatActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
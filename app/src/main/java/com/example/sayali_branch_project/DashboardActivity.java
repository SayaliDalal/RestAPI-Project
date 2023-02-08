package com.example.sayali_branch_project;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sayali_branch_project.models.Message;
import com.example.sayali_branch_project.services.APIClient;
import com.example.sayali_branch_project.services.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        apiInterface = APIClient.getClient().create(APIInterface.class);
        String authToken = getIntent().getStringExtra("data");
        Call<List<Message>> msgApiCall = apiInterface.getMessages(authToken);
        msgApiCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NotNull Call<List<Message>> call, @NotNull Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    List<Message> msg = response.body();
                    MessageViewAdapter messageViewAdapter = new MessageViewAdapter(msg,authToken);
                    recyclerView.setAdapter(messageViewAdapter);
                }else{
                    Toast.makeText(DashboardActivity.this, "No Messages", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Message>> call, @NotNull Throwable t) {
                Toast.makeText(DashboardActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}


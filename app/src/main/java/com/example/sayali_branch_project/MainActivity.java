package com.example.sayali_branch_project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sayali_branch_project.models.LoginRequest;
import com.example.sayali_branch_project.models.LoginResponse;
import com.example.sayali_branch_project.services.APIClient;
import com.example.sayali_branch_project.services.APIInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText username,password;
    private Dialog loadingDialog;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        loginBtn.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())) {
                loadingDialog.show();
                LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
                Call<LoginResponse> loginApiCall = apiInterface.login(request);
                loginApiCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            loadingDialog.dismiss();
                            LoginResponse authToken = response.body();
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class).putExtra("data", authToken.getAuthToken()));
//                        },5);

                        } else {
                            loadingDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
            else{
                if(TextUtils.isEmpty(username.getText().toString()) && TextUtils.isEmpty(password.getText().toString())){
                    username.setError("Enter the Username");
                    password.setError("Enter the Password");
                }
                else if(TextUtils.isEmpty(password.getText().toString()))
                    password.setError("Enter the Password");
                else
                    username.setError("Enter the Username");

            }
        });
    }
}
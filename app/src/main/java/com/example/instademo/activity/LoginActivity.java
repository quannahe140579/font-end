package com.example.instademo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.UserDTO;
import com.example.instademo.mapper.UserMapper;
import com.example.instademo.model.LoginForm;
import com.example.instademo.model.User;
import com.example.instademo.utils.ErrorMessage;
import com.example.instademo.utils.LogedUser;
import com.example.instademo.utils.Validation;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserName, edPassword;
    private ImageView imgShowPass;
    private Button btnLogin;
    private UserDTO userDTO;
    LogedUser logedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btnLogin = findViewById(R.id.btnLogIn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUserName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String result = Validation.validateLoginInputs(username,password);
                if(!"".equals(result)){
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginForm form = new LoginForm(username, password);
                ApiService.apiService.getUser(form).enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        userDTO = response.body();
                        if (userDTO != null) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            User u = UserMapper._toModel(userDTO);
                            logedUser = new LogedUser(u);
                            LogedUser.listPost = null;
                            startActivity(intent);
                            return;
                        }
                        Toast.makeText(LoginActivity.this, "Incorrect account, please input again !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, ErrorMessage.ERROR_SERVICE_CONNECT, Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });
    }

}
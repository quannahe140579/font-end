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
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserName, edPassword;
    private ImageView imgShowPass;
    private Button btnLogin;
    private UserDTO userDTO;

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
                //startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                String username = edUserName.getText().toString();
                String password = edPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please input account !", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginForm form = new LoginForm(username, password);
                readUser();
                if(userDTO != null){
                    Intent intent = new Intent(LoginActivity.this,EditProfileActivity.class);
                    User u = UserMapper._toModel(userDTO);
                    User u1 = u;
                    //intent.putExtra("u",u1);
                    startActivity(intent);
                }

//                ApiService.apiService.getUser(form).enqueue(new Callback<UserDTO>() {
//                    @Override
//                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
//                        userDTO = response.body();
//                        if (userDTO != null) {
//                            saveToDB();
//                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                            User u = UserMapper._toModel(userDTO);
//                            intent.putExtra("user", u);
//                            startActivity(intent);
//                            return;
//                        }
//                        Toast.makeText(LoginActivity.this, "Incorrect account, please input again !", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserDTO> call, Throwable t) {
//                        Toast.makeText(LoginActivity.this, "Incorrect account, please input again !", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                });
            }
        });
    }
    private void saveToDB(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("users",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", gson.toJson(userDTO));
        editor.commit();
    }
    private void readUser(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("users",MODE_PRIVATE);
        String data = sharedPreferences.getString("user","");
        if(!"".equals(data)){
            userDTO = gson.fromJson(data,UserDTO.class);
        }
    }
}
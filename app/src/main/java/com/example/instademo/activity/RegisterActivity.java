package com.example.instademo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instademo.R;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.UserDTO;
import com.example.instademo.model.RegisterForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etRepassword;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponent();
        setOnClick();
    }
    private void initComponent(){
        etUsername = findViewById(R.id.etUsername_register);
        etPassword = findViewById(R.id.etPassword_register);
        etRepassword = findViewById(R.id.etRepassword_register);

        btnRegister = findViewById(R.id.btnSignUp);
    }
    private void setOnClick(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String repass = etRepassword.getText().toString();

                RegisterForm form = new RegisterForm(username,password,repass);

                ApiService.apiService.register(form).enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        UserDTO userDTO = response.body();
                        if(userDTO != null){
                            Toast.makeText(RegisterActivity.this,userDTO.getUsername(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Null",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this,"Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}
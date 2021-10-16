package com.example.instademo.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instademo.R;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.UserDTO;
import com.example.instademo.mapper.UserMapper;
import com.example.instademo.model.User;
import com.example.instademo.model.UserForm;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private ImageView imgAvt, imgClose;
    private Button btnSave;
    private EditText etFullName, etAddress, etBirthDate, etPhone;
    private ActivityResultLauncher launcher;
    private TextView tvChangeAvt;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initComponent();
        setOnClickBtn();
        readUser();
        if(user != null){
            // filll data
            fillData();

        }
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data == null) {
                                return;
                            }
                            Uri uri = data.getData();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imgAvt.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void fillData(){
        etFullName.setText(user.getFullName());
        etPhone.setText(user.getPhone());
        etAddress.setText(user.getAddress());
        etBirthDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getDateOfBirth()));
        Picasso.with(EditProfileActivity.this).load("http://192.168.1.5:8080/naq11.jpg")
                .placeholder(R.mipmap.ic_launcher).error(R.drawable.img_default)
                .into(imgAvt);
    }

    void initComponent() {
        imgAvt = findViewById(R.id.image_profile);
        btnSave = findViewById(R.id.btnSave);
        etFullName = findViewById(R.id.etFullname);
        etBirthDate = findViewById(R.id.etBirthdate);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        imgClose = findViewById(R.id.close);
        tvChangeAvt = findViewById(R.id.txtChange);
    }

    private void setOnClickBtn() {
        tvChangeAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) imgAvt.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                byte[] avt = _convertBitmapToByteArray(bitmap);
                String fullName = etFullName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String birthDate = etBirthDate.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                UserForm form = new UserForm(user.getUsername(),fullName,address,birthDate,phone,avt);
                sendUpdateRequest(form);
            }
        });
    }
    private void sendUpdateRequest(UserForm form){
        ApiService.apiService.updateUser(form).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                UserDTO userDTO = response.body();
                if(userDTO != null){
                    Toast.makeText(EditProfileActivity.this,"Update successfully !", Toast.LENGTH_LONG).show();
                    user = UserMapper._toModel(userDTO);

                    etFullName.setText(user.getFullName());
                    etPhone.setText(user.getPhone());
                    etAddress.setText(user.getAddress());
                    etBirthDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getDateOfBirth()));
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {

            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private byte[] _convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void readUser(){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("users",MODE_PRIVATE);
        String data = sharedPreferences.getString("user","");
        if(!"".equals(data)){
            UserDTO userDTO = gson.fromJson(data, UserDTO.class);
            user = UserMapper._toModel(userDTO);
        }
    }
    private void saveToDB(UserDTO userDTO){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("users",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", gson.toJson(userDTO));
        editor.commit();
    }
}
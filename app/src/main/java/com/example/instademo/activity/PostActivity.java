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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instademo.MainActivity;
import com.example.instademo.R;
import com.example.instademo.api.ApiService;
import com.example.instademo.dto.PostDTO;
import com.example.instademo.mapper.PostMapper;
import com.example.instademo.model.Post;
import com.example.instademo.model.PostForm;
import com.example.instademo.utils.LogedUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    private ImageView imgView;
    private Button btnAdd;
    private EditText etContent;
    private static final int MY_REQUEST_CODE = 10;
    private ActivityResultLauncher launcher;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initComponent();
        setOnClick();
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
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                imgView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    private void initComponent(){
        imgView = findViewById(R.id.img_post_add);
        btnAdd = findViewById(R.id.btnAddPost);
        etContent = findViewById(R.id.et_content_post);
    }
    private void setOnClick(){
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap != null){
                    PostForm postForm = new PostForm();
                    postForm.setContent(etContent.getText().toString().trim());
                    postForm.setUser_id(LogedUser.logedUser.getId());
                    postForm.setByteAvt(_convertBitmapToByteArray(bitmap));

                    ApiService.apiService.createPost(postForm).enqueue(new Callback<PostDTO>() {
                        @Override
                        public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                            PostDTO dto = response.body();
                            if(dto != null){
                                Toast.makeText(PostActivity.this,"Create succsessfully !",Toast.LENGTH_LONG).show();
                                LogedUser.logedUser.getListPos().add(PostMapper._toModel(dto));
                                Intent intent = new Intent(PostActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Call<PostDTO> call, Throwable t) {

                        }
                    });
                }else{
                    Toast.makeText(PostActivity.this,"Please choose picture !",Toast.LENGTH_LONG).show();
                }

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
}
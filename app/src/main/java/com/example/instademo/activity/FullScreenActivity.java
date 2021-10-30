package com.example.instademo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.instademo.R;
import com.example.instademo.utils.LocalConst;
import com.squareup.picasso.Picasso;

public class FullScreenActivity extends AppCompatActivity {
    private ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        imgV = findViewById(R.id.img_show);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Picasso.with(this).load(LocalConst.URL + "/uploads/" + url).error(R.mipmap.ic_launcher).into(imgV);
        imgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
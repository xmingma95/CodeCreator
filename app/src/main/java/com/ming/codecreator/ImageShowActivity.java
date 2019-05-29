package com.ming.codecreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageShowActivity extends AppCompatActivity {
    private ImageView iv_image_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        initView();
        initData();
    }

    private void initData() {
        String path=getIntent().getStringExtra("IMAGEPATH");
        Glide.with(this)
                .load(path)
                .placeholder(R.mipmap.image_default_white)
                .into(iv_image_show);
    }

    private void initView() {
        iv_image_show=findViewById(R.id.iv_image_show);
    }
}

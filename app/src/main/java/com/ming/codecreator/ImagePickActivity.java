package com.ming.codecreator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ming.codecreator.adapter.ImageListAdapter;
import com.ming.codecreator.bean.ImageBean;

import java.util.ArrayList;

public class ImagePickActivity extends AppCompatActivity {
    private RecyclerView rvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);
        initView();
        initData();
    }

    private void initData() {
        ArrayList<ImageBean>  imageList = getIntent().getParcelableArrayListExtra("IMAGELIST");
        ImageListAdapter adapter=new ImageListAdapter(this,imageList);
        adapter.setOnItemClickListener(new ImageListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String path, Context context) {
                Intent intent=new Intent(context,ImageShowActivity.class);
                intent.putExtra("IMAGEPATH",path);
                startActivity(intent);
            }
        });
        rvImage.setLayoutManager(new GridLayoutManager(this,2));
        rvImage.addItemDecoration(new SpaceItemDecoration(20));
        rvImage.setAdapter(adapter);

    }

    private void initView() {
        rvImage=findViewById(R.id.rv_image);
    }
}

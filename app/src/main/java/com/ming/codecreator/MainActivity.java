package com.ming.codecreator;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ming.codecreator.bean.ImageBean;
import com.ming.codecreator.utils.CacheUtils;
import com.ming.codecreator.utils.CodeUtils;
import com.ming.codecreator.utils.DensityUtil;
import com.ming.codecreator.utils.FileUtils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.android.Intents;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;

import static com.ming.codecreator.utils.Contants.dirName;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE =1 ;
    private EditText etCodeNum;
    private Button btCreate;
    private Button btScan;
    private ImageView ivCodeBar;
    private RadioButton rbSave;
    private Button btClear;
    private Button btSave;
    private Button btOpen;
    private Bitmap codeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //请求权限
        performCodeWithPermission("存储权限", new PermissionCallback() {
            @Override
            public void hasPermission() {

            }

            @Override
            public void noPermission() {

            }
        },Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.VIBRATE);
        initView();
        initListener();
    }

    private void initListener() {
        btCreate.setOnClickListener(this);
        btScan.setOnClickListener(this);
        btClear.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btOpen.setOnClickListener(this);
        rbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CacheUtils.putBoolean(compoundButton.getContext(),"ISAUTOSAVE",b);
            }
        });
    }

    private void initView() {
        etCodeNum=findViewById(R.id.et_code_num);
        btCreate=findViewById(R.id.bt_create);
        ivCodeBar=findViewById(R.id.iv_code_bar);
        btScan=findViewById(R.id.bt_scan);
        rbSave=findViewById(R.id.rb_save);
        btClear=findViewById(R.id.bt_clear);
        btSave=findViewById(R.id.bt_save);
        btOpen=findViewById(R.id.bt_open);
        //设置缓存
        rbSave.setChecked(CacheUtils.getBoolean(this,"ISAUTOSAVE"));
        String temp=CacheUtils.getString(this,"BARCODE");
        if(temp!=null&&temp.length()>0){
            etCodeNum.setText(temp);
            showBarCode(temp,true);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_create:
                String codeNum= etCodeNum.getText().toString().trim();
                CacheUtils.putString(this,"BARCODE",codeNum);
                showBarCode(codeNum,false);
                break;
            case R.id.bt_scan:
                // 扫码
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.setAction(Intents.Scan.ACTION);
                intent.putExtra(Intents.Scan.FORMATS, "QR_CODE");
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.bt_clear:
                if(FileUtils.deleteAllImage(dirName)) {
                    Toast.makeText(this,"清除图片成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"清除图片失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_save:
                if(codeImage!=null){
                    FileUtils.saveImageInLocal(codeImage);
                }else{
                    Toast.makeText(this,"还没生成图片",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_open:
                ArrayList<ImageBean> imageList=FileUtils.findAllImage(dirName);
                if(imageList!=null&&imageList.size()>0){
                    Intent intent1=new Intent(this,ImagePickActivity.class);
                    intent1.putParcelableArrayListExtra("IMAGELIST",imageList);
                    startActivity(intent1);
                }else{
                    Toast.makeText(this,"还没生成任何图片",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    //扫描条码成功后回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            if (data != null) {
                String content=data.getStringExtra(Constant.CODED_CONTENT);
                etCodeNum.setText(content);
                CacheUtils.putString(this,"BARCODE",content);
                showBarCode(content,false);
            }

        }
    }

    private void showBarCode(String content,boolean isFromCache){
        if(content==null||content.length()==0){
            Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else{
            codeImage=CodeUtils.createBarcode(content,DensityUtil.dip2px(this,280),
                    DensityUtil.dip2px(this,100),true);
            ivCodeBar.setImageBitmap(codeImage);
            if(rbSave.isChecked()&&!isFromCache){
                Log.e("ischeck","save");
                if(FileUtils.saveImageInLocal(codeImage)){
                    Toast.makeText(this,"图片保存成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"图片保存失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}

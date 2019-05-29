package com.ming.codecreator.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.ming.codecreator.bean.ImageBean;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static com.ming.codecreator.utils.Contants.dirName;

/**
 * @author ming
 * @version $Rev$
 * @des ${TODO}
 */
public class FileUtils {

    public static boolean saveImageInLocal(Bitmap codeImage) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String bitName=generateFileName()+".png";
            Log.e("save", Environment.getExternalStorageDirectory().getPath());//sd = /storage/emulated/0
//            String path=Environment.getExternalStorageDirectory().getPath();
//            String dirName="BarCodeImage";
            File dirFile=new File(dirName);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            File file = new File(dirFile, bitName);
            FileOutputStream out;
            try{
                out = new FileOutputStream(file);
                if(codeImage.compress(Bitmap.CompressFormat.PNG, 90, out))
                {
                    out.flush();
                    out.close();
                }
                Log.e("save",file.getAbsolutePath());
            } catch (Exception e)
            {
                e.printStackTrace();
                Log.e("save fail",dirName);
            }
            return true;
        } else {
            return false;
        }

    }

    public static boolean deleteAllImage(String dirPath){
        File file = new File(dirPath);
        if(file.exists()&&file.isDirectory()){
            File[] filesList=file.listFiles();
            for (File aFilesList : filesList) {
                if (!aFilesList.isDirectory() && isPNGFile(aFilesList)) {
                    Log.e("isPNG", aFilesList.getName());
                    if(aFilesList.delete()){
                        Log.e("delete","success");
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }

    public static ArrayList<ImageBean> findAllImage(String dirPath){
        File file=new File(dirPath);
        if(file.exists()&&file.isDirectory()){
            File[] files=file.listFiles();
            ArrayList<ImageBean> imageList=new ArrayList<>();
            for(File f:files){
                if(!f.isDirectory()&&isPNGFile(f)){
                    ImageBean image=new ImageBean();
                    image.setDate(f.lastModified());
                    image.setPath(f.getAbsolutePath());
                    imageList.add(image);
                }
            }
            //按最后修改日期，从大到小
            Collections.sort(imageList, new Comparator<ImageBean>() {
                @Override
                public int compare(ImageBean i1, ImageBean i2) {
                    return i2.getDate()>i1.getDate()?1:-1;
                }
            });
            return imageList;
        }else{
            return null;
        }


    }
    private static boolean isPNGFile(File file){
        String s=file.getName();
        Log.e("isPNG",s.substring(s.lastIndexOf(".")+1));
        return s.substring(s.lastIndexOf(".")+1).equals("png");
    }


    private static String generateFileName() {
        //文件名为时间
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return sdf.format(new Date(timeStamp));
    }


}

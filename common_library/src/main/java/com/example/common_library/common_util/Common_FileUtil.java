package com.example.common_library.common_util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Common_FileUtil {

    String TAG="eeeFileUtils";
    /**android Q 权限限制，只能在程序包存储位置进行读写。*/
    public static String getFileFolder(Context context,String pathStr){
        String path=null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //保存在SD卡中
            path = Environment.getExternalStorageDirectory().getPath() + pathStr;
        } else {
            //保存到应用目录下
            path = context.getFilesDir().getAbsolutePath() + pathStr;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            Log.i("eeeFileUtils", "init: 创建文件夹 路径："+path);
        }
        Log.i("eee文件路径", path);
        return path;
    }

    /**
     * 读取文件中的内容**/
    public static String readFile(Context context,String pathStr){
        String filePath=pathStr;
        File file=new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String content = ""; //文件内容字符串
        try {
            InputStream instream = new FileInputStream(file);
            if (instream != null)
            {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffReader = new BufferedReader(inputreader);
                String line;

                //分行读取
                while (( line = buffReader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();
            }
        } catch (FileNotFoundException e)
        {
            Log.d("eeeFileUtils", "readFile==The File doesn't not exist.");
        }
        catch (IOException e)
        {
            Log.d("eeeFileUtils", e.getMessage());
        }
        return content;
    }


    /**
     * 定义文件保存的方法，写入到文件中，所以是输出流
     */
    public static void save(String stringValue,Context context,String path) {
        FileOutputStream fos = null;
        try {
            // Context.MODE_PRIVATE私有权限，Context.MODE_APPEND追加写入到已有内容的后面
            fos = context.openFileOutput(path, Context.MODE_APPEND);
            fos.write(stringValue.getBytes());
            fos.write("\r\n".getBytes());//写入换行
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**文件中写入字符串**/
    public  static boolean writeString2Txt(String content,String filePath) {
        // 每次写入时，都换行写
        // String strContent = strcontent + "\r\n";
        String strContent = content;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
            return true;
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
            return false;
        }
    }

    /**删除后重新写入*/
    public  static boolean writeString2TxtReSet(String content,String filePath) {
        // 每次写入时，都换行写
        // String strContent = strcontent + "\r\n";
        String strContent = content;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }

            file.getParentFile().mkdirs();
            file.createNewFile();

            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
            return true;
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
            return false;
        }
    }

}

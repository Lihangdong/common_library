package com.example.common_library.common_util;

import android.util.Log;

public class Common_TagUtil {

    protected static String prefixTag="eee";

    public static void printTagI(String name,String sign,String content){
        Log.i(prefixTag+name, sign+" "+content);
    }

    public static void printTagE(String name,String sign,String content){
        Log.e(prefixTag+name, sign+" "+content);
    }
}

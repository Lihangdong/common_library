package com.example.common_library.common_util;

import android.annotation.SuppressLint;
import android.app.Application;

public class GlobalApplicationUtils {
    private static final GlobalApplicationUtils APPLICATION_UTILS = new GlobalApplicationUtils();
    private GlobalApplicationUtils() {
    }
    public static GlobalApplicationUtils getInstance() {
        return APPLICATION_UTILS;
    }

    private Application currentApplication;

    /**
     * 获取全局的application
     *
     * @return 返回application
     */
    @SuppressLint("PrivateApi")
    public Application getNewApplication() {
        try {
            if (currentApplication == null) {
                currentApplication = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
            }
            return currentApplication;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

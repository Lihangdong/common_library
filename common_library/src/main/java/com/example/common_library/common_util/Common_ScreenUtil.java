package com.example.common_library.common_util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class Common_ScreenUtil {
    /**
     * Get the width of the screen.
     *
     * @Time 2015年11月3日
     * @Author lizy18
     * @param context
     *            The context to use. Usually your
     *            {@link android.app.Application} or
     *            {@link android.app.Activity} object.
     * @return Return the width of the screen.
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Get the height of the screen.
     *
     * @Time 2015年11月3日
     * @Author lizy18
     * @param context
     *            The context to use. Usually your
     *            {@link android.app.Application} or
     *            {@link android.app.Activity} object.
     * @return Return the height of the screen.
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     *
     * Whether the Status bar is hidden or not,the method always helps you get
     * the height of Status bar.
     *
     * @Time 2015年11月3日
     * @Author lizy18
     * @param context
     *            The context to use. Usually your
     *            {@link android.app.Application} or
     *            {@link android.app.Activity} object.
     * @return Return the height of Status bar.
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int id = (Integer) (clazz.getField("status_bar_height").get(object));
            statusHeight = context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 功能描述：获取整块屏幕的高度
     *
     * @Time 2016年4月15日
     * @Author lizy18
     * @param context
     * @return int
     */
    public static int getRealScreenHeight(Context context) {
        int dpi = 0;
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 功能描述：获取虚拟按键区域的高度
     *
     * @Time 2016年4月15日
     * @Author lizy18
     * @param context
     * @return int 如果有虚拟按键则返回其高度否则返回0；
     */
    public static int getNavigationAreaHeight(Context context) {
        int realScreenHeight = Common_ScreenUtil.getRealScreenHeight(context);
        int screenHeight = Common_ScreenUtil.getScreenHeight(context);

        return realScreenHeight - screenHeight;
    }


}

package com.example.common_library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.common_library.common_util.GlobalApplicationUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    static String TAG="eeeBaseActivity";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

        addActivity(this);
        // 设置全屏
        fullScreen();
        int layoutId = setLayout();
        if(layoutId!=0){
            setContentView(layoutId);
        }

        initView();
        initData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivity(this);

        // 设置全屏
        fullScreen();
        int layoutId = setLayout();
        if(layoutId!=0){
            setContentView(layoutId);
        }

        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();
    /**
     * 设置数据等逻辑代码
     */
    protected abstract void initData();

    /**
     * 绑定布局
     * @return
     */
    protected abstract int setLayout();


    /**
     * 简化findViewById()
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T fvbi(int resId){
        return (T) findViewById(resId);
    }

    /**
     * Intent跳转
     * @param context
     * @param clazz
     */
    protected void goToActivity(Context context, Class<? extends BaseActivity> clazz){
        goToActivity(context,clazz,null);
    }
    /**
     * Intent带值跳转
     * @param context
     * @param clazz
     * @param bundle
     */
    protected void goToActivity(Context context, Class<? extends BaseActivity> clazz, Bundle bundle){
        Intent intent = new Intent(context,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 带返回值的跳转
     * @param context
     * @param clazz
     * @param bundle
     * @param reuqestCode
     */
    protected void goToActivity(Context context,Class<? extends BaseActivity> clazz,Bundle bundle,int reuqestCode){
        Intent intent = new Intent(context,clazz);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,reuqestCode);
    }

    //全面屏，挖孔屏
    public void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }else{
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    /**
     * 显示提示  toast
     *
     * @param msg 提示信息
     */
    Toast toast;
    @SuppressLint("ShowToast")
    public void baseShowToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(GlobalApplicationUtils.getInstance().getNewApplication(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(GlobalApplicationUtils.getInstance().getNewApplication(), msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }


    public static List<Activity> activityList = new ArrayList<Activity>();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity管理
        removeActivity(this);
    }

    /**
     * 向List中添加一个活动
     *
     * @param activity 活动
     */
    public static void addActivity(Activity activity) {
        Log.i(TAG, "addActivity: "+activity.getLocalClassName());
        activityList.add(activity);
    }
    /**
     * 从List中移除活动
     *
     * @param activity 活动
     */
    public static void removeActivity(Activity activity) {
        Log.i(TAG, "removeActivity: "+activity.getLocalClassName());
        activityList.remove(activity);
    }


    /**
     * 将List中存储的活动全部销毁掉
     */
    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                Log.i(TAG, "finishAll: "+activity.getLocalClassName());
                activity.finish();
            }
        }
    }


}

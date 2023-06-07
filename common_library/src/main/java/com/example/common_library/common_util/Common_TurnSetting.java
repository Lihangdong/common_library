package com.example.common_library.common_util;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

public class Common_TurnSetting {
    public static void goHuaWeiMainager() {
        try {
            Intent intent = new Intent(GlobalApplicationUtils.getInstance().getNewApplication().getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            GlobalApplicationUtils.getInstance().getNewApplication().startActivity(intent);
        }
        catch (Exception e) {
            Log.i("eeeTurnSetting", "华为跳转权限设置界面失败！");
            e.printStackTrace();
        }
    }
}

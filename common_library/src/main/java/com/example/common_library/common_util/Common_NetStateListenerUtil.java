package com.example.common_library.common_util;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.example.common_library.common_receiver.Common_NetBroadcastReceiver;

/***

 <!--网络状态监听-->
 <receiver android:name="com.example.common_library.common_receiver.Common_NetBroadcastReceiver" android:exported="true">
 <intent-filter>
 <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
 </intent-filter>
 </receiver>

 //注册监听
 Common_NetStateListenerUtil.registerListenerNetState(this, new Common_NetBroadcastReceiver.NetConnectedListener() {
@Override
public void netContent(boolean isConnected) {
Log.i("eee", "netContent: "+isConnected);
if(isConnected){
Toast.makeText(MainActivity.this, "连接了网络", Toast.LENGTH_SHORT).show();
}else{
Toast.makeText(MainActivity.this, "网络不行了", Toast.LENGTH_SHORT).show();
}
}
});
 //注销监听
 Common_NetStateListenerUtil.unRegisterNetStateReceiver(this);
 * */

public class Common_NetStateListenerUtil {

    protected static Common_NetBroadcastReceiver netBroadcastReceiver;
    public static void registerListenerNetState(Context context, Common_NetBroadcastReceiver.NetConnectedListener netConnectedListener){
        netBroadcastReceiver = new Common_NetBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(netBroadcastReceiver, intentFilter);

        netBroadcastReceiver.setNetConnectedListener(new Common_NetBroadcastReceiver.NetConnectedListener() {
            @Override
            public void netContent(boolean isNetConnected) {
                netConnectedListener.netContent(isNetConnected);
            }
        });
    }

    public static void unRegisterNetStateReceiver(Context context){
        if(netBroadcastReceiver!=null){
            context.unregisterReceiver(netBroadcastReceiver);
        }
    }

}

package com.example.common_library.common_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Common_NetBroadcastReceiver extends BroadcastReceiver {


    private static NetConnectedListener netConnectedListener;
    MyHandler myHandler;
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

//        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        boolean rltPing=ping();
        if (!rltPing) {
            //WIFI和移动网络均未连接
            netConnectedListener.netContent(false);
            Log.i("eee", " ping 外网失败 ");

        } else {
            //WIFI连接或者移动网络连接
            if(rltPing){
                netConnectedListener.netContent(true);
                Log.i("eee", "NetBroadcastReceiver: wifi已连接可以上网");
            }else{
                Log.i("eee", "NetBroadcastReceiver: wifi已连接不可以上网");
                netConnectedListener.netContent(false);
                if(myHandler==null){
                    myHandler=new MyHandler();
                }
                myHandler.sendEmptyMessageDelayed(1,3000);
            }

        }
    }

    public void setNetConnectedListener(NetConnectedListener netConnectedListener) {
        this.netConnectedListener = netConnectedListener;
    }

    public interface NetConnectedListener {
        void netContent(boolean isConnected);
    }


    public static final boolean ping() {
        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 2 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("eee", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("eee", "result = " + result);
        }
        return false;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            boolean rePing=ping();
            if(rePing){
                netConnectedListener.netContent(true);
                if(myHandler!=null){
                    myHandler.removeCallbacksAndMessages(null);
                    myHandler=null;
                }
            }else{
                netConnectedListener.netContent(false);
                myHandler.sendEmptyMessageDelayed(1,3000);
            }
        }
    }

    public void destroy(){
        if(myHandler!=null){
            myHandler.removeCallbacksAndMessages(null);
            myHandler=null;
        }
    }

}

package com.xue.enablespeedmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.widget.Toast;

/**
 * @author xueshiji
 * desc 广播处理
 */
public class StartReceiver extends BroadcastReceiver {
    public StartReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //此处及是重启的之后，打开我们app的方法
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            try {
                SharedPreferences preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
                boolean isAutoRun = preferences.getBoolean("isAutoRun", false);
                if (isAutoRun) {
                    Settings.System.putInt(context.getContentResolver(), "speed_mode", 1);
                    Toast.makeText(context, "启用极致模式成功!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "启用极致模式失败!" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
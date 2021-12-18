package com.xue.enablespeedmode;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
            Toast.makeText(context, "哈哈，我成功启动了！", Toast.LENGTH_LONG).show();
            try {
                Settings.Global.putInt(context.getContentResolver(), "speed_mode", 1);
                Toast.makeText(context, "启用极致模式成功!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "启用极致模式失败!"+e.getMessage(), Toast.LENGTH_LONG).show();
            }

//            Intent intent= new Intent(context, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //自启动服务（Service）
//            context.startService(intent);
        }
    }
}
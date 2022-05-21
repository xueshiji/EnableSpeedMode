package com.xue.enablespeedmode;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.topjohnwu.superuser.Shell;

public class SpeedModeUtil {
    public static boolean isSpeedModeEnable(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "speed_mode", 0) == 1;
    }

    public static void setSpeedMode(Context context, boolean isEnable) {
        Shell.Result exec = Shell.cmd("settings put system speed_mode " + (isEnable ? 1 : 0)).exec();
        if (!exec.isSuccess()) {
            Log.i("极致模式设置失败", exec.toString());
            Toast.makeText(context, "极致模式设置失败,请授予root权限", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, !isEnable ? "关闭极致模式成功!" : "启用极致模式成功!", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.xue.enablespeedmode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.topjohnwu.superuser.Shell;

import java.io.File;

public class YcModeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("yc调度模式设置");
        setContentView(R.layout.activity_yc_mode);
        TextView tvYcStatus = findViewById(R.id.tvYcStatus);
        Button btnauto = findViewById(R.id.btnAuto);
        Button btnBalance = findViewById(R.id.btnBalance);
        Button btnPowersave = findViewById(R.id.btnPowersave);
        Button btnPerformance = findViewById(R.id.btnPerformance);
        Button btnFast = findViewById(R.id.btnFast);
        if (!fileIsExists(Environment.getExternalStorageDirectory().getPath() + "/Android/yc/uperf/cur_powermode.txt")) {
            tvYcStatus.setText("您未安装yc调度，无法设置!");
            btnauto.setEnabled(false);
            btnBalance.setEnabled(false);
            btnPowersave.setEnabled(false);
            btnPerformance.setEnabled(false);
            btnFast.setEnabled(false);
        } else {
            tvYcStatus.setText(ycModeToChinese(getYcmode()));
        }

        btnauto.setOnClickListener(button -> setYcModeListener(button, tvYcStatus));
        btnBalance.setOnClickListener(button -> setYcModeListener(button, tvYcStatus));
        btnPowersave.setOnClickListener(button -> setYcModeListener(button, tvYcStatus));
        btnPerformance.setOnClickListener(button -> setYcModeListener(button, tvYcStatus));
        btnFast.setOnClickListener(button -> setYcModeListener(button, tvYcStatus));
    }


    private void setYcModeListener(View button, TextView tvYcStatus) {
        setYcMode(button.getTag().toString());
        tvYcStatus.setText(ycModeToChinese(getYcmode()));
    }

    private void setYcMode(String mode) {
        Boolean appGrantedRoot = Shell.isAppGrantedRoot();
        if (appGrantedRoot == null || !appGrantedRoot) {
            Shell.Result su = Shell.cmd("su").exec();
            if (!su.isSuccess()) {
                Toast.makeText(this, "未授予ROOT权限!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Shell.Result result = Shell.cmd(String.format("sh /data/powercfg.sh %s", mode)).exec();
        if (!result.isSuccess()) {
            Log.i("设置yc模式失败", result.toString());
        } else {
            Toast.makeText(this, "设置成功!", Toast.LENGTH_LONG).show();
        }

    }

    private String getYcmode() {
        Shell.Result result = Shell.cmd("cat /sdcard/Android/yc/uperf/cur_powermode.txt").exec();
        if (result.isSuccess()) {
            return result.getOut().get(0);
        } else {
            return "获取失败,请授予root权限！";
        }
    }

    private String ycModeToChinese(String ycMode) {
        switch (ycMode) {
            case "auto":
                return "自动";
            case "balance":
                return "均衡";
            case "powersave":
                return "省电";
            case "performance":
                return "性能";
            case "fast":
                return "极速";
            default:
                return ycMode;
        }
    }

    public boolean fileIsExists(String fileName) {
        try {
            File f = new File(fileName);
            if (f.exists()) {
                Log.i("测试", "有这个文件");
                return true;
            } else {
                Log.i("测试", "没有这个文件");
                return false;
            }
        } catch (Exception e) {
            Log.i("测试", "崩溃");
            return false;
        }
    }

}
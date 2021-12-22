package com.xue.enablespeedmode;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = this.getSharedPreferences("settings", MODE_PRIVATE);
        boolean isAutoRun = preferences.getBoolean("isAutoRun", false);
        setContentView(R.layout.activity_main);
        Button btnEnable = findViewById(R.id.btnEnable);
        Button btnJump = findViewById(R.id.btnJump);
        btnJump.setOnClickListener(view -> {
            openAutoStart();
        });

        TextView tvStatus = findViewById(R.id.tvStatus);
        Switch swAutorun = findViewById(R.id.swAutorun);
        swAutorun.setChecked(isAutoRun);
        swAutorun.setOnCheckedChangeListener((compoundButton, b) -> {
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("isAutoRun", b);
            edit.apply();
        });
        btnEnable.setOnClickListener(view -> {
            boolean currentStatus = Settings.System.getInt(this.getContentResolver(), "speed_mode", 0) == 1;
            if (!Settings.System.canWrite(MainActivity.this)) {
                Toast.makeText(this, "请授予修改设置权限!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 200);
            } else {
                try {
                    Settings.System.putInt(this.getContentResolver(), "speed_mode", currentStatus ? 0 : 1);
                    Toast.makeText(this, currentStatus ? "关闭极致模式成功!" : "启用极致模式成功!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, (currentStatus ? "关闭极致模式失败!" : "启用极致模式失败!") + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            currentStatus = Settings.System.getInt(this.getContentResolver(), "speed_mode", 0) == 1;
            btnEnable.setBackgroundColor(currentStatus ? Color.RED : Color.parseColor("#2553E1"));
            btnEnable.setText(currentStatus ? "关闭极致模式" : "启用极致模式");
            tvStatus.setText(currentStatus ? "极致模式已启用" : "极致模式未启用");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean currentStatus = Settings.System.getInt(this.getContentResolver(), "speed_mode", 0) == 1;
        TextView tvStatus = findViewById(R.id.tvStatus);
        tvStatus.setText(currentStatus ? "极致模式已启用" : "极致模式未启用");
        Button btnEnable = findViewById(R.id.btnEnable);
        btnEnable.setBackgroundColor(currentStatus ? Color.RED : Color.parseColor("#2553E1"));
        btnEnable.setText(currentStatus ? "关闭极致模式" : "启用极致模式");
    }

    private void openAutoStart() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.applications.InstalledAppDetails"));
        intent.setData(Uri.fromParts("package", this.getPackageName(), null));
        startActivity(intent);
    }
}
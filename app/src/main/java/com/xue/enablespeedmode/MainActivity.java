package com.xue.enablespeedmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(view -> {
            try {
                Settings.Global.putInt(this.getContentResolver(), "speed_mode", 1);
                Toast.makeText(this, "启用极致模式成功!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "启用极致模式失败!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
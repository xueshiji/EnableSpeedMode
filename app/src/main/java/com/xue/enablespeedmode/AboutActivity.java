package com.xue.enablespeedmode;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drakeet.about.AbsAboutActivity;
import com.drakeet.about.Card;
import com.drakeet.about.Category;
import com.drakeet.about.Contributor;
import com.drakeet.about.License;

import java.util.List;

public class AboutActivity extends AbsAboutActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        setTitle("关于");
        icon.setImageResource(R.drawable.ic_launcher);
        slogan.setText("极致模式");
        version.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull List<Object> items) {
        items.add(new Category("介绍"));
        items.add(new Card(getString(R.string.card_content)));

        items.add(new Category("开发者"));
        items.add(new Contributor(R.drawable.coolapkavator, "酷安", "@xuesama", "http://www.coolapk.com/u/2047810"));
        items.add(new Contributor(R.drawable.githubqvator, "Github", "@xueshiji", "https://github.com/xueshiji"));


        items.add(new Category("引用的开源项目"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
        items.add(new License("libsu", "topjohnwu", License.APACHE_2, "https://github.com/topjohnwu/libsu"));
    }
}

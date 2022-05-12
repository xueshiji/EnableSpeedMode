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

        items.add(new Category("更新记录"));
        items.add(new Card(getString(R.string.card_log)));
        items.add(new Category("开发者"));
        items.add(new Contributor(R.drawable.coolapkavator, "酷安", "@薛sama", "http://www.coolapk.com/u/2047810"));
        items.add(new Contributor(R.drawable.githubqvator, "Github", "@xueshiji", "https://github.com/xueshiji"));

        items.add(new Category("赞助"));
        items.add(new Contributor(R.drawable.alipay, "支付宝", "请作者喝瓶饮料", "https://qr.alipay.com/fkx19273hnsh7tczze8p1cf?t=1652330662686"));

        items.add(new Category("反馈"));
        items.add(new Contributor(R.drawable.ic_launcher, "如遇问题，请通过酷安或下面的邮箱向我反馈", "xueshiji@foxmail.com", ""));

        items.add(new Category("Q&A"));
        items.add(new Card(getString(R.string.card_Qa)));

        items.add(new Category("源代码"));
        items.add(new Contributor(R.drawable.github, "Github", "https://github.com/xueshiji/EnableSpeedMode", "https://github.com/xueshiji/EnableSpeedMode"));

        items.add(new Category("引用的开源项目"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
        items.add(new License("libsu", "topjohnwu", License.APACHE_2, "https://github.com/topjohnwu/libsu"));
    }
}

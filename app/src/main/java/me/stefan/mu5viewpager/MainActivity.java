package me.stefan.mu5viewpager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;

import me.stefan.library.mu5viewpager.Mu5Interface;
import me.stefan.library.mu5viewpager.Mu5ViewPager;

public class MainActivity extends AppCompatActivity implements Mu5Interface {

    private Mu5ViewPager mu5ViewPager;
    private TextView indexTv;

    private String[] datas = new String[]{
            "http://www.quanjing.com/image/2017index/lx1.png",
            "http://imgsrc.baidu.com/imgad/pic/item/241f95cad1c8a7860ea6962d6d09c93d70cf5001.jpg",
            "http://pic.qiantucdn.com/58pic/16/69/41/02758PICS2Q_1024.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/a50f4bfbfbedab6440d4dfe5fd36afc379311e74.jpg",
            "http://img.tuku.cn/file_big/201503/d8905515d1c046aeba51025f0ea842f0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1395710768,4003046922&fm=214&gp=0.jpg",
            "http://www.pp3.cn/uploads/201412/2014123114.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mu5ViewPager = (Mu5ViewPager) findViewById(R.id.viewpager);
        indexTv = (TextView) findViewById(R.id.index);

        mu5ViewPager.setData(datas, this);

        indexTv.setText(getString(R.string.index_str, 1, datas.length));
    }

    @Override
    public void onIndexChange(int currentIndex) {
        indexTv.setText(getString(R.string.index_str, currentIndex + 1, datas.length));
    }

    @Override
    public void onLoadImage(final ImageView imageView, String url, final int position) {
        Glide.with(getApplicationContext()).load(url).asBitmap().into(new ImageViewTarget<Bitmap>(imageView) {
            @Override
            protected void setResource(Bitmap loadedImage) {
                mu5ViewPager.bindSource(loadedImage, position, imageView);
            }
        });
    }
}

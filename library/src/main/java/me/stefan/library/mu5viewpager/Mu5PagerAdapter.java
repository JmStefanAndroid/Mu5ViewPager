package me.stefan.library.mu5viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by gjm on 2017/7/26.
 * Func:主要用于实现imageview的回调加载
 */

public class Mu5PagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> urls;
    private Mu5Interface mu5Interface;


    public Mu5PagerAdapter(Context mContext, List<String> urls) {
        this.mContext = mContext;
        this.urls = urls;
    }

    public Mu5PagerAdapter(Context mContext, List<String> urls, Mu5Interface mu5Interface) {
        this.mContext = mContext;
        this.urls = urls;
        this.mu5Interface = mu5Interface;
    }

    public void setMu5Interface(Mu5Interface mu5Interface) {
        this.mu5Interface = mu5Interface;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls == null ? 0 : urls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (mu5Interface != null) {
            mu5Interface.onLoadImage(imageView, urls.get(position), position);
        }
        container.addView(imageView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

package me.stefan.library.mu5viewpager;

import android.widget.ImageView;

/**
 * Created by gjm on 2017/7/26.
 * Func:
 */

public interface Mu5Interface {
    /**
     * 位置发生改变
     *
     * @param currentIndex
     */
    void onIndexChange(int currentIndex);


    void onLoadImage(ImageView imageView, String url, int position);
}

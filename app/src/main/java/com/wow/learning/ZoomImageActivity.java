package com.wow.learning;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wow.learning.widget.ZoomImageView;

public class ZoomImageActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private int[] IMAGES = {R.drawable.d1, R.drawable.d2, R.drawable.d3, R.mipmap.ic_launcher};
    private ImageView[] mImageViews = new ImageView[IMAGES.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        initViewPager();
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ZoomImageView imageView = new ZoomImageView(getApplicationContext());
                imageView.setImageResource(IMAGES[position]);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public int getCount() {
                return mImageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }
}

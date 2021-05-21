package android.javapapers.com.majorproject.Adapters;

import android.content.Context;
import android.javapapers.com.majorproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SlideViewAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideViewAdapter(Context context){
        this.context=context;
    }

    public int[] slide_images={
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3
    };


    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== (ImageView)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(slide_images[position]);
        ((ViewPager)container).addView(imageView,0);

        return imageView;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}

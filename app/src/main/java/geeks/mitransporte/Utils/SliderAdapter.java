package geeks.mitransporte.Utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import geeks.mitransporte.R;

public class SliderAdapter extends PagerAdapter {

    Context context ;
    LayoutInflater inflater ;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //ArrayImg
    public int[] slide_image = {
            R.drawable.splash_1,
            R.drawable.splash_2,
            R.drawable.splash_3,
            R.drawable.splash_4
    };

    @Override
    public int getCount() {
        return slide_image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return  view == (RelativeLayout) object ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.slider_layout, container,false);

        ImageView imgSlider = view.findViewById(R.id.ivImageIntro) ;
        imgSlider.setImageResource(slide_image[position]);

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((RelativeLayout) object);
    }
}

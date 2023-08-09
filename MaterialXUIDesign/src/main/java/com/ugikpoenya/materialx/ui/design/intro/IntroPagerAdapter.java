package com.ugikpoenya.materialx.ui.design.intro;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ugikpoenya.materialx.ui.design.R;
import com.ugikpoenya.materialx.ui.design.model.Image;

import java.util.List;

public class IntroPagerAdapter {
    public static class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Context context;
        private int max_step;
        private int item_intro;

        private List<Image> items;

        public MyViewPagerAdapter(Context context, List<Image> items, int item_intro) {
            this.context = context;
            this.items = items;
            this.max_step = items.size();
            this.item_intro = item_intro;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(item_intro, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(items.get(position).name);
            ((TextView) view.findViewById(R.id.description)).setText(items.get(position).brief);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(items.get(position).image);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return max_step;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    public static void ProgressDots(Context context, int max_step, int current_index, LinearLayout dotsLayout, int color) {

        ImageView[] dots = new ImageView[max_step];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(context);
            int height = 8;
            int width = i == current_index ? (height * 15) : (height * 4);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width, height));
            params.setMargins(2, 10, 2, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_rectangle);
            dots[i].setColorFilter(context.getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }
        dots[current_index].setImageResource(R.drawable.shape_rectangle);
        dots[current_index].setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_IN);
    }

    public static class onChangeListener implements ViewPager.OnPageChangeListener {
        private Context context;
        private int max_step;
        private int color;

        private Button btnNext;

        LinearLayout dotsLayout;

        public onChangeListener(Context context, int max_step, Button btnNext, LinearLayout dotsLayout, int color) {
            this.context = context;
            this.max_step = max_step;
            this.btnNext = btnNext;
            this.dotsLayout = dotsLayout;
            this.color = color;
            ProgressDots(context, max_step, 0, dotsLayout, color);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ProgressDots(context, max_step, position, dotsLayout, color);
            if (position == max_step - 1) {
                btnNext.setText(context.getString(R.string.DONE));
            } else {
                btnNext.setText(context.getString(R.string.NEXT));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

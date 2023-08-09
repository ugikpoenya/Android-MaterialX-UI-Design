package com.ugikpoenya.materialx.ui.design.intro;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class Transformer {

    public static class Depth implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position < -1) {    // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) {    // [-1,0]
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) {    // (0,1]
                view.setTranslationX(-position * view.getWidth());
                view.setAlpha(1 - Math.abs(position));
                view.setScaleX(1 - Math.abs(position));
                view.setScaleY(1 - Math.abs(position));

            } else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);

            }
        }
    }

    public static class Fade implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {
            if (position <= -1.0F || position >= 1.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(0.0F);
            } else if (position == 0.0F) {
                view.setTranslationX(view.getWidth() * position);
                view.setAlpha(1.0F);
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.setTranslationX(view.getWidth() * -position);
                view.setAlpha(1.0F - Math.abs(position));
            }
        }
    }

    public static class FlipVertical implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            view.setTranslationX(-position * view.getWidth());
            view.setCameraDistance(12000);

            if (position < 0.5 && position > -0.5) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }

            if (position < -1) {     // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) {    // [-1,0]
                view.setAlpha(1);
                view.setRotationY(180 * (1 - Math.abs(position) + 1));

            } else if (position <= 1) {    // (0,1]
                view.setAlpha(1);
                view.setRotationY(-180 * (1 - Math.abs(position) + 1));

            } else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);

            }
        }
    }

    public static class Pop implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            view.setTranslationX(-position * view.getWidth());
            if (Math.abs(position) < 0.5) {
                view.setVisibility(View.VISIBLE);
                view.setScaleX(1 - Math.abs(position));
                view.setScaleY(1 - Math.abs(position));
            } else if (Math.abs(position) > 0.5) {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static class Zoom implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(View view, float position) {
            if (position >= -1 || position <= 1) {
                // Modify the default slide transition to shrink the page as well
                final float height = view.getHeight();
                final float width = view.getWidth();
                final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                final float vertMargin = height * (1 - scaleFactor) / 2;
                final float horzMargin = width * (1 - scaleFactor) / 2;

                // Center vertically
                view.setPivotY(0.5f * height);
                view.setPivotX(0.5f * width);

                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
        }
    }


    public static class ZoomOut implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {

            if (position < -1) {  // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                final float scale = 1f + Math.abs(position);
                view.setScaleX(scale);
                view.setScaleY(scale);
                view.setAlpha(position < -1f || position > 1f ? 0f : 1f - (scale - 1f));

            } else {  // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }

        }
    }
}

package com.xiajue.dayxiaohua.ui.custom;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;
import com.xiajue.dayxiaohua.utils.L;

/**
 * xiaJue 2018/3/15创建
 */
public class PicassoTransformation implements Transformation {
    int screenWidth;
    double targetWidth;
    double maxHeight;
    private ImageView mImg;

    public PicassoTransformation(ImageView mImg, int targetWidth, int maxHeight) {
        this.mImg = mImg;
        this.targetWidth = targetWidth;
        this.maxHeight = maxHeight;
//        L.e("targetWidth=" + targetWidth + "maxHeight=" + maxHeight);
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
//        L.e("transform" + bitmap.getWidth());
        if (bitmap.getWidth() == 0) {
            return bitmap;
        }

        int bitWidth = bitmap.getWidth();
        int bitHeight = bitmap.getHeight();
        int viewHeight = (int) (bitHeight * ((float) targetWidth / bitWidth));
        int viewWidth = (int) targetWidth;

        if (viewHeight > maxHeight && maxHeight != -1) {
            viewHeight = (int) maxHeight;
        }

        if (mImg.getLayoutParams().width != viewWidth || mImg.getLayoutParams().height !=
                viewHeight) {
            L.e("settings...image size");
            mImg.getLayoutParams().width = viewWidth;
            mImg.getLayoutParams().width = viewWidth;
            mImg.getLayoutParams().height = viewHeight;
        }
        return bitmap;
    }

    @Override
    public String key() {
        return "transformation" + screenWidth;
    }

}

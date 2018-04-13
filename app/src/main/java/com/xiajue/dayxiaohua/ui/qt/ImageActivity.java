package com.xiajue.dayxiaohua.ui.qt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xiajue.dayxiaohua.R;
import com.xiajue.dayxiaohua.app.App;
import com.xiajue.dayxiaohua.base.BaseActivity;
import com.xiajue.dayxiaohua.utils.L;

import java.io.File;
import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static com.xiajue.dayxiaohua.R.id.gifImageView;
import static com.xiajue.dayxiaohua.R.id.imageView;


/**
 * xiaJue 2018/3/15创建
 */
public class ImageActivity extends BaseActivity implements View.OnClickListener {
    private View mRoot;
    private View mClose;
    private TextView mContents;
    private SubsamplingScaleImageView mImageView;
    private GifImageView mGifImageView;
    private Target mTarget;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindView() {
        mContents = (TextView) findViewById(R.id.contents);
        mImageView = (SubsamplingScaleImageView) findViewById(imageView);
        mGifImageView = (GifImageView) findViewById(gifImageView);
        mClose = findViewById(R.id.close);
        mRoot = findViewById(R.id.root);
    }

    @Override
    protected void initialize() {
        mImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mImageView.setMaxScale(10F);
        mClose.setOnClickListener(this);
        mRoot.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mGifImageView.setOnClickListener(this);
        setData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.avtivity_image_details;
    }

    private void setData() {
        Intent intent = getIntent();
        String contents = intent.getStringExtra("contents");
        mContents.setText(contents);
        String image = intent.getStringExtra("image");
        L.e("image=" + image);
        boolean gif = intent.getBooleanExtra("gif", false);
        if (gif) {
            //显示gif图片
            mImageView.setVisibility(View.GONE);
            mGifImageView.setVisibility(View.VISIBLE);
            try {
                GifDrawable drawable = new GifDrawable(new File(image));
                mGifImageView.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        //显示普通图片
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mImageView.setImage(ImageSource.bitmap(bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                L.e("load failed...");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(App.getInstance().getContext()).load(image).into(mTarget);
        L.e("image is show=" + mImageView.isShown() + "gifImage is show=" + mGifImageView.isShown
                ());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.root:
                //点击任何地方都消失
                finish();
                break;
            case R.id.gifImageView:
            case R.id.imageView:
                //图片除外...
                break;
        }
    }
}

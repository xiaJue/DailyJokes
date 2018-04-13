package com.xiajue.dayxiaohua.ui.qt;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.xiajue.dayxiaohua.R;
import com.xiajue.dayxiaohua.app.App;
import com.xiajue.dayxiaohua.bean.ImageBeans;
import com.xiajue.dayxiaohua.net.RetrofitFactory;
import com.xiajue.dayxiaohua.net.RxSchedulers;
import com.xiajue.dayxiaohua.net.qt.QtApiServer;
import com.xiajue.dayxiaohua.ui.custom.SimpleObserver;
import com.xiajue.dayxiaohua.utils.DiskManager;
import com.xiajue.dayxiaohua.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * xiaJue 2018/4/10创建
 */
public class QtAdapter extends BaseQuickAdapter<ImageBeans.Datum> {

    public QtAdapter(int layoutResId, List<ImageBeans.Datum> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ImageBeans.Datum datum) {
        //set contents text
        baseViewHolder.setText(R.id.contents, datum.getContent()).setText(R.id.date, StringUtils
                .formatDate(datum.getUpdatetime()));
        //set image
        final GifImageView imageView = baseViewHolder.getView(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImageClickListener != null) {
                    mOnImageClickListener.onImageClick(v, datum, baseViewHolder.getPosition());
                }
            }
        });
        //加载等待图片
        imageView.setImageResource(R.mipmap.wait);
        if (!datum.getUrl().endsWith(".gif")) {
            //普通图片
            setImage(datum, imageView);
        } else {
            //处理gif图片
            setGifImage(datum, imageView);
        }
    }

    /**
     * 显示普通图片
     *
     * @param datum
     * @param imageView
     */
    private void setImage(final ImageBeans.Datum datum, final GifImageView imageView) {
        //rx java 切换线程
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter e) throws Exception {
                //使用picasso获得同步的bitmap ps:由于picasso对于加载过的图片会从内存重新加载，但因此不会再执行transformation的逻辑,
                // 所以导致imageView的尺寸不会重新设置。又由于target类在picasso内部采用弱引用，容易被回收而不稳定，所以采用get方法同步获取bitmap
                Bitmap bitmap = Picasso.with(mContext).load(datum.getUrl()).get();
                e.onNext(bitmap);
            }
        }).compose(RxSchedulers.<Bitmap>applySchedulers()).subscribe(new SimpleObserver<Bitmap>() {
            @Override
            public void onNext(@NonNull Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                resetImageViewSize(imageView, bitmap.getWidth(), bitmap.getHeight());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                imageView.setImageResource(R.mipmap.fail);
            }
        });
    }

    /**
     * 显示gif图片
     *
     * @param datum
     * @param imageView
     */
    private void setGifImage(final ImageBeans.Datum datum, final GifImageView imageView) {
        //gif 图片先缓存到本地
        //rx code
        RetrofitFactory.create(QtApiServer.class).download(datum.getUrl()).compose(RxSchedulers
                .<ResponseBody>applySchedulers()).unsubscribeOn(Schedulers.io()).doOnSubscribe
                (new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        File file = DiskManager.getInstance(mContext).get(datum.getUrl());
                        if (file.exists()) {
                            //如果图片已经存在，直接设置并终止rx的链式
                            if (!disposable.isDisposed()) {
                                setGifImageView(imageView, file);
                                disposable.dispose();
                            }
                        }
                    }
                }).map(new Function<ResponseBody, File>() {
            @Override
            public File apply(@NonNull ResponseBody responseBody) throws Exception {
                //将图片缓存至应用缓存目录
                DiskManager.getInstance(mContext).putDrawable(datum.getUrl(), responseBody
                        .byteStream());
                return DiskManager.getInstance(mContext).get(datum.getUrl());
            }
        }).subscribe(new Consumer<File>() {
            @Override
            public void accept(@NonNull File f) throws Exception {
                //设置gif图片
                setGifImageView(imageView, f);
            }
        });
    }

    private void setGifImageView(@NonNull GifImageView imageView, File f) throws
            IOException {
        GifDrawable drawable = new GifDrawable(f);
        imageView.setImageDrawable(drawable);
        resetImageViewSize(imageView, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    /**
     * 重新设置imageView的宽高
     *
     * @param imageView
     * @param width
     * @param height
     */
    private void resetImageViewSize(@NonNull ImageView imageView, int width, int height) {
        //重新计算控件大小
        int viewWidth = App.getInstance().getItemMaxHeight();
        int viewHeight = (int) (height * ((float) viewWidth / width));

        int maxHeight = App.getInstance().getItemWidth();
        if (viewHeight > maxHeight && maxHeight != -1) {
            viewHeight = maxHeight;
        }
        //重新设置控件大小
        if (imageView.getLayoutParams().width != viewWidth || imageView.getLayoutParams().height !=
                viewHeight) {
            imageView.getLayoutParams().width = viewWidth;
            imageView.getLayoutParams().width = viewWidth;
            imageView.getLayoutParams().height = viewHeight;
        }
    }

    /**
     * listener
     */
    private OnImageClickListener mOnImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener {
        void onImageClick(View view, ImageBeans.Datum datum, int position);
    }
}

package com.xiajue.dayxiaohua.utils;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;
import com.xiajue.dayxiaohua.constant.ConstKey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.xiajue.dayxiaohua.constant.ConstKey.CACHE_PICTURE;


/**
 * xiaJue 2018/4/13创建
 */
public class DiskManager {
    private Context mContext;
    private static DiskManager mDiskManager;

    public static DiskManager getInstance(Context context) {
        if (mDiskManager == null) {
            synchronized (DiskManager.class) {
                if (mDiskManager == null) {
                    mDiskManager = new DiskManager(context);
                }
            }
        }
        return mDiskManager;
    }

    private DiskLruCache mDiskLruCache;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;// 50MB

    private DiskManager(Context context) {
        try {
            mContext = context;
            mDiskLruCache = DiskLruCache.open(FileUtils.getCacheDirectory(context, CACHE_PICTURE)
                    , 1, 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get path
     *
     * @return
     */
    public File get(String url) {
        String key = StringUtils.hashKeyFromUrl(url);
        return new File(FileUtils.getCacheDirectory(mContext, ConstKey.CACHE_PICTURE) + File
                .separator +
                key + ".0");
    }

    public void putDrawable(String url, InputStream is) {
        String key = StringUtils.hashKeyFromUrl(url);
        L.e(key + ">>>>>>>>>>>>>put");
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                FileUtils.writeFile(is, outputStream);
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}

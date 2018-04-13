package com.xiajue.dayxiaohua.net.xh;

import com.xiajue.dayxiaohua.bean.ContentBeans;
import com.xiajue.dayxiaohua.net.HttpConfig;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * xiaJue 2018/4/8创建
 */
public interface XhApiServer {
    /**
     * 获取笑话内容
     */
    @Headers("User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/29.0.1547.66 Safari/537.36")
    @GET("content/list.php?key=" + HttpConfig
            .INTERFACCE_KEY)
    Observable<Response<ContentBeans.RootObject>>
    getList(@Query("sort") String sort,
            @Query("page") String page,
            @Query("pagesize") String pageSize,
            @Query("time") String time);
}

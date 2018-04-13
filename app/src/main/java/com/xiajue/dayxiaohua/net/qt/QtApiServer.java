package com.xiajue.dayxiaohua.net.qt;

import com.xiajue.dayxiaohua.bean.ImageBeans;
import com.xiajue.dayxiaohua.net.HttpConfig;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * xiaJue 2018/4/8创建
 */
public interface QtApiServer {
    /**
     * 获取趣图内容
     */
//    @Headers("User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) " +
//            "Chrome/29.0.1547.66 Safari/537.36")
    @GET("img/text.php?key=" + HttpConfig
            .INTERFACCE_KEY)
    Observable<Response<ImageBeans.RootObject>>
    getList(@Query("sort") String sort,
            @Query("page") String page,
            @Query("pagesize") String pageSize,
            @Query("time") String time);

    @GET
    Observable<ResponseBody> download(@Url String url);
}

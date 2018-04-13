package com.xiajue.dayxiaohua.net;

/**
 * xiaJue 2018/3/6创建
 */
public class HttpConfig {
    public static final boolean CONNECT_INTERNET = true;
    public static final long HTTP_TIME = 20L;
    /**
     * http://v.juhe.cn/joke/img/text.php
     * http://v.juhe.cn/joke/content/list.php
     */
    public static final String BASE_URL = "http://v.juhe.cn/joke/";
    public static final String INTERFACCE_KEY = "2cb4277b2e203f178050d2dcabafe141";
    /**
     * 10002	该KEY无请求权限	102
     * 10003	KEY过期	103
     * 10004	错误的OPENID	104
     * 10007	未知的请求源	107
     * 10011	当前IP请求超过限制	111
     * 10012	请求超过次数限制	112
     * 10014	系统内部异常(调用充值类业务时，请务必联系客服或通过订单查询接口检测订单，避免造成损失)	114
     * 10020	接口维护	120
     * 10021	接口停用
     */
    public static long[] ERROR_CODE = {10002, 10003, 10004, 10007, 10011, 10012, 10014,
            10020, 10021};
    public static String[] ERROR_STRING = {"该KEY无请求权限", "KEY过期", "错误的OPENID", "未知的请求源",
            "当前IP请求超过限制", "很抱歉，今日应用使用总数超过接口的可用总数。明天再来吧", "系统内部异常", "接口维护", "接口停用"};

}

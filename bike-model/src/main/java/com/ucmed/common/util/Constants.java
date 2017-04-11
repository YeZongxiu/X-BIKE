package com.ucmed.common.util;

/**
 * Created by ucmed on 2017/3/15.
 */
public class Constants {
    public static final String DOMAIN = "http://test.ykfb.ucmed.cn";
    public static final String PARAMS = "params";
    public static final String API_NAME = "api_name";
    public static final String IP = "ip";
    public static final String USER_SESSION = "user_session";
    public static final String API_PARAMS_SESSION_ID = "session_id";
    public static final String API_PARAMS_USER_ID = "user_id";
    public static final String CODE = "code";
    public static final String CACHE_TAG = "bike.cache-";
    public static final Integer NUMBER_QUERY_CACHED_TIME_IN_SECOND = 30 * 60;

    public static final int API_RESPONSE_RESULT_RET_CODE_FAIL = 1;
    public static final int API_RESPONSE_RESULT_RET_CODE_SUCCESS = 0;

    public static final String API_EXCEPTION_MSG = "服务器异常，请稍后再试";

    public static final String USER_IMG_UPLOAD_URL = "/app/apache-tomcat-ykfb-17111/webapps/userImage/";
    public static final String USER_IMG_URL = "http://test.ykfb.ucmed.cn//userImage/";
    
    public static final String BIKE_TYPE_IMG_UPLOAD_URL = "/app/apache-tomcat-ykfb-17111/webapps/bikeTypeImage/";
    public static final String BIKE_TYPE_IMG_URL = "http://test.ykfb.ucmed.cn/bikeTypeImage/";

    //地球半径千米
    public static final Double R = (double) 6371;
    //20米距离
    public static final Double DIS = 0.02;
    //5000米距离
    public static final Double DIS2 = (double)5;
    //20000米距离
    public static final Double DIS3 = (double)20;

    //杭州经度
    public static final String HZ_LONGITUDE = "120.161693";
    //杭州纬度
    public static final String HZ_LATITUDE = "30.280059";

    public static final String BAIDUAK = "nry1ogpPPBHyTnftGh5mng8fbsCmV7v2";

    //微信公众号信息
    public static final String APP_ID = "wx19290816dd5f9de9";
    public static final String APP_SECRET = "388f844c770b7a7b53d4d8b445a5c781";

}

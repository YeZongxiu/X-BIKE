package com.ucmed.common.util;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ReturnMsg {

    public static final int FAIL = -100; // 失败
    public static final String FAIL_WX = "-100";

    public static final int SUCCESS = 200; // 成功
    public static final String SUCCESS_WX = "200";
    public static final String SUCCESS_MSG = "成功";

    public static final String SUCCESS_EVALUATION = "提交成功，谢谢您的合作！";
    public static final String FAIL_EVALUATION = "提交失败，请稍后重试！";

    public static final int PARAMETER_ERROR = 400; // 请求参数有误
    public static final String PARAMETER_ERROR_MSG = "请求参数有误";

    public static final int LOGIN_AFTER_ACCESS = 401; // 需要登录后访问
    public static final String LOGIN_AFTER_ACCESS_MSG = "登录超时，请重新登录";

    public static final int NO_BASEPATIENTLIST_SESSION = 402; // session失效
    public static final String NO_BASEPATIENTLIST_SESSION_MSG = "请重试";

    public static final int CAN_NO_FIND_API = 404; // 找不到对应的api
    public static final String CAN_NO_FIND_API_MSG = "找不到对应的api";

    public static final int NO_JSON = 405; // 不是一个json格式
    public static final String NO_JSON_MSG = "不是一个json格式";

    public static final int UNKNOWN_ERROR = 500; // 服务器发生未知错误
    public static final String UNKNOWN_ERROR_MSG = "服务器发生未知错误";

}

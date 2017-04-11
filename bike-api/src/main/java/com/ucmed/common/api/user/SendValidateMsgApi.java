package com.ucmed.common.api.user;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.MsgUtil;
import com.ucmed.common.util.StringUtil;
import com.ucmed.common.util.sendMsgUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ucmed on 2017/3/16.
 */
public class SendValidateMsgApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(SendValidateMsgApi.class);

    public static final String TAG = "api.hzpt.user.validate.msg";

    private MemcachedClient memcachedClient;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        String phone = params.optString("phone");
        if (!StringUtil.isMobileNO(phone)){
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", "手机号输入错误，请重新输入");
            return result;
        }
        String no = null;
        try {
            no = MsgUtil.getCachedNumber(memcachedClient, TAG, phone, 6);
            if(no == null) {
                result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
                result.put("ret_info", "验证码生成失败");
                return result;
            }
            String msg = "您正在进行用户注册操作，验证码是:" + no  + "，30分钟内有效，请勿将验证码告诉他人，如非本人操作，请忽略。";
            //TODO 测试期间不发送短信
            /*JSONObject msgResult = sendMsgUtil.sendSms(msg, phone);
            if(0 != msgResult.optInt("code")) {
                result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
                result.put("ret_info", msgResult.optString("msg"));
                result.put("phone", phone);
                return result;
            }*/
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
            result.put("ret_info", "已将验证码发送到您的手机,因为网络原因可能会有些延迟，请稍候!");
            result.put("phone", phone);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", Constants.API_EXCEPTION_MSG);
            return result;
        }
        return result;
    }

}

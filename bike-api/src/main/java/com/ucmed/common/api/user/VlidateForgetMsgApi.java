package com.ucmed.common.api.user;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.MsgUtil;
import com.ucmed.common.util.StringUtil;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by ucmed on 2017/3/17.
 */
public class VlidateForgetMsgApi implements Api {
    public static final String TAG = "api.hzpt.user.forget.msg";

    private static final Logger LOG = LoggerFactory.getLogger(VlidateForgetMsgApi.class);
    private UserService userService;

    private MemcachedClient memcachedClient;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        try {
            String valid = (String) params.get("valid");
            String phone = (String) params.get("phone");
            String password = (String) params.get("password");
            if (!StringUtil.isMobileNO(phone)) {
                return errorResult(result, "请输入正确的手机号码", "参数错误：手机号码不符合规则。");
            }
            if (StringUtils.isBlank(password)) {
                return errorResult(result, "请输入密码", "参数错误：密码未输入。");
            }
            UserModel userModel = userService.getUser(phone);
            // 已注册 用户存在且密码不为空
            if (userModel == null) {
                return errorResult(result, "您的手机号码未注册", "错误：未注册用户。");
            }
            if (StringUtils.isBlank(valid) || !(valid instanceof String)) {
                return errorResult(result, "验证码不能为空", "参数错误：验证码为空。");
            }
            String no = MsgUtil.getCachedMsg(memcachedClient, TAG, phone);
            if (!valid.equals(no)) {
                return errorResult(result, "验证码不正确", "参数错误：验证码不正确。");
            }
            userModel.setUpdateTime(new Date());
            userModel.setPassword(password);
            updateUser(userModel);
            Boolean isSuccess = userService.update(userModel);
            if (isSuccess){
                result = userModel.getJsonObject();
                result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
                result.put("ret_info", "密码修改成功");
                return result;
            }
            return errorResult(result, "密码修改失败，请稍后再试", "密码修改失败");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", Constants.API_EXCEPTION_MSG);
            return result;
        }
    }

    private void updateUser(UserModel user) {
        user.setLastLoginTime(new Date());
        user.setLoginTime(user.getLoginTime() == null ? 0 : user.getLoginTime() + 1);
    }

    private JSONObject errorResult(JSONObject res, String retInfo, String logString) {
        JSONObject result = res;
        if (result == null) {
            result = new JSONObject();
        }
        result.put("ret_code", 1);
        result.put("ret_info", retInfo);
        LOG.warn(logString);
        return result;
    }
}

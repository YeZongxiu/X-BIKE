package com.ucmed.common.api.user;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户登录API.
 * 
 * @author plz
 */
@Transactional
public class UserLoginApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(UserLoginApi.class);
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        if(params == null) {
            return errorResult(result, "登陆失败", "参数错误：参数为空，登陆失败。");
        }
        String phone = params.optString("phone");
        String password = params.optString("password");
        if (!StringUtil.isMobileNO(phone)) {
            return errorResult(result, "请输入正确的手机号码", "参数错误：手机号码不符合规则。");
        }
        if (StringUtils.isBlank(password)) {
            return errorResult(result, "请输入密码", "参数错误：密码未输入。");
        }
        UserModel user = userService.getUser(phone);
        if (user != null && password.equals(user.getPassword())){
            updateUser(user);
            Boolean isSuccess = userService.update(user);
            if (isSuccess){
                result = user.getJsonObject();
                result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
                result.put("ret_info", "登录成功");
                return result;
            }
        }
        return errorResult(result, "登陆失败,登陆的手机号或密码错误,请检查后重新输入", "登陆失败,登陆的手机号或密码错误");
    }

    private void updateUser(UserModel user) {
        user.setLastLoginTime(new Date());
        user.setLoginTime(user.getLoginTime() == null ? 0 : user.getLoginTime() + 1);
    }

    private JSONObject errorResult(JSONObject res,
            String retInfo, String logString) {
        if(res == null) {
            res = new JSONObject();
        }
        res.put("ret_code", 1);
        res.put("ret_info", retInfo);
        LOG.warn(logString);
        return res;
    }
}

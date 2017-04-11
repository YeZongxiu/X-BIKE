package com.ucmed.common.api.user;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ucmed on 2017/3/18.
 */
public class UserWallerApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(UserWallerApi.class);
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        if (params == null || params.isEmpty()){
            return errorResult(result, "失败，参数错误。", "错误：参数为空。");
        }
        Long userId = params.optLong("user_id");
        UserModel user = userService.getUserById(userId);
        if (null == user){
            return errorResult(result, "用户不存在。", "错误：用户不存在，用户" + userId + "为空");
        }
        result.put("deposit", null == user.getDeposit() ? 0 : user.getDeposit());
        result.put("wallet", null == user.getWallet() ? 0 : user.getWallet());
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取钱包金额成功");
        return  result;
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

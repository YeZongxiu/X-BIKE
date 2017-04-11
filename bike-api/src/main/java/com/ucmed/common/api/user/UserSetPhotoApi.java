package com.ucmed.common.api.user;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by ucmed on 2017/3/18.
 */
public class UserSetPhotoApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(UserRegistApi.class);
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
        try {
            JSONObject file_uploaded = (JSONObject) params.get("file_uploaded");
            if (null != file_uploaded){
                JSONArray array = file_uploaded.optJSONArray("file_paths");
                String realFileName = array.optString(0);
                result.put("photo", Constants.USER_IMG_URL + realFileName);
                user.setPhoto(realFileName);
                user.setUpdateTime(new Date());
                userService.update(user);
            }
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", Constants.API_EXCEPTION_MSG);
            return result;
        }
        result.put("ret_code", 0);
        result.put("ret_info", "用户设置头像成功");
        return result;
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

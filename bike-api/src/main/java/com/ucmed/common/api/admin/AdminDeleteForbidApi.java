package com.ucmed.common.api.admin;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ForbidSpaceModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.parking.ForbidSpaceService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by ucmed on 2017/3/18.
 */
public class AdminDeleteForbidApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDeleteForbidApi.class);
    private ForbidSpaceService forbidSpaceService;
    private UserService userService;

    public void setForbidSpaceService(ForbidSpaceService forbidSpaceService) {
        this.forbidSpaceService = forbidSpaceService;
    }

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
        if (!StringUtil.isNotBlank(user.getIsAdmin()) && !"1".equals(user.getIsAdmin())){
            return errorResult(result, "您不是管理员，不能添加车辆信息。", "错误：不是管理员，无法增加车辆");
        }
        String id = params.optString("id");
        ForbidSpaceModel model = forbidSpaceService.getForbidById(Long.parseLong(id));
        if (model == null)
            return errorResult(result, "删除车辆不存在，请稍后再试。", "错误：车辆不存在");
        forbidSpaceService.deleteById(Long.parseLong(id));
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "车辆信息删除成功");
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

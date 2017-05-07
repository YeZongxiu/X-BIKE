package com.ucmed.common.api.admin;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.parking.ForbidSpaceModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.parking.ForbidSpaceService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.DateUtil;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AdminEditForbidSpaceApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(AdminEditForbidSpaceApi.class);
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
            return errorResult(result, "您不是管理员，不能修改站点信息。", "错误：不是管理员，无法修改信息");
        }
        if (!StringUtil.isNotBlank(params.optString("radius"))){
            return errorResult(result, "未输入范围。", "错误：范围为空");
        }
        if (!StringUtil.isNotBlank(params.optString("start_time"))){
            return errorResult(result, "未输入开始禁停时间。", "错误：开始禁停时间为空");
        }
        if (!StringUtil.isNotBlank(params.optString("end_time"))){
            return errorResult(result, "未输入结束禁停时间。", "错误：结束禁停时间为空");
        }
        if (!StringUtil.isNotBlank(params.optString("id")) && (!StringUtil.isNotBlank(params.optString("longitude")) || !StringUtil.isNotBlank(params.optString("latitude")))){
            return errorResult(result, "禁停信息错误，请重新输入。", "错误：禁停信息输入错误");
        }
        String id = params.optString("id");
        Date start_time = DateUtil.StringToDate1(params.optString("start_time"));
        Date end_time = DateUtil.StringToDate1(params.optString("end_time"));
        ForbidSpaceModel model = new ForbidSpaceModel();
        model.setStartTime(start_time);
        model.setEndTime(end_time);
        model.setDistance(Double.parseDouble(params.optString("radius")));
        model.setMessage(params.optString("message"));
        if (StringUtil.isNotBlank(id)){
            model.setId(Integer.parseInt(id));
            ForbidSpaceModel old = forbidSpaceService.getForbidById(Long.parseLong(id));
            if (old == null)
                return errorResult(result, "禁停信息错误，修改信息不存在。", "错误：禁停信息不存在，无法修改");
        }
        else {
            model.setLatitude(params.optString("latitude"));
            model.setLongitude(params.optString("longitude"));
        }
        forbidSpaceService.editForbidSpaceInfo(model);
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "修改禁止停车点信息成功");
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

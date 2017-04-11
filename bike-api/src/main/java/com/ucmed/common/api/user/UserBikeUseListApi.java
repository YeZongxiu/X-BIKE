package com.ucmed.common.api.user;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucmed.common.api.bike.ReturnUseBikeApi;
import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.record.RecordModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.record.RecordService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.DateUtil;

public class UserBikeUseListApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(ReturnUseBikeApi.class);
	private UserService userService;
	private RecordService recordService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
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
        List<RecordModel> list = recordService.getUseList(userId);
        JSONArray array = new JSONArray();
        for (RecordModel recordModel : list) {
			JSONObject object = new JSONObject();
			object.put("start_time", DateUtil.dateToString4(recordModel.getStartTime()));
			object.put("bike_no", recordModel.getBikeNo());
			object.put("cost", recordModel.getCost());
			object.put("time", DateUtil.getMinuteBetweenTime(recordModel.getStartTime(), recordModel.getEndTime()) + "分钟");
			object.put("bike_type_name", recordModel.getBikeTypeName());
			array.add(object);
		}
        result.put("use_list", array);
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "查询成功");
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

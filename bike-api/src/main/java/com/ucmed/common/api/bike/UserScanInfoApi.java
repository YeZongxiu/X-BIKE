package com.ucmed.common.api.bike;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.record.RecordModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.service.record.RecordService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.DateUtil;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class UserScanInfoApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(UserScanInfoApi.class);
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
        List<RecordModel> recordModels = recordService.getRecordAndBike(userId);
        if (null == recordModels || recordModels.isEmpty()){
            return errorResult(result, "未查询到您借的车辆。", "错误：用户没有借车");
        }
        RecordModel model = recordModels.get(0);
        Date endDate = new Date();
        Date startDate = model.getStartTime();
        Long between = endDate.getTime() - startDate.getTime();
        Long hour = between / (60 * 60 * 1000);
        Long minute = (between - hour * 60 * 60 * 1000)/(60 * 1000);
        Long second = (between - hour * 60 * 60 * 1000 - minute * 60 * 1000)/1000;
        StringBuffer time = new StringBuffer();
        if (hour > 0){
            time.append(hour + "小时");
        }
        if ((hour * 60) < (between/(60 * 1000))){
            hour = hour + 1;
        }
        Integer cost = Integer.valueOf(hour.toString());
        time.append(minute + "分钟");
        time.append(second + "秒");
        result.put("time", time.toString());
        result.put("cost", cost);
        result.put("start_time", DateUtil.dateToString4(model.getStartTime()));
        result.put("bike_no", model.getBikeNo());
        result.put("bike_type_name", model.getBikeTypeName());
        result.put("two_bar_codes", model.getTwoBarCodes());
        result.put("password", model.getPassword());
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

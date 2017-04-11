package com.ucmed.common.api.admin;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;

import java.util.Date;

/**
 * Created by ucmed on 2017/3/18.
 */
public class AdminDeleteBikeApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDeleteBikeApi.class);
    private UserService userService;
    private BikeService bikeService;
    private ParkingSpaceService parkingSpaceService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    public void setParkingSpaceService(ParkingSpaceService parkingSpaceService) {
		this.parkingSpaceService = parkingSpaceService;
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
        Long typeId = params.optLong("type_id");
        String bikeNo = params.optString("bike_no");
        String twoBarCodes = params.optString("two_bar_codes");
        if (null == typeId || (null == twoBarCodes && null == bikeNo)){
            return errorResult(result, "未选择车辆。", "错误：车辆信息未填");
        }
        BikeModel bike = new BikeModel();
        if (StringUtil.isNotBlank(bikeNo)) {
        	bike = bikeService.getBikeByBikeNo(bikeNo, typeId);
		} else {
			bike = bikeService.getBikeByTwoBarCodes(twoBarCodes, typeId);
		}
        if (null == bike || "1".equals(bike.getIsDelete())){
            return errorResult(result, "不存在此辆车信息。", "错误：车辆不存在");
        }
        if ("1".equals(bike.getStatus())){
            return errorResult(result, "车辆未归还，不可删除。", "错误：车辆还没还回");
        }
        if (null != bike.getParkId()) {
        	ParkingSpaceModel model = parkingSpaceService.getParkById(bike.getParkId());
        	if (model.getBikeNumber() > 0) {
				model.setBikeNumber(model.getBikeNumber() - 1);
				parkingSpaceService.updatePark(model);
			}
		}
        bike.setUpdateTime(new Date());
        bike.setIsDelete("1");
        bike.setParkId(null);
        bikeService.updateBike(bike);
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

package com.ucmed.common.api.admin;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucmed on 2017/3/18.
 */
public class AdminAddParkingApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(AdminAddParkingApi.class);
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
            return errorResult(result, "您不是管理员，不能添加车辆信息。", "不是管理员，无法增加车辆");
        }
        String longitude = params.optString("longitude");
        String latitude = params.optString("latitude");
        //如果没有传入停车数量，则默认为10
        Integer number = params.optInt("number", 10);
        if (null == longitude || null == latitude){
            return errorResult(result, "位置信息错误。", "经纬度为空");
        }
        ParkingSpaceModel oldParkingSpace = parkingSpaceService.getSameParking(longitude, latitude);
        if (null != oldParkingSpace){
            return errorResult(result, "站点信息已存在。", "错误：站点信息已存在");
        }
        Integer count = 0;
        List<BikeModel> bikeModels = new ArrayList<>();
        try {
			Double longDouble = Double.valueOf(longitude);
			Double latiDouble = Double.valueOf(latitude);
			bikeModels = bikeService.getBikeNumber(longDouble, latiDouble);
            count = bikeModels.size();
        } catch (Exception e) {
			return errorResult(result, "坐标输入错误", "坐标错误");
		}
        ParkingSpaceModel model = addBikeInfo(count, latitude, longitude, number);
        if (null != bikeModels && !bikeModels.isEmpty()){
            for (BikeModel bikeModel : bikeModels) {
                bikeModel.setParkId(model.getId());
                bikeService.updateBike(bikeModel);
            }
        }
        if (null != model){
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
            result.put("ret_info", "站点添加成功");
        } else {
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", "站点添加失败");
        }
        return result;
    }

    private ParkingSpaceModel addBikeInfo(Integer count, String latitude, String longitude, Integer number){
    	ParkingSpaceModel model = new ParkingSpaceModel();
        model.setBikeNumber(count.longValue());
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        model.setParkNumber(number.longValue());
        parkingSpaceService.addParkingInfo(model);
        boolean isSuccess = false;
        isSuccess = model.getId() != null;
        if (isSuccess) {
            return model;
        } else {
            return null;
        }
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

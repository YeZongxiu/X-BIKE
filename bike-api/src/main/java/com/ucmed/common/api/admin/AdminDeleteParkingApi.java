package com.ucmed.common.api.admin;

import java.util.List;

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

/**
 * Created by ucmed on 2017/3/18.
 */
public class AdminDeleteParkingApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDeleteParkingApi.class);
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
        String longitude = params.optString("longitude");
        String latitude = params.optString("latitude");
        if (null == longitude || null == latitude){
            return errorResult(result, "位置信息错误。", "错误：经纬度为空");
        }
        ParkingSpaceModel oldParkingSpace = parkingSpaceService.getSameParking(longitude, latitude);
        if (null == oldParkingSpace){
            return errorResult(result, "删除的站点不存在。", "错误：站点信息不存在");
        }
        List<BikeModel> bikeModels = bikeService.getBikesByPark(oldParkingSpace.getId());
        parkingSpaceService.deletePark(oldParkingSpace.getId());
        if (null != bikeModels && !bikeModels.isEmpty()){
            for (BikeModel bikeModel : bikeModels) {
                bikeModel.setParkId(null);
                bikeService.updateBike(bikeModel);
            }
        }
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "删除站点成功");
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

package com.ucmed.common.api.admin;

import java.util.Date;
import java.util.List;

import com.ucmed.common.service.bike.BikeTypeService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.bike.BikeTypeModel;
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
public class AdminAddBikeApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(AdminAddBikeApi.class);
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
        String password = params.optString("password");
        String longitude = params.optString("longitude");
        String latitude = params.optString("latitude");
        String twoBarCodes = params.optString("two_bar_codes");
        if (null == typeId || !StringUtil.isNotBlank(password) || !StringUtil.isNotBlank(twoBarCodes) || !StringUtil.isNotBlank(bikeNo)){
            return errorResult(result, "车辆信息未填写。", "错误：车辆信息未填写");
        }
        BikeTypeModel model = new BikeTypeModel();
        model.setId(typeId);
        if (!equals(model)){
            return errorResult(result, "车辆类型选择错误。", "错误：选择了未合作的车辆类型");
        }
        if (null == longitude || null == latitude){
            return errorResult(result, "位置信息错误。", "错误：经纬度为空");
        }
        BikeModel bike = bikeService.getBikeByBikeNo(bikeNo, typeId);
        BikeModel bike2 = bikeService.getBikeByTwoBarCodes(twoBarCodes, typeId);
        List<ParkingSpaceModel> models = parkingSpaceService.getParkingDetail(Double.valueOf(longitude), Double.valueOf(latitude));
        if (null != bike || null != bike2){
            if (bike.equals(bike2) && "1".equals(bike.getIsDelete())){
                bike.setIsDelete("0");
                if (null != models && !models.isEmpty()) {
                    ParkingSpaceModel parkingSpaceModel = parkingSpaceService.getParkById(models.get(0).getId());
                    parkingSpaceModel.setBikeNumber(parkingSpaceModel.getBikeNumber() + 1);
                    parkingSpaceService.updatePark(parkingSpaceModel);
                }
                bike.setPassword(password);
                bike.setParkId(models.get(0).getId());
                bikeService.updateBike(bike);
                result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
                result.put("ret_info", "车辆添加成功");
                return  result;
            }
            return errorResult(result, "车辆信息已存在。", "错误：已存在车辆");
        }
        BikeModel bikeModel = new BikeModel();
        if (null != models && !models.isEmpty()) {
        	bikeModel = addBikeInfo(typeId, bikeNo, password, longitude, latitude, twoBarCodes, models.get(0).getId());
            ParkingSpaceModel parkingSpaceModel = parkingSpaceService.getParkById(models.get(0).getId());
            parkingSpaceModel.setBikeNumber(parkingSpaceModel.getBikeNumber() + 1);
            parkingSpaceService.updatePark(parkingSpaceModel);
		} else {
			bikeModel = addBikeInfo(typeId, bikeNo, password, longitude, latitude, twoBarCodes, null);
		}
        if (null != bikeModel){
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
            result.put("ret_info", "车辆添加成功");
        } else {
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", "车辆添加失败");
        }
        return result;
    }

    private BikeModel addBikeInfo(Long typeId, String bikeNo, String password, String longitude, String latitude, String twoBarCodes, Long parkId){
        BikeModel bikeModel = new BikeModel();
        bikeModel.setPassword(password);
        bikeModel.setBikeNo(bikeNo);
        bikeModel.setBikeTypeId(typeId);
        bikeModel.setLatitude(latitude);
        bikeModel.setLongitude(longitude);
        bikeModel.setTwoBarCodes(twoBarCodes);
        bikeModel.setParkId(parkId);
        bikeModel.setCreateTime(new Date());
        bikeService.addBikeInfo(bikeModel);
        boolean isSuccess = false;
        isSuccess = bikeModel.getId() != null;
        if (isSuccess) {
            return bikeModel;
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

    private BikeTypeService bikeTypeService;
    public void setBikeTypeService(BikeTypeService bikeTypeService) {
        this.bikeTypeService = bikeTypeService;
    }

    public boolean equals(BikeTypeModel bikeTypeModel) {
        List<BikeTypeModel> list = bikeTypeService.getTypeList();
        for (BikeTypeModel model: list) {
            if (bikeTypeModel.getId().equals(model.getId())){
                return  true;
            }
        }
        return false;
    }
}

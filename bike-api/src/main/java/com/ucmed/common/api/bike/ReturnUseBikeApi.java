package com.ucmed.common.api.bike;

import java.util.Date;
import java.util.List;

import com.ucmed.common.model.parking.ForbidSpaceModel;
import com.ucmed.common.service.parking.ForbidSpaceService;
import com.ucmed.common.util.GetDistanceUtil;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class ReturnUseBikeApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(ReturnUseBikeApi.class);
	private UserService userService;
	private BikeService bikeService;
	private RecordService recordService;
	private ParkingSpaceService parkingSpaceService;
    private ForbidSpaceService forbidSpaceService;

    public void setForbidSpaceService(ForbidSpaceService forbidSpaceService) {
        this.forbidSpaceService = forbidSpaceService;
    }

    public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBikeService(BikeService bikeService) {
		this.bikeService = bikeService;
	}

	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
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
        String longitude = params.optString("longitude");
        String latitude = params.optString("latitude");
        if (null == longitude || null == latitude){
            return errorResult(result, "位置信息错误。", "经纬度为空");
        }
        RecordModel record = recordService.getRecordByUserId(userId);
        if (null == record) {
        	return errorResult(result, "对不起，您未借出车辆，无需归还。", "错误：用户没有借车");
		}
        BikeModel bikeModel = bikeService.getBikeById(record.getBikeId());
        if (null == bikeModel) {
        	return errorResult(result, "对不起，此车非本系统车辆。", "错误：数据库中未匹配到这辆车");
		}
        List<ForbidSpaceModel> forbidSpaceModels = forbidSpaceService.getForbid(Double.parseDouble(longitude), Double.parseDouble(latitude));
        if (forbidSpaceModels != null && !forbidSpaceModels.isEmpty()){
            for (ForbidSpaceModel forbidSpace: forbidSpaceModels) {
                Double distance = GetDistanceUtil.getDistanceByGPS(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(forbidSpace.getLongitude()), Double.parseDouble(forbidSpace.getLatitude()));
                if (distance <= forbidSpace.getDistance()){
                    return errorResult(result, forbidSpace.getMessage(), "错误：车辆在禁停区域");
                }
            }
        }
        Date endDate = new Date();
        Date startDate = record.getStartTime();
        Long between = endDate.getTime() - startDate.getTime();
        Long hour = between / (60 * 60 * 1000);
        if ((hour * 60) <= (between/(60 * 1000))){
            hour = hour + 1;
        }
        Integer cost = 0;
        String isBluetooth = params.optString("is_bluetooth");
        if (null == isBluetooth || "0".equals(isBluetooth) ){
            cost = Integer.valueOf(hour.toString());
        }
        record.setCost(cost);
        record.setEndTime(endDate);
        recordService.updateRecord(record);
        bikeModel.setStatus("0");
        List<ParkingSpaceModel> models = parkingSpaceService.getParkingDetail(Double.valueOf(longitude), Double.valueOf(latitude));
        if (null == models || models.isEmpty()) {
			bikeModel.setParkId(null);
		} else {
			bikeModel.setParkId(models.get(0).getId());
			ParkingSpaceModel parkingSpaceModel = models.get(0);
			parkingSpaceModel.setBikeNumber(parkingSpaceModel.getBikeNumber() + 1);
			parkingSpaceService.updatePark(parkingSpaceModel);
		}
        bikeModel.setUpdateTime(new Date());
        bikeService.updateBike(bikeModel);
        user.setWallet(user.getWallet() - cost);
        userService.update(user);
        result.put("start_time", DateUtil.dateToString4(record.getStartTime()));
        result.put("time", between / (60 * 1000) + "分钟");
        result.put("cost", cost);
        result.put("wallet", user.getWallet());
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "还车成功");
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

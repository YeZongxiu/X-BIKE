package com.ucmed.common.api.bike;

import java.util.Date;

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
import com.ucmed.common.util.StringUtil;

public class ScanUseBikeApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(ScanUseBikeApi.class);
	private UserService userService;
	private BikeService bikeService;
	private RecordService recordService;
	private ParkingSpaceService parkingSpaceService;
	
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
        if (user.getDeposit() == 0) {
        	return errorResult(result, "请先充值押金。", "错误：押金为0");
		}
        if (user.getWallet() <= 0) {
        	return errorResult(result, "你的钱包额度为0，请先充值。", "错误：钱包额度为0");
		}
        String bikeNo = params.optString("bike_no");
        String twoBarCodes = params.optString("two_bar_codes");
        Long typeId = params.optLong("type_id");
        if (null == typeId || (null == twoBarCodes && null == bikeNo)){
            return errorResult(result, "请选择车辆。", "车辆信息未填写");
        }
        RecordModel record = recordService.getRecordByUserId(userId);
        if (null != record) {
        	return errorResult(result, "对不起，请先归还车辆。", "错误：未归还车辆");
		}
        BikeModel bikeModel = new BikeModel();
        if (StringUtil.isNotBlank(bikeNo)) {
        	bikeModel = bikeService.getBikeByBikeNo(bikeNo, typeId);
		} else {
			bikeModel = bikeService.getBikeByTwoBarCodes(twoBarCodes, typeId);
		}
        if (null == bikeModel || "1".equals(bikeModel.getIsDelete())) {
        	return errorResult(result, "对不起，此车非本系统车辆。", "错误：数据库中未匹配到这辆车");
		}
        if ("1".equals(bikeModel.getStatus())) {
        	return errorResult(result, "对不起，本车已借出。", "错误：车辆已借出");
		}
        if ("2".equals(bikeModel.getStatus())) {
        	return errorResult(result, "故障车辆，请您重新选择车辆", "错误：故障车辆");
		}
        if (null != bikeModel.getParkId()) {
        	ParkingSpaceModel parkingSpaceModel = parkingSpaceService.getParkById(bikeModel.getParkId());
        	parkingSpaceModel.setBikeNumber(parkingSpaceModel.getBikeNumber() - 1);
        	parkingSpaceService.updatePark(parkingSpaceModel);
		}
        RecordModel recordModel = new RecordModel();
        recordModel.setStartTime(new Date());
        recordModel.setBikeId(bikeModel.getId());
        recordModel.setUserId(userId);
        recordService.addRecordInfo(recordModel);
        bikeModel.setParkId(null);
        bikeModel.setUpdateTime(new Date());
        bikeModel.setStatus("1");
        bikeService.updateBike(bikeModel);
        result.put("password", bikeModel.getPassword());
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "借车成功，请妥善保管车辆密码");
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

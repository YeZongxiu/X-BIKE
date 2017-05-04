package com.ucmed.common.api.bike;

import java.util.List;

import com.ucmed.common.service.fix.FixOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.util.Constants;

public class GetParkingBikeApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(GetParkingBikeApi.class);
	private BikeService bikeService;
	private ParkingSpaceService parkingSpaceService;
    private FixOrderService fixOrderService;

    public void setFixOrderService(FixOrderService fixOrderService) {
        this.fixOrderService = fixOrderService;
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
        String longitude = params.optString("longitude");
        String latitude = params.optString("latitude");
        if (null == longitude || null == latitude){
            return errorResult(result, "位置信息错误。", "经纬度为空");
        }
        List<ParkingSpaceModel> parkingSpaceModels = parkingSpaceService.getParkBike(Double.valueOf(longitude), Double.valueOf(latitude));
        if (null == parkingSpaceModels || parkingSpaceModels.isEmpty()) {
			result.put("parking_list", new JSONArray());
		} else {
            JSONArray array = new JSONArray();
            JSONArray fixArray = new JSONArray();
            for (ParkingSpaceModel parkingSpaceModel : parkingSpaceModels) {
                JSONObject obj = parkingSpaceModel.getJsonObject();
                List<BikeModel> bikes = bikeService.getBikesByPark(parkingSpaceModel.getId());
                if (null == bikes || bikes.isEmpty()){
                    obj.put("parking_bike_list", new JSONArray());
                } else {
                    obj.put("parking_bike_list", JSONArray.fromObject(bikes));
                }
                fixArray = fixOrderService.getFixOrderByPark(parkingSpaceModel.getId());
                obj.put("fix_bike_list", fixArray);
                array.add(obj);
            }
            result.put("parking_list", array);
		}
        /*List<BikeModel> bikeModels = bikeService.getBikeInfo(Double.valueOf(longitude), Double.valueOf(latitude));
        if (null == bikeModels || bikeModels.isEmpty()) {
			result.put("bike_list", new JSONArray());
		} else {
			result.put("bike_list", JSONArray.fromObject(bikeModels));
		}
        result.put("fix_bike_list", fixOrderService.getBikeFixList(Double.valueOf(longitude), Double.valueOf(latitude)));*/
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取车辆站点信息成功");
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

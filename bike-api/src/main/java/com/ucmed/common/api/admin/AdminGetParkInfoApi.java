package com.ucmed.common.api.admin;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminGetParkInfoApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(AdminGetParkInfoApi.class);
	private BikeService bikeService;
	private ParkingSpaceService parkingSpaceService;

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
        List<ParkingSpaceModel> parkingSpaceModels = parkingSpaceService.selectAllPrak();
        if (null == parkingSpaceModels || parkingSpaceModels.isEmpty()) {
			result.put("parking_list", new JSONArray());
		} else {
            JSONArray array = new JSONArray();
            for (ParkingSpaceModel parkingSpaceModel : parkingSpaceModels) {
                JSONObject obj = JSONObject.fromObject(parkingSpaceModel);
                List<BikeModel> bikes = bikeService.getBikesByPark(parkingSpaceModel.getId());
                if (null == bikes || bikes.isEmpty()){
                    obj.put("parking_bike_list", new JSONArray());
                } else {
                    obj.put("parking_bike_list", JSONArray.fromObject(bikes));
                }
                array.add(obj);
            }
            result.put("parking_list", array);
		}
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取站点信息成功");
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

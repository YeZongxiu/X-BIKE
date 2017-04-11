package com.ucmed.common.api.bike;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.service.bike.BikeTypeService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by ucmed on 2017/3/18.
 */
public class GetBikeTypeApi implements Api {
    private BikeTypeService bikeTypeService;
    public void setBikeTypeService(BikeTypeService bikeTypeService) {
        this.bikeTypeService = bikeTypeService;
    }
    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        List<BikeTypeModel> list = bikeTypeService.getTypeList();
        if (null == list || list.isEmpty()){
            result.put("list", new JSONArray());
        }else{
            JSONArray array = new JSONArray();
            for (BikeTypeModel model : list){
                array.add(model.toJsonObject());
            }
            result.put("list", array);
        }
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取车辆品牌成功");
        return  result;
    }
}

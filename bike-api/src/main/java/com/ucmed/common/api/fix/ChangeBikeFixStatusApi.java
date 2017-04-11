package com.ucmed.common.api.fix;

import com.ucmed.common.api.bike.GetParkingBikeApi;
import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.service.fix.FixOrderService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wzyk88 on 2017/3/28.
 */
public class ChangeBikeFixStatusApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(GetParkingBikeApi.class);
    private FixOrderService fixOrderService;

    public void setFixOrderService(FixOrderService fixOrderService) {
        this.fixOrderService = fixOrderService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        if (params == null || params.isEmpty()){
            return errorResult(result, "失败，参数错误。", "错误：参数为空。");
        }
        String status = params.optString("status");
        Long id = Long.parseLong(params.optString("id"));
        if (null == id){
            return errorResult(result, "报修车辆信息错误。", "报修id为空");
        }
        FixOrderModel.FixStatus fixStatus = FixOrderModel.FixStatus.equals(id);
        if (null == status || null == fixStatus){
            return errorResult(result, "车辆状态错误。", "报修车辆状态错误");
        }
        FixOrderModel model = fixOrderService.selectById(id);
        if (null == model){
            return errorResult(result, "报修车辆信息错误。", "未找到该报修车辆信息");
        }
        fixOrderService.updateFixOrder(id, status);
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "报修状态修改成功");
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

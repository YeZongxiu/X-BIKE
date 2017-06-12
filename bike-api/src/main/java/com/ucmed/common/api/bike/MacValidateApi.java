package com.ucmed.common.api.bike;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.record.RecordModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.parking.BluetoothService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.service.record.RecordService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class MacValidateApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(MacValidateApi.class);
    private BluetoothService bluetoothService;

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    @Override
	public JSONObject execute(JSONObject params) {
		JSONObject result = new JSONObject();
        if (params == null || params.isEmpty()){
            return errorResult(result, "失败，参数错误。", "错误：参数为空。");
        }
        String mac = params.optString("mac");
        BluetoothModel model = bluetoothService.getBluetoothByMac(mac);
        if (model != null) {
            return errorResult(result, "失败，非本系统蓝牙。", "错误：非本系统蓝牙");
        } else {
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
            result.put("ret_info", "蓝牙验证完成");
        }
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

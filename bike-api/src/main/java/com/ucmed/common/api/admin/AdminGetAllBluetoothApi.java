package com.ucmed.common.api.admin;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.fix.FixOrderService;
import com.ucmed.common.service.parking.BluetoothService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminGetAllBluetoothApi implements Api{
	private static final Logger LOG = LoggerFactory.getLogger(AdminGetAllBluetoothApi.class);
    private UserService userService;
    private BluetoothService bluetoothService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
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
            return errorResult(result, "您不是管理员，不能查看全部蓝牙信息。", "错误：不是管理员，无法查看全部去蓝牙信息");
        }
        String type = params.optString("type");
        List<BluetoothModel> bluetoothModels = bluetoothService.getBluetoothList(type);
        if (null == bluetoothModels || bluetoothModels.isEmpty()) {
			result.put("bluetooth_list", new JSONArray());
		} else {
            result.put("bluetooth_list", JSONArray.fromObject(bluetoothModels));
		}
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取蓝牙信息成功");
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

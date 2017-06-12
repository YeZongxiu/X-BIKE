package com.ucmed.common.api.admin;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.bike.BikeTypeService;
import com.ucmed.common.service.parking.BluetoothService;
import com.ucmed.common.service.parking.ParkingSpaceService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by ucmed on 2017/3/18.
 */
public class AdminAddBluetoothApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(AdminAddBluetoothApi.class);
    private UserService userService;
    private BluetoothService bluetoothService;

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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
            return errorResult(result, "您不是管理员，不能添加车辆信息。", "错误：不是管理员，无法添加蓝牙信息");
        }
        String bluetoothNo = params.optString("bluetooth_no");
        String mac = params.optString("mac");
        if (StringUtils.isEmpty(bluetoothNo) || StringUtils.isEmpty(mac)){
            return errorResult(result, "蓝牙信息未填写。", "错误：蓝牙信息未填写完全");
        }
        if (bluetoothService.getBluetoothByMac(mac) != null) {
            return errorResult(result, "蓝牙Mac已存在，请确认后再填写。", "错误：蓝牙Mac已存在");
        }
        if (bluetoothService.getBluetoothByNo(bluetoothNo) != null) {
            return errorResult(result, "蓝牙编号已存在，请确认后再填写。", "错误：蓝牙编号已存在");
        }
        BluetoothModel bluetoothModel = new BluetoothModel();
        bluetoothModel.setMac(mac);
        bluetoothModel.setBluetoothNo(bluetoothNo);
        bluetoothService.addBluetooth(bluetoothModel);
        if (bluetoothModel.getId() != null){
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
            result.put("ret_info", "蓝牙信息添加成功");
        } else {
            return errorResult(result, "蓝牙信息添加失败，请稍后再试。", "错误：蓝牙信息添加失败，系统异常");
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

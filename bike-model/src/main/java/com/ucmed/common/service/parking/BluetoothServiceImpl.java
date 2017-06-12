package com.ucmed.common.service.parking;

import com.ucmed.common.dao.parking.BluetoothMapper;
import com.ucmed.common.dataobj.parking.Bluetooth;
import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.util.ModelDataObjectUtil;
import com.ucmed.common.util.StringUtil;

/**
 * Created by ucmed on 2017/3/16.
 */
public class BluetoothServiceImpl implements BluetoothService {
	private BluetoothMapper bluetoothMapper;

	public void setBluetoothMapper(BluetoothMapper bluetoothMapper) {
		this.bluetoothMapper = bluetoothMapper;
	}

	@Override
	public BluetoothModel getBluetoothByMac(String mac) {
		BluetoothModel model = null;
		Bluetooth bluetooth;
		if (StringUtil.isNotBlank(mac)){
			bluetooth = bluetoothMapper.selectByMac(mac);
			if (bluetooth != null)
				model =  ModelDataObjectUtil.model2do(bluetooth, BluetoothModel.class);
		}
		return model;
	}

	@Override
	public void addBluetooth(BluetoothModel model) {
		Bluetooth bluetooth = ModelDataObjectUtil.model2do(model, Bluetooth.class);
		bluetoothMapper.insertSelective(bluetooth);
		model.setId(bluetooth.getId());
	}

	@Override
	public BluetoothModel getBluetoothByNo(String no) {
		BluetoothModel model = null;
		Bluetooth bluetooth;
		if (StringUtil.isNotBlank(no)){
			bluetooth = bluetoothMapper.getBluetoothByNo(no);
			if (bluetooth != null)
				model =  ModelDataObjectUtil.model2do(bluetooth, BluetoothModel.class);
		}
		return model;
	}
}

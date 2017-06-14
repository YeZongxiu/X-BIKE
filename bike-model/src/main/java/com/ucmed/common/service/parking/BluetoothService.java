package com.ucmed.common.service.parking;

import com.ucmed.common.model.parking.BluetoothModel;

import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface BluetoothService {
    public BluetoothModel getBluetoothByMac(String mac);

    public BluetoothModel getBluetoothByNo(String no);

    public void addBluetooth(BluetoothModel model);

    public List<BluetoothModel> getBluetoothList(String type);
}

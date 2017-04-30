package com.ucmed.common.service.parking;

import com.ucmed.common.model.parking.BluetoothModel;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface BluetoothService {
    public BluetoothModel getBluetoothByMac(String mac);
}

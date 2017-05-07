package com.ucmed.common.service.parking;

import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.model.parking.ForbidSpaceModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;

import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface ForbidSpaceService {
    public void editForbidSpaceInfo(ForbidSpaceModel model);

    public List<ForbidSpaceModel> getForbidSpaceList();

    public ForbidSpaceModel getForbidById(Long id);

    public List<ForbidSpaceModel> getForbid(Double longitude, Double latitude);

    public void deleteById(Long id);
}

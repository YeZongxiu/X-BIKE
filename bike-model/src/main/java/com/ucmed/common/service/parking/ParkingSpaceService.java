package com.ucmed.common.service.parking;

import java.util.List;

import com.ucmed.common.model.parking.ParkingSpaceModel;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface ParkingSpaceService {
    public void addParkingInfo(ParkingSpaceModel model);

    public List<ParkingSpaceModel> getParkingDetail(Double longitude, Double latitude);

    public List<ParkingSpaceModel> getParkBike(Double longitude, Double latitude);

    public void updatePark(ParkingSpaceModel model);

    public ParkingSpaceModel getParkById(Long parkId);

    public ParkingSpaceModel getSameParking(String longitude, String latitude);
    
    public void deletePark(Long parkId);

    public List<ParkingSpaceModel> selectAllPrak();

}

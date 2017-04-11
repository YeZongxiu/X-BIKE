package com.ucmed.common.service.bike;

import java.util.List;

import com.ucmed.common.model.bike.BikeModel;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface BikeService {
    public void addBikeInfo(BikeModel model);
    
    public List<BikeModel> getBikeNumber(Double longitude, Double latitude);
    
    public List<BikeModel> getBikeInfo(Double longitude, Double latitude);
    
    public BikeModel getBikeByBikeNo(String bikeNo, Long typeId);
    
    public BikeModel getBikeByTwoBarCodes(String twoBarCodes, Long typeId);
    
    public void updateBike(BikeModel model);
    
    public BikeModel getBikeById(Long bikeId);
    
    public List<BikeModel> getBikesByPark(Long parkId);
    
    public void deleteBike(Long bikeId);

    public List<BikeModel> getBikesFixByPark(Long parkId);
}

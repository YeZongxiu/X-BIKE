package com.ucmed.common.service.bike;

import java.util.ArrayList;
import java.util.List;

import com.ucmed.common.dao.bike.BikeMapper;
import com.ucmed.common.dataobj.bike.Bike;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.ModelDataObjectUtil;

/**
 * Created by ucmed on 2017/3/16.
 */
public class BikeServiceImpl implements BikeService {
    private BikeMapper bikeMapper;
    public void setBikeMapper(BikeMapper bikeMapper) {
        this.bikeMapper = bikeMapper;
    }

    @Override
    public void addBikeInfo(BikeModel model) {
        Bike bike = ModelDataObjectUtil.model2do(model, Bike.class);
        bikeMapper.insertSelective(bike);
        model.setId(bike.getId());
    }

	@Override
	public List<BikeModel> getBikeNumber(Double longitude, Double latitude) {
		Double dlng = 2*Math.asin(Math.sin(Constants.DIS/(2*Constants.R))/Math.cos(latitude*Math.PI/180));
		dlng = dlng * 180 / Math.PI;
		Double dlat = Constants.DIS / Constants.R;
		dlat = dlat * 180 / Math.PI;
		Double minlat;
		Double maxlat;
		Double minlng;
		Double maxlng;
		if (dlat>=0){
			minlat =latitude-dlat;
			maxlat = latitude+dlat;
		} else {
			minlat =latitude+dlat;
			maxlat = latitude-dlat;
		}
		if (dlng>=0){
			minlng = longitude -dlng;
			maxlng = longitude + dlng;
		} else {
			minlng = longitude + dlng;
			maxlng = longitude - dlng;
		}
		List<Bike> bikes = bikeMapper.selectAcount(minlng, maxlng, minlat, maxlat);
		if (bikes.isEmpty() || null == bikes){
			return new ArrayList<BikeModel>();
		}
		List<BikeModel> models = new ArrayList<>();
		for (Bike bike : bikes) {
			models.add(ModelDataObjectUtil.model2do(bike, BikeModel.class));
		}
		return models;
	}

	@Override
	public List<BikeModel> getBikeInfo(Double longitude, Double latitude) {
		Double dlng = 2*Math.asin(Math.sin(Constants.DIS2/(2*Constants.R))/Math.cos(latitude*Math.PI/180));
		dlng = dlng * 180 / Math.PI;
		Double dlat = Constants.DIS2 / Constants.R;
		dlat = dlat * 180 / Math.PI;
		Double minlat;
		Double maxlat;
		Double minlng;
		Double maxlng;
		if (dlat>=0){
			minlat =latitude-dlat;
			maxlat = latitude+dlat;
		} else {
			minlat =latitude+dlat;
			maxlat = latitude-dlat;
		}
		if (dlng>=0){
			minlng = longitude -dlng;
			maxlng = longitude + dlng;
		} else {
			minlng = longitude + dlng;
			maxlng = longitude - dlng;
		}
		List<Bike> list = bikeMapper.selectOutParkBike(minlng, maxlng, minlat, maxlat);
		if (null == list || list.isEmpty()) {
			return new ArrayList<BikeModel>();
		}
		List<BikeModel> models = new ArrayList<>();
		for (Bike bike : list) {
			BikeModel model = ModelDataObjectUtil.model2do(bike, BikeModel.class);
			model.setBikeTypeUrl(Constants.BIKE_TYPE_IMG_URL + bike.getBikeTypeUrl());
			models.add(model);
		}
		return models;
	}

	@Override
	public BikeModel getBikeByBikeNo(String bikeNo, Long typeId) {
		Bike bike = bikeMapper.getBikeByBikeNo(bikeNo, typeId);
		return ModelDataObjectUtil.model2do(bike, BikeModel.class);
	}

	@Override
	public BikeModel getBikeByTwoBarCodes(String twoBarCodes, Long typeId) {
		Bike bike = bikeMapper.getBikeByTwoBarCodes(twoBarCodes, typeId);
		return ModelDataObjectUtil.model2do(bike, BikeModel.class);
	}

	@Override
	public void updateBike(BikeModel model) {
		bikeMapper.updateByPrimaryKey(ModelDataObjectUtil.model2do(model, Bike.class));
	}

	@Override
	public BikeModel getBikeById(Long bikeId) {
		Bike bike = bikeMapper.selectByPrimaryKey(bikeId);
		return ModelDataObjectUtil.model2do(bike, BikeModel.class);
	}

	@Override
	public List<BikeModel> getBikesByPark(Long parkId) {
		List<Bike> list = bikeMapper.getBikesByPark(parkId);
		if (null == list || list.isEmpty()) {
			return new ArrayList<BikeModel>();
		}
		List<BikeModel> models = new ArrayList<>();
		for (Bike bike : list) {
			models.add(ModelDataObjectUtil.model2do(bike, BikeModel.class));
		}
		return models;
	}

	@Override
	public void deleteBike(Long bikeId) {
		bikeMapper.deleteByPrimaryKey(bikeId);
	}

	@Override
	public List<BikeModel> getBikesFixByPark(Long parkId) {
		//List<Bike> list = bikeMapper.getBikesFixByPark(parkId);
		List<Bike> list = bikeMapper.getBikesByPark(parkId);
		if (null == list || list.isEmpty()) {
			return new ArrayList<BikeModel>();
		}
		List<BikeModel> models = new ArrayList<>();
		for (Bike bike : list) {
			models.add(ModelDataObjectUtil.model2do(bike, BikeModel.class));
		}
		return models;
	}
}

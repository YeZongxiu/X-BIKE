package com.ucmed.common.service.parking;

import java.util.ArrayList;
import java.util.List;

import com.ucmed.common.dao.fix.FixOrderMapper;
import com.ucmed.common.dao.parking.BluetoothMapper;
import com.ucmed.common.dao.parking.ParkingSpaceMapper;
import com.ucmed.common.dataobj.parking.Bluetooth;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.ModelDataObjectUtil;

/**
 * Created by ucmed on 2017/3/16.
 */
public class ParkingSpaceServiceImpl implements ParkingSpaceService {
	private ParkingSpaceMapper parkingSpaceMapper;
	private FixOrderMapper fixOrderMapper;
	private BluetoothMapper bluetoothMapper;

	public void setBluetoothMapper(BluetoothMapper bluetoothMapper) {
		this.bluetoothMapper = bluetoothMapper;
	}

	public void setFixOrderMapper(FixOrderMapper fixOrderMapper) {
		this.fixOrderMapper = fixOrderMapper;
	}

	public void setParkingSpaceMapper(ParkingSpaceMapper parkingSpaceMapper) {
		this.parkingSpaceMapper = parkingSpaceMapper;
	}

	@Override
	public void addParkingInfo(ParkingSpaceModel model) {
		ParkingSpace parkingSpace = ModelDataObjectUtil.model2do(model, ParkingSpace.class);
		parkingSpaceMapper.insertSelective(parkingSpace);
        model.setId(parkingSpace.getId());
		String[] macList = model.getMacList();
		if (macList != null){
			for (String mac: macList){
				Bluetooth bluetooth = new Bluetooth();
				bluetooth.setSpaceId(parkingSpace.getId());
				bluetooth.setMac(mac);
				bluetoothMapper.insert(bluetooth);
			}
		}
	}

	@Override
	public List<ParkingSpaceModel> getParkingDetail(Double longitude,
			Double latitude) {
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
		List<ParkingSpace> list = parkingSpaceMapper.selectAcount(minlng, maxlng, minlat, maxlat);
		if (null == list || list.isEmpty()) {
			return new ArrayList<ParkingSpaceModel>();
		}
		List<ParkingSpaceModel> models = new ArrayList<>();
		for (ParkingSpace parkingSpace : list) {
			models.add(ModelDataObjectUtil.model2do(parkingSpace, ParkingSpaceModel.class));
		}
		return models;
	}

	@Override
	public List<ParkingSpaceModel> getParkBike(Double longitude, Double latitude) {
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
		List<ParkingSpace> list = parkingSpaceMapper.getParkBike(minlng, maxlng, minlat, maxlat);
		if (null == list || list.isEmpty()) {
			return new ArrayList<ParkingSpaceModel>();
		}
		List<ParkingSpaceModel> models = new ArrayList<>();
		for (ParkingSpace parkingSpace : list) {
			ParkingSpaceModel model = ModelDataObjectUtil.model2do(parkingSpace, ParkingSpaceModel.class);
			Long fixCount = fixOrderMapper.getPrakFixCount(model.getId());
			model.setFixNumber(fixCount);
			if (model.getBikeNumber() == 0) {
				model.setPercentum((double)0);
			} else if (model.getParkNumber() < model.getBikeNumber()) {
				model.setPercentum((double)1);
			} else {
				model.setPercentum((double)model.getBikeNumber() / model.getParkNumber() * 100);
			}
			if (fixCount == 0) {
				model.setFixPercentum((double)0);
			} else if (fixCount < model.getBikeNumber()) {
				model.setFixPercentum((double)1);
			} else {
				model.setFixPercentum((double)model.getBikeNumber() / model.getParkNumber() * 100);
			}
			models.add(model);
		}
		return models;
	}

	@Override
	public void updatePark(ParkingSpaceModel model) {
		parkingSpaceMapper.updateByPrimaryKey(ModelDataObjectUtil.model2do(model, ParkingSpace.class));
	}

	@Override
	public ParkingSpaceModel getParkById(Long parkId) {
		ParkingSpace parkingSpace = parkingSpaceMapper.selectByPrimaryKey(parkId);
		return ModelDataObjectUtil.model2do(parkingSpace, ParkingSpaceModel.class);
	}

	@Override
	public ParkingSpaceModel getSameParking(String longitude, String latitude) {
		ParkingSpace parkingSpace = parkingSpaceMapper.getSameParking(longitude, latitude);
		return ModelDataObjectUtil.model2do(parkingSpace, ParkingSpaceModel.class);
	}

	@Override
	public void deletePark(Long parkId) {
		parkingSpaceMapper.deleteByPrimaryKey(parkId);
	}

	@Override
	public List<ParkingSpaceModel> selectAllPrak() {
		List<ParkingSpace> list = parkingSpaceMapper.selectAllPrak();
		if (null == list || list.isEmpty()) {
			return new ArrayList<ParkingSpaceModel>();
		}
		List<ParkingSpaceModel> models = new ArrayList<>();
		for (ParkingSpace parkingSpace : list) {
			ParkingSpaceModel model = ModelDataObjectUtil.model2do(parkingSpace, ParkingSpaceModel.class);
			Long fixCount = fixOrderMapper.getPrakFixCount(model.getId());
			model.setFixNumber(fixCount);
			if (model.getBikeNumber() == 0) {
				model.setPercentum((double)0);
			} else if (model.getParkNumber() < model.getBikeNumber()) {
				model.setPercentum((double)1);
			} else {
				model.setPercentum((double)model.getBikeNumber() / model.getParkNumber() * 100);
			}
			if (fixCount == 0) {
				model.setFixPercentum((double)0);
			} else if (fixCount < model.getBikeNumber()) {
				model.setFixPercentum((double)1);
			} else {
				model.setFixPercentum((double)model.getBikeNumber() / model.getParkNumber() * 100);
			}
			models.add(model);
		}
		return models;
	}

}

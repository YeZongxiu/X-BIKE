package com.ucmed.common.service.parking;

import com.ucmed.common.dao.fix.FixOrderMapper;
import com.ucmed.common.dao.parking.BluetoothMapper;
import com.ucmed.common.dao.parking.ForbidSpaceMapper;
import com.ucmed.common.dao.parking.ParkingSpaceMapper;
import com.ucmed.common.dataobj.parking.Bluetooth;
import com.ucmed.common.dataobj.parking.ForbidSpace;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import com.ucmed.common.model.parking.ForbidSpaceModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.GetDistanceUtil;
import com.ucmed.common.util.ModelDataObjectUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public class ForbidSpaceServiceImpl implements ForbidSpaceService {
    private ForbidSpaceMapper forbidSpaceMapper;

    public void setForbidSpaceMapper(ForbidSpaceMapper forbidSpaceMapper) {
        this.forbidSpaceMapper = forbidSpaceMapper;
    }

    @Override
    public void editForbidSpaceInfo(ForbidSpaceModel model) {
        ForbidSpace forbidSpace = ModelDataObjectUtil.model2do(model, ForbidSpace.class);
        forbidSpace.setUpdateTime(new Date());
        if (forbidSpace.getId() != null){
            forbidSpaceMapper.updateByPrimaryKeySelective(forbidSpace);
        } else {
            forbidSpace.setCreateTime(new Date());
            forbidSpaceMapper.insertSelective(forbidSpace);
        }
    }


    @Override
    public List<ForbidSpaceModel> getForbidSpaceList() {
        Date now = new Date();
        JSONObject center = GetDistanceUtil.getByIp();
        Double longitude;
        Double latitude;
        if (center == null) {
            longitude = Double.parseDouble(Constants.HZ_LONGITUDE);
            latitude = Double.parseDouble(Constants.HZ_LATITUDE);
        } else {
            longitude = Double.parseDouble(center.optString("x"));
            latitude = Double.parseDouble(center.optString("y"));
        }
        Double dlng = 2*Math.asin(Math.sin(Constants.DIS3/(2*Constants.R))/Math.cos(latitude*Math.PI/180));
        dlng = dlng * 180 / Math.PI;
        Double dlat = Constants.DIS3 / Constants.R;
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
        List<ForbidSpace> list = forbidSpaceMapper.selectForbidSpace(minlng, maxlng, minlat, maxlat, now);
        if (null == list || list.isEmpty()) {
            return new ArrayList<ForbidSpaceModel>();
        }
        List<ForbidSpaceModel> models = new ArrayList<>();
        for (ForbidSpace forbidSpace : list) {
            models.add(ModelDataObjectUtil.model2do(forbidSpace, ForbidSpaceModel.class));
        }
        return models;
    }

    @Override
    public ForbidSpaceModel getForbidById(Long id) {
        ForbidSpace forbidSpace = forbidSpaceMapper.selectByPrimaryKey(id);
        if (forbidSpace != null)
            return ModelDataObjectUtil.model2do(forbidSpace, ForbidSpaceModel.class);
        return null;
    }

    @Override
    public List<ForbidSpaceModel> getForbid(Double longitude, Double latitude) {
        Date now = new Date();
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
        List<ForbidSpace> list = forbidSpaceMapper.getForbid(minlng, maxlng, minlat, maxlat, now);
        if (null == list || list.isEmpty()) {
            return new ArrayList<ForbidSpaceModel>();
        }
        List<ForbidSpaceModel> models = new ArrayList<>();
        for (ForbidSpace forbidSpace : list) {
            ForbidSpaceModel model = ModelDataObjectUtil.model2do(forbidSpace, ForbidSpaceModel.class);
            models.add(model);
        }
        return models;
    }
}

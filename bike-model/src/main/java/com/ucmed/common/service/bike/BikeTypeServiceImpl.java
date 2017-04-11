package com.ucmed.common.service.bike;

import com.ucmed.common.dao.bike.BikeTypeMapper;
import com.ucmed.common.dataobj.bike.BikeType;
import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.util.ModelDataObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public class BikeTypeServiceImpl implements BikeTypeService {
    private BikeTypeMapper bikeTypeMapper;

    public void setBikeTypeMapper(BikeTypeMapper bikeTypeMapper) {
        this.bikeTypeMapper = bikeTypeMapper;
    }

    @Override
    public List<BikeTypeModel> getTypeList() {
        List<BikeType> list = bikeTypeMapper.selectAll();
        List<BikeTypeModel> types = new ArrayList<BikeTypeModel>();
        if (null == list || list.isEmpty()){
            return new ArrayList<BikeTypeModel>();
        }
        for (BikeType type: list) {
            types.add(ModelDataObjectUtil.do2model(type, BikeTypeModel.class));
        }
        return  types;
    }
}

package com.ucmed.common.service.fix;

import com.ucmed.common.dao.fix.FixOrderMapper;
import com.ucmed.common.dataobj.fix.FixOrder;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.GetDistanceUtil;
import com.ucmed.common.util.ModelDataObjectUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public class FixOrderServiceImpl implements FixOrderService{
    private FixOrderMapper fixOrderMapper;

    public void setFixOrderMapper(FixOrderMapper fixOrderMapper) {
        this.fixOrderMapper = fixOrderMapper;
    }

    @Override
    public void addFixOrder(FixOrderModel model) {
        FixOrder fixOrder = ModelDataObjectUtil.model2do(model, FixOrder.class);
        fixOrderMapper.insertSelective(fixOrder);
        model.setId(fixOrder.getId());
    }

    @Override
    public FixOrderModel selectById(Long id) {
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        return ModelDataObjectUtil.model2do(fixOrder, FixOrderModel.class);
    }

    @Override
    public void updateFixOrder(Long id, String status) {
        fixOrderMapper.updateStatusById(id, status);
    }

    @Override
    public JSONObject getBikeFixList(Double longitude, Double latitude) {
        if (fixOrderMapper.getBikeFixCount() == 0){
            return null;
        }
        Double distance = (double)1;
        List<FixOrder> list = new ArrayList<>();
        while (list.isEmpty() || null == list){
            Double dlng = 2*Math.asin(Math.sin(distance/(2* Constants.R))/Math.cos(latitude*Math.PI/180));
            dlng = dlng * 180 / Math.PI;
            Double dlat = distance / Constants.R;
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
            list = fixOrderMapper.getBikeFixList(minlng, maxlng, minlat, maxlat);
            distance += 1;
        }
        if (list.size() == 1){
            fixOrderMapper.updateById(list.get(0).getId());
            return JSONObject.fromObject(list.get(0));
        }
        JSONArray array = GetDistanceUtil.getPoint(JSONArray.fromObject(list), longitude, latitude, new JSONArray());
        JSONObject order = JSONObject.fromObject(array.get(0));
        fixOrderMapper.updateById(order.optLong("id"));
        return order;
    }
}
package com.ucmed.common.service.fix;

import com.ucmed.common.dao.fix.FixOrderMapper;
import com.ucmed.common.dataobj.bike.Bike;
import com.ucmed.common.dataobj.fix.FixOrder;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import com.ucmed.common.util.*;
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
    public JSONArray getFixOrder(Double longitude, Double latitude) {
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
        List<FixOrder> list = fixOrderMapper.selectOutParkFix(minlng, maxlng, minlat, maxlat);
        if (null == list || list.isEmpty()) {
            return new JSONArray();
        }
        JSONArray array = new JSONArray();
        for (FixOrder fixOrder : list) {
            array.add(fixOrder.getJsonObject());
        }
        return array;
    }

    @Override
    public PaginationResult<FixOrderModel> getFixOrderList(Long pageNo, Long pageSize) {
        PaginationResult<FixOrderModel> result = new PaginationResult<>();
        Long count = fixOrderMapper.getFixCount();
        result.setTotalCount(count);
        Long pageCount = PageUtil.getPageCount(count, pageSize);
        if (pageNo > pageCount){
            pageNo = pageCount;
        }
        result.setPageCount(pageCount);
        Long start = PageUtil.getStartRecord(pageNo, pageSize);
        List<FixOrder> list = fixOrderMapper.selectFixList(PageUtil.getStartRecord(pageNo, pageSize),
                PageUtil.getEndRecord(pageNo, pageSize));
        if(list == null) {
            list = new ArrayList<FixOrder>();
        }
        for(FixOrder fixOrder : list) {
            result.getList().add(
                    ModelDataObjectUtil.do2model(fixOrder,
                            FixOrderModel.class));
        }
        return result;
    }

    @Override
    public JSONArray getFixOrderByPark(Long parkId) {
        List<FixOrder> list = fixOrderMapper.getFixOrderByPark(parkId);
        if (null == list || list.isEmpty()) {
            return new JSONArray();
        }
        JSONArray array = new JSONArray();
        for (FixOrder fixOrder : list) {
            array.add(fixOrder.getJsonObject());
        }
        return array;
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

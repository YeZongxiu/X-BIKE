package com.ucmed.common.service.fix;

import com.ucmed.common.dataobj.fix.FixOrder;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.model.parking.ParkingSpaceModel;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface FixOrderService {
    public void addFixOrder(FixOrderModel model);

    public JSONObject getBikeFixList(Double longitude, Double latitude);

    public void updateFixOrder(Long id, String status);

    public FixOrderModel selectById(Long id);
}

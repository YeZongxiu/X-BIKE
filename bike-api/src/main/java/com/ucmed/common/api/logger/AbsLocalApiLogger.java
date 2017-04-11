package com.ucmed.common.api.logger;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.ucmed.common.model.api.ApiFlowJnlModel;
import com.ucmed.common.service.api.ApiFlowJnlService;
import com.ucmed.common.util.DateUtil;
import net.sf.json.JSONObject;

import com.ucmed.common.util.Constants;

public abstract class AbsLocalApiLogger extends AbsApiLogger {

    protected abstract void addDateToMap(Map<String, Object> map,
        String userId, JSONObject req);

    @Override
    public void log(String userId, JSONObject req) {
        Map<String, Object> map = new HashMap<String, Object>();
        addDateToMap(map, userId, req);
        ApiFlowJnlModel model = new ApiFlowJnlModel();
        model.setClientUser(userId);
        addDateToModel(model, map, req);
        ((ApiFlowJnlService) service).addApiFlowJnl(model);
    }

    private void addDateToModel(ApiFlowJnlModel model,
        Map<String, Object> map, JSONObject req) {
        JSONObject params = req.optJSONObject(Constants.PARAMS);
        Calendar calendar = Calendar.getInstance();
        model.setClientDate(DateUtil.getyyyyMMdd(calendar.getTime()));
        model.setClientDatetime(calendar.getTime());
        model.setClientTransname(req
            .optString(Constants.API_NAME));
        model.setData(req.toString());
        model.setDay(((Integer) calendar.get(Calendar.DAY_OF_MONTH))
            .toString());
        model.setHour(((Integer) calendar.get(Calendar.HOUR_OF_DAY))
            .toString());
        model.setIp(params.optString(Constants.IP));
        model.setMonth(((Integer) (calendar.get(Calendar.MONTH) + 1))
            .toString());
        model.setYear(((Integer) calendar.get(Calendar.YEAR))
            .toString());

    }
}

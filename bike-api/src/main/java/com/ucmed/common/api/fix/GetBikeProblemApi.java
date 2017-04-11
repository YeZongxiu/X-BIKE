package com.ucmed.common.api.fix;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.model.fix.ProblemModel;
import com.ucmed.common.service.bike.BikeTypeService;
import com.ucmed.common.service.fix.ProblemService;
import com.ucmed.common.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by ucmed on 2017/3/18.
 */
public class GetBikeProblemApi implements Api {
    private ProblemService problemService;

    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        List<ProblemModel> list = problemService.getProblemList();
        if (null == list || list.isEmpty()){
            result.put("list", new JSONArray());
        }else{
            JSONArray array = new JSONArray();
            for (ProblemModel model : list){
                array.add(model.toJsonObject());
            }
            result.put("list", array);
        }
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取可选问题列表成功");
        return  result;
    }
}

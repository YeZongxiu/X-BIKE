package com.ucmed.common.api.fix;

import com.ucmed.common.service.bike.BikeTypeService;
import com.ucmed.common.service.fix.ProblemService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucmed.common.api.web.Api;
import com.ucmed.common.model.bike.BikeModel;
import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.model.fix.ProblemModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.bike.BikeService;
import com.ucmed.common.service.fix.FixOrderService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.Constants;
import com.ucmed.common.util.StringUtil;

import java.util.List;

/**
 * Created by ucmed on 2017/3/18.
 */
public class BikeAddFixApi implements Api {
    private static final Logger LOG = LoggerFactory.getLogger(BikeAddFixApi.class);
    private UserService userService;
    private FixOrderService fixOrderService;
    private BikeService bikeService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setFixOrderService(FixOrderService fixOrderService) {
        this.fixOrderService = fixOrderService;
    }
    
    public void setBikeService(BikeService bikeService) {
		this.bikeService = bikeService;
	}

	@Override
    public JSONObject execute(JSONObject params) {
        JSONObject result = new JSONObject();
        if (params == null || params.isEmpty()){
            return errorResult(result, "失败，参数错误。", "错误：参数为空。");
        }
        Long userId = params.optLong("user_id");
        UserModel user = userService.getUserById(userId);
        if (null == user){
            return errorResult(result, "用户不存在。", "错误：用户不存在，用户" + userId + "为空");
        }
        try {
            FixOrderModel fixModel = new FixOrderModel();
            JSONObject file_uploaded = (JSONObject) params.get("file_uploaded");
            if (null != file_uploaded){
                JSONArray array = file_uploaded.optJSONArray("file_paths");
                String realFileName = array.optString(0);
                fixModel.setPhoto(realFileName);
            }
            Long typeId = params.optLong("type_id");
            String longitude = params.optString("longitude");
            String latitude = params.optString("latitude");
            String twoBarCodes = params.optString("two_bar_codes");
            String bikeNo = params.optString("bike_no");
            String problemList = params.optString("problem_list");
            String remark = params.optString("remark");
            if (null == typeId || (null == twoBarCodes && null == bikeNo)){
                return errorResult(result, "车辆信息未填写。", "车辆信息未填写");
            }
            BikeTypeModel model = new BikeTypeModel();
            model.setId(typeId);
            if (!equals(model)){
                return errorResult(result, "车辆类型选择错误。", "选择了未合作的车辆类型");
            }
            if (StringUtil.isNotBlank(problemList)){
               String[] problem = problemList.split(",");
               for (String problemId : problem) {
                   Long problem_id = Long.parseLong(problemId);
                   if (!equals(problem_id)){
                       return errorResult(result, "问题选择错误。", "选择了未存在的问题");
                   }
               }
            }
            if (null == longitude || null == latitude){
                return errorResult(result, "位置信息错误。", "经纬度为空");
            }
            fixModel.setLatitude(latitude);
            fixModel.setLongitude(longitude);
            fixModel.setBikeTypeId(typeId);
            BikeModel bikeModel = new BikeModel();
            if(StringUtil.isNotBlank(bikeNo)){
            	fixModel.setBikeNo(bikeNo);
            	bikeModel = bikeService.getBikeByBikeNo(bikeNo, typeId);
            }
            else {
            	fixModel.setTwoBarCodes(twoBarCodes);
            	bikeModel = bikeService.getBikeByTwoBarCodes(twoBarCodes, typeId);
            }
            if (null == bikeModel){
                return errorResult(result, "不存在此辆车。", "错误：车辆不存在");
            }
            bikeModel.setStatus("2");
            bikeService.updateBike(bikeModel);
            fixModel.setProblem(problemList);
            fixModel.setRemark(remark);
            fixOrderService.addFixOrder(fixModel);
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_FAIL);
            result.put("ret_info", Constants.API_EXCEPTION_MSG);
            return result;
        }
        result.put("ret_code", 0);
        result.put("ret_info", "报修成功");
        return result;
    }

    private JSONObject errorResult(JSONObject res, String retInfo, String logString) {
        JSONObject result = res;
        if (result == null) {
            result = new JSONObject();
        }
        result.put("ret_code", 1);
        result.put("ret_info", retInfo);
        LOG.warn(logString);
        return result;
    }

    private BikeTypeService bikeTypeService;
    public void setBikeTypeService(BikeTypeService bikeTypeService) {
        this.bikeTypeService = bikeTypeService;
    }

    public boolean equals(BikeTypeModel bikeTypeModel) {
        List<BikeTypeModel> list = bikeTypeService.getTypeList();
        for (BikeTypeModel model: list) {
            if (bikeTypeModel.getId().equals(model.getId())){
                return  true;
            }
        }
        return false;
    }

    private ProblemService problemService;

    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    public boolean equals(Long problemId) {
        List<ProblemModel> list = problemService.getProblemList();
        for (ProblemModel model: list) {
            if (problemId.equals(model.getId())){
                return  true;
            }
        }
        return false;
    }
}


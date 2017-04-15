package com.ucmed.common.api.fix;

import com.ucmed.common.api.admin.AdminAddBikeApi;
import com.ucmed.common.api.web.Api;
import com.ucmed.common.dataobj.fix.FixOrder;
import com.ucmed.common.model.fix.FixOrderModel;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.service.fix.FixOrderService;
import com.ucmed.common.service.user.UserService;
import com.ucmed.common.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ucmed on 2017/4/15.
 */
public class GetFixOrderListApi implements Api{
    private static final Logger LOG = LoggerFactory.getLogger(GetFixOrderListApi.class);
    private UserService userService;
    private FixOrderService fixOrderService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setFixOrderService(FixOrderService fixOrderService) {
        this.fixOrderService = fixOrderService;
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
        if (!StringUtil.isNotBlank(user.getIsAdmin()) && !"1".equals(user.getIsAdmin())){
            return errorResult(result, "您不是管理员，不能添加车辆信息。", "错误：不是管理员，无法增加车辆");
        }
        Long pageNo = DataUtil.getLongValue(params, "page_no",
                PageUtil.DEFAULT_PAGE_NO);
        Long pageSize = DataUtil.getLongValue(params, "page_size",
                PageUtil.DEFAULT_PAGE_SIZE);
        PaginationResult<FixOrderModel> fixOrders = fixOrderService.getFixOrderList(pageNo, pageSize);
        result.put("total_count", fixOrders.getTotalCount());
        result.put("page_count", fixOrders.getPageCount());
        result.put("list", JSONArray.fromObject(fixOrders.getList()));
        result.put("ret_code", Constants.API_RESPONSE_RESULT_RET_CODE_SUCCESS);
        result.put("ret_info", "获取报修数据成功");
        return  result;
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
}

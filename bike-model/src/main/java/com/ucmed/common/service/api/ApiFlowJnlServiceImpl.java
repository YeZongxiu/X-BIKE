package com.ucmed.common.service.api;

import com.ucmed.common.dao.api.ApiFlowJnlMapper;
import com.ucmed.common.dataobj.api.ApiFlowJnl;
import com.ucmed.common.model.api.ApiFlowJnlModel;
import com.ucmed.common.util.ModelDataObjectUtil;
import net.sf.json.JSONObject;

/**
 * Created by ucmed on 2017/3/16.
 */
public class ApiFlowJnlServiceImpl implements ApiFlowJnlService {
    private ApiFlowJnlMapper apiFlowJnlMapper;

    public void setApiFlowJnlMapper(ApiFlowJnlMapper apiFlowJnlMapper) {
        this.apiFlowJnlMapper = apiFlowJnlMapper;
    }

    @Override
    public void addApiFlowJnl(ApiFlowJnlModel model) {
        ApiFlowJnl record = ModelDataObjectUtil.model2do(model,
                ApiFlowJnl.class);
        apiFlowJnlMapper.insertSelective(record);
        model.setId(record.getId());
    }
}

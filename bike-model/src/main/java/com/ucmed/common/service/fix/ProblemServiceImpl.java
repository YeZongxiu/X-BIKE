package com.ucmed.common.service.fix;

import com.ucmed.common.dao.bike.BikeTypeMapper;
import com.ucmed.common.dao.fix.ProblemMapper;
import com.ucmed.common.dataobj.bike.BikeType;
import com.ucmed.common.dataobj.fix.Problem;
import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.model.fix.ProblemModel;
import com.ucmed.common.util.ModelDataObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public class ProblemServiceImpl implements ProblemService {
    private ProblemMapper problemMapper;

    public void setProblemMapper(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    @Override
    public List<ProblemModel> getProblemList() {
        List<Problem> list = problemMapper.selectAll();
        List<ProblemModel> problems = new ArrayList<ProblemModel>();
        if (null == list || list.isEmpty()){
            return new ArrayList<ProblemModel>();
        }
        for (Problem type: list) {
            problems.add(ModelDataObjectUtil.do2model(type, ProblemModel.class));
        }
        return  problems;
    }
}

package com.ucmed.common.service.fix;

import com.ucmed.common.model.bike.BikeTypeModel;
import com.ucmed.common.model.fix.ProblemModel;

import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface ProblemService {
    List<ProblemModel> getProblemList();
}

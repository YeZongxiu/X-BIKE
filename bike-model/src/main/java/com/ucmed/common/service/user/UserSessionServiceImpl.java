package com.ucmed.common.service.user;

import com.ucmed.common.dao.user.UserSessionMapper;
import com.ucmed.common.model.user.UserSessionModel;
import com.ucmed.common.service.record.RecordService;
import com.ucmed.common.util.ModelDataObjectUtil;

import java.util.Date;

/**
 * Created by ucmed on 2017/3/16.
 */
public class UserSessionServiceImpl implements UserSessionService {
    private UserSessionMapper userSessionMapper;

    public void setUserSessionMapper(UserSessionMapper userSessionMapper) {
        this.userSessionMapper = userSessionMapper;
    }

    @Override
    public UserSessionModel getBySession(String sessionId) {
        return ModelDataObjectUtil.do2model(
                userSessionMapper.selectBySession(sessionId),
                UserSessionModel.class);
    }

    @Override
    public void loginOut(String sessionId) {
        userSessionMapper.clearBySession(new Date(), sessionId);
    }
}

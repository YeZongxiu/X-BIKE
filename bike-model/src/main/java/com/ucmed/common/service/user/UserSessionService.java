package com.ucmed.common.service.user;

import com.ucmed.common.model.user.UserSessionModel;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface UserSessionService {
    public UserSessionModel getBySession(String sessionId);

    public void loginOut(String sessionId);
}

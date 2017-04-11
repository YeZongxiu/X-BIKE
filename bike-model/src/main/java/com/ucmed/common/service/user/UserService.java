package com.ucmed.common.service.user;

import com.ucmed.common.dao.user.UserMapper;
import com.ucmed.common.dataobj.user.User;
import com.ucmed.common.model.user.UserModel;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface UserService {
    public void addUser(UserModel model);

    public UserModel getUser(String phone);

    public Boolean update(UserModel model);

    public UserModel getUserById(Long userId);
}

package com.ucmed.common.service.user;

import com.ucmed.common.dao.user.UserMapper;
import com.ucmed.common.dataobj.user.User;
import com.ucmed.common.model.user.UserModel;
import com.ucmed.common.util.ModelDataObjectUtil;

/**
 * Created by ucmed on 2017/3/16.
 */
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void addUser(UserModel model) {
        User user = ModelDataObjectUtil.model2do(model, User.class);
        userMapper.insertSelective(user);
        model.setId(user.getId());
    }

    @Override
    public UserModel getUser(String phone) {
        User user = userMapper.selectUserByPhone(phone);
        return ModelDataObjectUtil.do2model(user, UserModel.class);
    }

    @Override
    public Boolean update(UserModel model) {
        int rowAffected = userMapper.updateByPrimaryKeySelective(ModelDataObjectUtil.model2do(
                model, User.class));
        return rowAffected > 0;
    }

    @Override
    public UserModel getUserById(Long userId) {
        if (null == userId){
            return  null;
        }
        User user = userMapper.selectByPrimaryKey(userId);
        return  ModelDataObjectUtil.do2model(user, UserModel.class);
    }
}

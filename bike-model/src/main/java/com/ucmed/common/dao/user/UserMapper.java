package com.ucmed.common.dao.user;

import com.ucmed.common.dataobj.user.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user (id, phone, ",
        "password, security_key, ",
        "create_time, last_login_time, ",
        "login_time, photo, ",
        "update_time, is_delete, ",
        "deposit, wallet, ",
        "is_admin)",
        "values (#{id,jdbcType=DECIMAL}, #{phone,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{securityKey,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastLoginTime,jdbcType=TIMESTAMP}, ",
        "#{loginTime,jdbcType=INTEGER}, #{photo,jdbcType=VARCHAR}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{isDelete,jdbcType=CHAR}, ",
        "#{deposit,jdbcType=INTEGER}, #{wallet,jdbcType=INTEGER}, ",
        "#{isAdmin,jdbcType=CHAR})"
    })
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @Select({
        "select",
        "id, phone, password, security_key, create_time, last_login_time, login_time, ",
        "photo, update_time, is_delete, deposit, wallet, is_admin",
        "from user",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="security_key", property="securityKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_login_time", property="lastLoginTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="login_time", property="loginTime", jdbcType=JdbcType.INTEGER),
        @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.CHAR),
        @Result(column="deposit", property="deposit", jdbcType=JdbcType.INTEGER),
        @Result(column="wallet", property="wallet", jdbcType=JdbcType.INTEGER),
        @Result(column="is_admin", property="isAdmin", jdbcType=JdbcType.CHAR)
    })
    User selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update user",
        "set phone = #{phone,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "security_key = #{securityKey,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "last_login_time = #{lastLoginTime,jdbcType=TIMESTAMP},",
          "login_time = #{loginTime,jdbcType=INTEGER},",
          "photo = #{photo,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "is_delete = #{isDelete,jdbcType=CHAR},",
          "deposit = #{deposit,jdbcType=INTEGER},",
          "wallet = #{wallet,jdbcType=INTEGER},",
          "is_admin = #{isAdmin,jdbcType=CHAR}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(User record);

    @Select({
            "select",
            "id, phone, password, security_key, create_time, last_login_time, login_time, ",
            "photo, update_time, is_delete, deposit, wallet, is_admin",
            "from user",
            "where phone = #{phone,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="security_key", property="securityKey", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="last_login_time", property="lastLoginTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="login_time", property="loginTime", jdbcType=JdbcType.INTEGER),
            @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.CHAR),
            @Result(column="deposit", property="deposit", jdbcType=JdbcType.INTEGER),
            @Result(column="wallet", property="wallet", jdbcType=JdbcType.INTEGER),
            @Result(column="is_admin", property="isAdmin", jdbcType=JdbcType.CHAR)
    })
    User selectUserByPhone(String phone);
}
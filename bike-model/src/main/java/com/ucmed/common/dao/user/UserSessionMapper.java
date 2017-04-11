package com.ucmed.common.dao.user;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import com.ucmed.common.dataobj.user.UserSession;

import java.util.Date;

public interface UserSessionMapper {
    @Delete({
        "delete from user_session",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_session (id, user_id, ",
        "device, session_id, ",
        "create_time, update_time, ",
        "is_delete)",
        "values (#{id,jdbcType=DECIMAL}, #{userId,jdbcType=DECIMAL}, ",
        "#{device,jdbcType=VARCHAR}, #{sessionId,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{isDelete,jdbcType=CHAR})"
    })
    int insert(UserSession record);

    @InsertProvider(type=UserSessionSqlProvider.class, method="insertSelective")
    int insertSelective(UserSession record);

    @Select({
        "select",
        "id, user_id, device, session_id, create_time, update_time, is_delete",
        "from user_session",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.DECIMAL),
        @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
        @Result(column="session_id", property="sessionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.CHAR)
    })
    UserSession selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserSessionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserSession record);

    @Update({
        "update user_session",
        "set user_id = #{userId,jdbcType=DECIMAL},",
          "device = #{device,jdbcType=VARCHAR},",
          "session_id = #{sessionId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "is_delete = #{isDelete,jdbcType=CHAR}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(UserSession record);
    
    @Select({
        "select",
        "id, user_id, device, session_id, create_time, update_time, is_delete",
        "from user_session",
        "where user_id = #{userId,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.DECIMAL),
        @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
        @Result(column="session_id", property="sessionId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.CHAR)
    })
    UserSession getByUserId(Long userId);

    @Select({
            "select",
            "id, user_id, device, session_id, create_time, update_time, is_delete",
            "from user_session",
            "where session_id = #{sessionId,jdbcType=DECIMAL}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.DECIMAL),
            @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
            @Result(column="session_id", property="sessionId", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="is_delete", property="isDelete", jdbcType=JdbcType.CHAR)
    })
    UserSession selectBySession(String sessionId);
    @Update({
            "update user_session",
            "set session_id = null,",
            "update_time = #{updateTime,jdbcType=TIMESTAMP}",
            "where session_id = #{sessionId,jdbcType=VARCHAR}"
    })
    int clearBySession(@Param("updateTime") Date updateTime,
                       @Param("sessionId") String sessionId);
}
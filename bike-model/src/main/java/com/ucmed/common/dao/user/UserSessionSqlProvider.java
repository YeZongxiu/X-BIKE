package com.ucmed.common.dao.user;

import com.ucmed.common.dataobj.user.UserSession;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class UserSessionSqlProvider {

    public String insertSelective(UserSession record) {
        BEGIN();
        INSERT_INTO("user_session");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getUserId() != null) {
            VALUES("user_id", "#{userId,jdbcType=DECIMAL}");
        }
        
        if (record.getDevice() != null) {
            VALUES("device", "#{device,jdbcType=VARCHAR}");
        }
        
        if (record.getSessionId() != null) {
            VALUES("session_id", "#{sessionId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=CHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(UserSession record) {
        BEGIN();
        UPDATE("user_session");
        
        if (record.getUserId() != null) {
            SET("user_id = #{userId,jdbcType=DECIMAL}");
        }
        
        if (record.getDevice() != null) {
            SET("device = #{device,jdbcType=VARCHAR}");
        }
        
        if (record.getSessionId() != null) {
            SET("session_id = #{sessionId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=CHAR}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
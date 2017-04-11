package com.ucmed.common.dao.user;

import com.ucmed.common.dataobj.user.User;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class UserSqlProvider {

    public String insertSelective(User record) {
        BEGIN();
        INSERT_INTO("user");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getPhone() != null) {
            VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getSecurityKey() != null) {
            VALUES("security_key", "#{securityKey,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastLoginTime() != null) {
            VALUES("last_login_time", "#{lastLoginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginTime() != null) {
            VALUES("login_time", "#{loginTime,jdbcType=INTEGER}");
        }
        
        if (record.getPhoto() != null) {
            VALUES("photo", "#{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            VALUES("is_delete", "#{isDelete,jdbcType=CHAR}");
        }
        
        if (record.getDeposit() != null) {
            VALUES("deposit", "#{deposit,jdbcType=INTEGER}");
        }
        
        if (record.getWallet() != null) {
            VALUES("wallet", "#{wallet,jdbcType=INTEGER}");
        }
        
        if (record.getIsAdmin() != null) {
            VALUES("is_admin", "#{isAdmin,jdbcType=CHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(User record) {
        BEGIN();
        UPDATE("user");
        
        if (record.getPhone() != null) {
            SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getSecurityKey() != null) {
            SET("security_key = #{securityKey,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastLoginTime() != null) {
            SET("last_login_time = #{lastLoginTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginTime() != null) {
            SET("login_time = #{loginTime,jdbcType=INTEGER}");
        }
        
        if (record.getPhoto() != null) {
            SET("photo = #{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getIsDelete() != null) {
            SET("is_delete = #{isDelete,jdbcType=CHAR}");
        }
        
        if (record.getDeposit() != null) {
            SET("deposit = #{deposit,jdbcType=INTEGER}");
        }
        
        if (record.getWallet() != null) {
            SET("wallet = #{wallet,jdbcType=INTEGER}");
        }
        
        if (record.getIsAdmin() != null) {
            SET("is_admin = #{isAdmin,jdbcType=CHAR}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
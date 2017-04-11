package com.ucmed.common.dao.api;


import com.ucmed.common.dataobj.api.ApiFlowJnl;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class ApiFlowJnlSqlProvider {

    public String insertSelective(ApiFlowJnl record) {
        BEGIN();
        INSERT_INTO("api_flow_jnl");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getClientDatetime() != null) {
            VALUES("client_datetime", "#{clientDatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getClientDate() != null) {
            VALUES("client_date", "#{clientDate,jdbcType=VARCHAR}");
        }
        
        if (record.getHour() != null) {
            VALUES("hour", "#{hour,jdbcType=CHAR}");
        }
        
        if (record.getMonth() != null) {
            VALUES("month", "#{month,jdbcType=CHAR}");
        }
        
        if (record.getDay() != null) {
            VALUES("day", "#{day,jdbcType=CHAR}");
        }
        
        if (record.getYear() != null) {
            VALUES("year", "#{year,jdbcType=CHAR}");
        }
        
        if (record.getClientTransname() != null) {
            VALUES("client_transName", "#{clientTransname,jdbcType=VARCHAR}");
        }
        
        if (record.getClientUser() != null) {
            VALUES("client_user", "#{clientUser,jdbcType=VARCHAR}");
        }
        
        if (record.getData() != null) {
            VALUES("data", "#{data,jdbcType=VARCHAR}");
        }
        
        if (record.getReturnData() != null) {
            VALUES("return_data", "#{returnData,jdbcType=VARCHAR}");
        }
        
        if (record.getIp() != null) {
            VALUES("ip", "#{ip,jdbcType=VARCHAR}");
        }
        
        if (record.getTmp5() != null) {
            VALUES("tmp5", "#{tmp5,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ApiFlowJnl record) {
        BEGIN();
        UPDATE("api_flow_jnl");
        
        if (record.getClientDatetime() != null) {
            SET("client_datetime = #{clientDatetime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getClientDate() != null) {
            SET("client_date = #{clientDate,jdbcType=VARCHAR}");
        }
        
        if (record.getHour() != null) {
            SET("hour = #{hour,jdbcType=CHAR}");
        }
        
        if (record.getMonth() != null) {
            SET("month = #{month,jdbcType=CHAR}");
        }
        
        if (record.getDay() != null) {
            SET("day = #{day,jdbcType=CHAR}");
        }
        
        if (record.getYear() != null) {
            SET("year = #{year,jdbcType=CHAR}");
        }
        
        if (record.getClientTransname() != null) {
            SET("client_transName = #{clientTransname,jdbcType=VARCHAR}");
        }
        
        if (record.getClientUser() != null) {
            SET("client_user = #{clientUser,jdbcType=VARCHAR}");
        }
        
        if (record.getData() != null) {
            SET("data = #{data,jdbcType=VARCHAR}");
        }
        
        if (record.getReturnData() != null) {
            SET("return_data = #{returnData,jdbcType=VARCHAR}");
        }
        
        if (record.getIp() != null) {
            SET("ip = #{ip,jdbcType=VARCHAR}");
        }
        
        if (record.getTmp5() != null) {
            SET("tmp5 = #{tmp5,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
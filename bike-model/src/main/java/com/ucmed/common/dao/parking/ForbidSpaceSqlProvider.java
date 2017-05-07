package com.ucmed.common.dao.parking;

import com.ucmed.common.dataobj.parking.ForbidSpace;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class ForbidSpaceSqlProvider {

    public String insertSelective(ForbidSpace record) {
        BEGIN();
        INSERT_INTO("forbid_space");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getLongitude() != null) {
            VALUES("longitude", "#{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            VALUES("latitude", "#{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getDistance() != null) {
            VALUES("distance", "#{distance,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStartTime() != null) {
            VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ForbidSpace record) {
        BEGIN();
        UPDATE("forbid_space");
        
        if (record.getLongitude() != null) {
            SET("longitude = #{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            SET("latitude = #{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getDistance() != null) {
            SET("distance = #{distance,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStartTime() != null) {
            SET("start_time = #{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{endTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}
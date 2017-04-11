package com.ucmed.common.dao.bike;

import com.ucmed.common.dataobj.bike.Bike;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class BikeSqlProvider {

    public String insertSelective(Bike record) {
        BEGIN();
        INSERT_INTO("bike");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getBikeNo() != null) {
            VALUES("bike_no", "#{bikeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getLongitude() != null) {
            VALUES("longitude", "#{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            VALUES("latitude", "#{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            VALUES("status", "#{status,jdbcType=CHAR}");
        }
        
        if (record.getBikeTypeId() != null) {
            VALUES("bike_type_id", "#{bikeTypeId,jdbcType=INTEGER}");
        }

        if (record.getTwoBarCodes() != null){
            VALUES("two_bar_codes", "#{twoBarCodes,jdbcType=VARCHAR}");
        }

        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }

        if (record.getParkId() != null) {
            VALUES("park_id", "#{parkId,jdbcType=DECIMAL}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(Bike record) {
        BEGIN();
        UPDATE("bike");
        
        if (record.getBikeNo() != null) {
            SET("bike_no = #{bikeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getLongitude() != null) {
            SET("longitude = #{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            SET("latitude = #{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            SET("status = #{status,jdbcType=CHAR}");
        }
        
        if (record.getBikeTypeId() != null) {
            SET("bike_type_id = #{bikeTypeId,jdbcType=INTEGER}");
        }

        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }

        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
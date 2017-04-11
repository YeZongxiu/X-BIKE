package com.ucmed.common.dao.parking;

import com.ucmed.common.dataobj.parking.ParkingSpace;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class ParkingSpaceSqlProvider {

    public String insertSelective(ParkingSpace record) {
        BEGIN();
        INSERT_INTO("parking_space");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getLongitude() != null) {
            VALUES("longitude", "#{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            VALUES("latitude", "#{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getBikeNumber() != null) {
            VALUES("bike_number", "#{bikeNumber,jdbcType=DECIMAL}");
        }
        
        if (record.getParkNumber() != null) {
            VALUES("park_number", "#{parkNumber,jdbcType=DECIMAL}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(ParkingSpace record) {
        BEGIN();
        UPDATE("parking_space");
        
        if (record.getLongitude() != null) {
            SET("longitude = #{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            SET("latitude = #{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getBikeNumber() != null) {
            SET("bike_number = #{bikeNumber,jdbcType=DECIMAL}");
        }
        
        if (record.getParkNumber() != null) {
            SET("park_number = #{parkNumber,jdbcType=DECIMAL}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
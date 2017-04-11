package com.ucmed.common.dao.fix;

import com.ucmed.common.dataobj.fix.FixOrder;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class FixOrderSqlProvider {

    public String insertSelective(FixOrder record) {
        BEGIN();
        INSERT_INTO("fix_order");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getBikeTypeId() != null) {
            VALUES("bike_type_id", "#{bikeTypeId,jdbcType=DECIMAL}");
        }
        
        if (record.getBikeNo() != null) {
            VALUES("bike_no", "#{bikeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getLongitude() != null) {
            VALUES("longitude", "#{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            VALUES("latitude", "#{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getPhoto() != null) {
            VALUES("photo", "#{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getProblem() != null) {
            VALUES("problem", "#{problem,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(FixOrder record) {
        BEGIN();
        UPDATE("fix_order");
        
        if (record.getBikeTypeId() != null) {
            SET("bike_type_id = #{bikeTypeId,jdbcType=DECIMAL}");
        }
        
        if (record.getBikeNo() != null) {
            SET("bike_no = #{bikeNo,jdbcType=VARCHAR}");
        }
        
        if (record.getLongitude() != null) {
            SET("longitude = #{longitude,jdbcType=VARCHAR}");
        }
        
        if (record.getLatitude() != null) {
            SET("latitude = #{latitude,jdbcType=VARCHAR}");
        }
        
        if (record.getPhoto() != null) {
            SET("photo = #{photo,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            SET("remark = #{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getProblem() != null) {
            SET("problem = #{problem,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
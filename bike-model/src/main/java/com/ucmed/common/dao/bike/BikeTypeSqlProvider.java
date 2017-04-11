package com.ucmed.common.dao.bike;

import com.ucmed.common.dataobj.bike.BikeType;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class BikeTypeSqlProvider {

    public String insertSelective(BikeType record) {
        BEGIN();
        INSERT_INTO("bike_type");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getBikeTypeName() != null) {
            VALUES("bike_type_name", "#{bikeTypeName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(BikeType record) {
        BEGIN();
        UPDATE("bike_type");
        
        if (record.getBikeTypeName() != null) {
            SET("bike_type_name = #{bikeTypeName,jdbcType=VARCHAR}");
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
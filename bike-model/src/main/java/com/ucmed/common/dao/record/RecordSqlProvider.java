package com.ucmed.common.dao.record;

import com.ucmed.common.dataobj.record.Record;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class RecordSqlProvider {

    public String insertSelective(Record record) {
        BEGIN();
        INSERT_INTO("record");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getBikeId() != null) {
            VALUES("bike_id", "#{bikeId,jdbcType=DECIMAL}");
        }
        
        if (record.getCost() != null) {
            VALUES("cost", "#{cost,jdbcType=INTEGER}");
        }
        
        if (record.getStartTime() != null) {
            VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUserId() != null) {
        	VALUES("user_id", "#{userId,jdbcType=DECIMAL}");
		}
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(Record record) {
        BEGIN();
        UPDATE("record");
        
        if (record.getBikeId() != null) {
            SET("bike_id = #{bikeId,jdbcType=DECIMAL}");
        }
        
        if (record.getCost() != null) {
            SET("cost = #{cost,jdbcType=INTEGER}");
        }
        
        if (record.getStartTime() != null) {
            SET("start_time = #{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{endTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }
}
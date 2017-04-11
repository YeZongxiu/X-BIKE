package com.ucmed.common.dao.record;

import java.util.List;

import com.ucmed.common.dataobj.record.Record;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface RecordMapper {
    @Delete({
        "delete from record",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into record (id, bike_id, ",
        "cost, start_time, ",
        "end_time)",
        "values (#{id,jdbcType=DECIMAL}, #{bikeId,jdbcType=DECIMAL}, ",
        "#{cost,jdbcType=INTEGER}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP})"
    })
    int insert(Record record);

    @InsertProvider(type=RecordSqlProvider.class, method="insertSelective")
    int insertSelective(Record record);

    @Select({
        "select",
        "id, bike_id, cost, start_time, end_time",
        "from record",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="bike_id", property="bikeId", jdbcType=JdbcType.DECIMAL),
        @Result(column="cost", property="cost", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Record selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Record record);

    @Update({
        "update record",
        "set bike_id = #{bikeId,jdbcType=DECIMAL},",
          "cost = #{cost,jdbcType=INTEGER},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(Record record);
    
    @Select({
        "select",
        "id, bike_id, cost, start_time, end_time, user_id",
        "from record",
        "where user_id = #{userId,jdbcType=DECIMAL} and end_time is null"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="bike_id", property="bikeId", jdbcType=JdbcType.DECIMAL),
        @Result(column="cost", property="cost", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.DECIMAL)
    })
    Record selectByUserId(Long userId);
    
    @Select({
        "select",
        "r.id, r.bike_id, r.cost, r.start_time, r.end_time, r.user_id, t.bike_type_name, b.bike_no",
        "from record r, bike_type t, bike b",
        "where r.user_id = #{userId,jdbcType=DECIMAL} and b.id = r.bike_id",
        "and b.bike_type_id = t.id and end_time is not null"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="bike_id", property="bikeId", jdbcType=JdbcType.DECIMAL),
        @Result(column="cost", property="cost", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.DECIMAL),
        @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bike_no", property="bikeNo", jdbcType=JdbcType.VARCHAR)
    })
    List<Record> getUseList(Long userId);

    @Select({
            "select",
            "r.start_time, t.bike_type_name, b.bike_no, b.password, b.two_bar_codes",
            "from record r, bike_type t, bike b",
            "where r.user_id = #{userId,jdbcType=DECIMAL} and b.id = r.bike_id",
            "and b.bike_type_id = t.id and end_time is null"
    })
    @Results({
            @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="bike_no", property="bikeNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="two_bar_codes", property="twoBarCodes", jdbcType=JdbcType.VARCHAR)
    })
    List<Record> getRecordAndBike(Long userId);
}
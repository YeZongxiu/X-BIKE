package com.ucmed.common.dao.parking;

import com.ucmed.common.dataobj.parking.ForbidSpace;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface ForbidSpaceMapper {
    @Delete({
        "delete from forbid_space",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into forbid_space (id, longitude, ",
        "latitude, distance, ",
        "create_time, update_time, ",
        "start_time, end_time)",
        "values (#{id,jdbcType=INTEGER}, #{longitude,jdbcType=VARCHAR}, ",
        "#{latitude,jdbcType=VARCHAR}, #{distance,jdbcType=DOUBLE}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{startTime,jdbcType=TIMESTAMP}, #{endTime,jdbcType=TIMESTAMP})"
    })
    int insert(ForbidSpace record);

    @InsertProvider(type=ForbidSpaceSqlProvider.class, method="insertSelective")
    int insertSelective(ForbidSpace record);

    @Select({
        "select",
        "id, longitude, latitude, distance, create_time, update_time, start_time, end_time",
        "from forbid_space",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="distance", property="distance", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP)
    })
    ForbidSpace selectByPrimaryKey(Long id);

    @UpdateProvider(type=ForbidSpaceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ForbidSpace record);

    @Update({
        "update forbid_space",
        "set longitude = #{longitude,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=VARCHAR},",
          "distance = #{distance,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ForbidSpace record);

    @Select({
            "select",
            "id, longitude, latitude, distance, start_time, end_time",
            "from forbid_space",
            "where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
            "and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
            "and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
            "and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}",
            "and end_time >=  #{now,jdbcType=TIMESTAMP}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="distance", property="distance", jdbcType=JdbcType.DOUBLE),
            @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ForbidSpace> selectForbidSpace(@Param("minlng") Double minlng,
                                    @Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
                                    @Param("maxlat") Double maxlat, @Param("now") Date now);

    @Select({
            "select",
            "id, longitude, latitude, distance, start_time, end_time",
            "from forbid_space",
            "where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
            "and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
            "and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
            "and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}",
            "and end_time >=  #{now,jdbcType=TIMESTAMP}",
            "and start_time <=  #{now,jdbcType=TIMESTAMP}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="distance", property="distance", jdbcType=JdbcType.DOUBLE)
    })
    List<ForbidSpace> getForbid(@Param("minlng") Double minlng,
                                        @Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
                                        @Param("maxlat") Double maxlat, @Param("now") Date now);
}
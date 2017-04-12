package com.ucmed.common.dao.fix;

import com.ucmed.common.dataobj.fix.FixOrder;
import com.ucmed.common.dataobj.parking.ParkingSpace;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface FixOrderMapper {
    @Delete({
        "delete from fix_order",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into fix_order (id, bike_type_id, ",
        "bike_no, longitude, ",
        "latitude, photo, ",
        "remark, problem_id, two_bar_codes)",
        "values (#{id,jdbcType=DECIMAL}, #{bikeTypeId,jdbcType=DECIMAL}, ",
        "#{bikeNo,jdbcType=VARCHAR}, #{longitude,jdbcType=VARCHAR}, ",
        "#{latitude,jdbcType=VARCHAR}, #{photo,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{problem,jdbcType=VARCHAR}, #{twoBarCodes,jdbcType=VARCHAR})"
    })
    int insert(FixOrder record);

    @InsertProvider(type=FixOrderSqlProvider.class, method="insertSelective")
    int insertSelective(FixOrder record);

    @Select({
        "select",
        "id, bike_type_id, bike_no, longitude, latitude, photo, remark, problem",
        "from fix_order",
        "where id = #{id,jdbcType=DECIMAL} and status = 1"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="bike_type_id", property="bikeTypeId", jdbcType=JdbcType.DECIMAL),
        @Result(column="bike_no", property="bikeNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="problem", property="problem", jdbcType=JdbcType.VARCHAR)
    })
    FixOrder selectByPrimaryKey(Long id);

    @UpdateProvider(type=FixOrderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FixOrder record);

    @Update({
        "update fix_order",
        "set bike_type_id = #{bikeTypeId,jdbcType=INTEGER},",
          "bike_no = #{bikeNo,jdbcType=VARCHAR},",
          "longitude = #{longitude,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=VARCHAR},",
          "photo = #{photo,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "problem = #{problem,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(FixOrder record);

    @Select({
            "select fix_order.id,longitude,latitude,bike_no,two_bar_codes,photo,remark,problem,bike_type_name",
            "from fix_order",
            "JOIN bike_type ON fix_order.bike_type_id = bike_type.id",
            "where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
            "and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
            "and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
            "and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}",
            "and status = 0"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="bike_no", property="bikeNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="photo", property="photo", jdbcType=JdbcType.VARCHAR),
            @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
            @Result(column="problem", property="problem", jdbcType=JdbcType.VARCHAR),
            @Result(column="two_bar_codes", property="twoBarCodes", jdbcType=JdbcType.VARCHAR)
    })
    List<FixOrder> getBikeFixList(@Param("minlng") Double minlng,
                                      @Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
                                      @Param("maxlat") Double maxlat);

    @Update({
            "update fix_order",
            "set status = '1'",
            "where id = #{id,jdbcType=DECIMAL}",
            "and status = '0'"
    })
    int updateById(@Param("id") Long id);

    @Select({
            "select count(*) from fix_order",
            "where status = 0"})
    Long getBikeFixCount();

    @Update({
            "update fix_order",
            "set status = #{status,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=DECIMAL}",
            "and status = '1'"
    })
    int updateStatusById(@Param("id") Long id,
                           @Param("status") String status);

    @Select({
            "select fix_order.id, bike_type_id, bike_no, longitude, latitude, bike_type_name",
            "from bike_type, fix_order",
            "where park_id = #{parkId,jdbcType=DECIMAL}",
            "and bike_type.id = fix_order.bike_type_id"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="bike_type_id", property="bikeTypeId", jdbcType=JdbcType.DECIMAL),
            @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="bike_no", property="bikeNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR)
    })
    List<FixOrder> getFixOrderByPark(Long parkId);

    @Select({
            "select fix_order.id,longitude,latitude,bike_no,bike_type_name,bike_type_id",
            "from fix_order",
            "JOIN bike_type ON fix_order.bike_type_id = bike_type.id",
            "where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
            "and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
            "and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
            "and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}",
            "and status = 0"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="bike_type_id", property="bikeTypeId", jdbcType=JdbcType.DECIMAL),
            @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="bike_no", property="bikeNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR)
    })
    List<FixOrder> selectOutParkFix(@Param("minlng") Double minlng,
                                  @Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
                                  @Param("maxlat") Double maxlat);

    @Select({
            "select count(*) from fix_order",
            "where park_id = #{parkId,jdbcType=DECIMAL}"})
    Long getPrakFixCount(Long parkId);
}
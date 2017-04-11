package com.ucmed.common.dao.parking;

import java.util.List;

import com.ucmed.common.dataobj.parking.ParkingSpace;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ParkingSpaceMapper {
    @Delete({
        "delete from parking_space",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into parking_space (id, longitude, ",
        "latitude, bike_number, ",
        "park_number)",
        "values (#{id,jdbcType=DECIMAL}, #{longitude,jdbcType=VARCHAR}, ",
        "#{latitude,jdbcType=VARCHAR}, #{bikeNumber,jdbcType=DECIMAL}, ",
        "#{parkNumber,jdbcType=DECIMAL})"
    })
    int insert(ParkingSpace record);

    @InsertProvider(type=ParkingSpaceSqlProvider.class, method="insertSelective")
    int insertSelective(ParkingSpace record);

    @Select({
        "select",
        "id, longitude, latitude, bike_number, park_number",
        "from parking_space",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="bike_number", property="bikeNumber", jdbcType=JdbcType.DECIMAL),
        @Result(column="park_number", property="parkNumber", jdbcType=JdbcType.DECIMAL)
    })
    ParkingSpace selectByPrimaryKey(Long id);

    @UpdateProvider(type=ParkingSpaceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ParkingSpace record);

    @Update({
        "update parking_space",
        "set longitude = #{longitude,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=VARCHAR},",
          "bike_number = #{bikeNumber,jdbcType=DECIMAL},",
          "park_number = #{parkNumber,jdbcType=DECIMAL}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ParkingSpace record);
    
    @Select({
		"select",
		"id, bike_number, longitude, latitude, park_number",
		"from parking_space",
		"where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
		"and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
		"and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
		"and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="bike_number", property="bikeNumber", jdbcType=JdbcType.DECIMAL),
            @Result(column="park_number", property="parkNumber", jdbcType=JdbcType.DECIMAL)
    })
	List<ParkingSpace> selectAcount(@Param("minlng") Double minlng,
		@Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
		@Param("maxlat") Double maxlat);
    
    @Select({
		"select",
		"id, longitude, latitude, bike_number, park_number",
		"from parking_space",
		"where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
		"and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
		"and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
		"and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}"})
    @Results({
    	@Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="bike_number", property="bikeNumber", jdbcType=JdbcType.DECIMAL),
        @Result(column="park_number", property="parkNumber", jdbcType=JdbcType.DECIMAL)
    })
	List<ParkingSpace> getParkBike(@Param("minlng") Double minlng,
		@Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
		@Param("maxlat") Double maxlat);

    @Select({
            "select",
            "id, longitude, latitude, bike_number, park_number",
            "from parking_space",
            "where longitude = #{longitude,jdbcType=VARCHAR}",
            "and latitude = #{latitude,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
            @Result(column="bike_number", property="bikeNumber", jdbcType=JdbcType.DECIMAL),
            @Result(column="park_number", property="parkNumber", jdbcType=JdbcType.DECIMAL)
    })
    ParkingSpace getSameParking(@Param("longitude") String longitude, @Param("latitude") String latitude);

}
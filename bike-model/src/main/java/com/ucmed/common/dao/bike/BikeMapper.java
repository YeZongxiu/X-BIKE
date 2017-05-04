package com.ucmed.common.dao.bike;

import java.util.List;

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

import com.ucmed.common.dataobj.bike.Bike;

public interface BikeMapper {
	@Delete({ "delete from bike", "where id = #{id,jdbcType=DECIMAL}" })
	int deleteByPrimaryKey(Long id);

	@Insert({
			"insert into bike (id, bike_no, ",
			"password, longitude, ",
			"latitude, status, bike_type_id, create_time)",
			"values (#{id,jdbcType=DECIMAL}, #{bikeNo,jdbcType=VARCHAR}, ",
			"#{password,jdbcType=VARCHAR}, #{longitude,jdbcType=VARCHAR}, ",
			"#{latitude,jdbcType=VARCHAR}, #{status,jdbcType=CHAR}, #{bikeTypeId,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP})" })
	int insert(Bike record);

	@InsertProvider(type = BikeSqlProvider.class, method = "insertSelective")
	int insertSelective(Bike record);

	@Select({
			"select",
			"id, bike_no, password, longitude, latitude, status, bike_type_id, park_id",
			"from bike", "where id = #{id,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL) })
	Bike selectByPrimaryKey(Long id);

	@UpdateProvider(type = BikeSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(Bike record);

	@Update({ "update bike", "set bike_no = #{bikeNo,jdbcType=VARCHAR},",
			"password = #{password,jdbcType=VARCHAR},",
			"longitude = #{longitude,jdbcType=VARCHAR},",
			"latitude = #{latitude,jdbcType=VARCHAR},",
			"status = #{status,jdbcType=CHAR},",
			"bike_type_id = #{bikeTypeId,jdbcType=DECIMAL},",
			"park_id = #{parkId,jdbcType=DECIMAL},",
			"is_delete = #{isDelete,jdbcType=CHAR}",
			"where id = #{id,jdbcType=DECIMAL}" })
	int updateByPrimaryKey(Bike record);

	@Select({
			"select",
			"id, bike_no, password, longitude, latitude, status, bike_type_id, park_id",
			" from bike", "where (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
			"and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
			"and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
			"and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}",
			"and status = 0 and park_id is null" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL) })
	List<Bike> selectAcount(@Param("minlng") Double minlng,
			@Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
			@Param("maxlat") Double maxlat);

	@Select({
			"select",
			"b.id, b.bike_no, b.password, b.longitude, b.latitude, b.status, b.bike_type_id, t.bike_type_name, t.bike_type_url",
			"from bike b, bike_type t ", "where b.bike_type_id = t.id",
			"and (longitude+0) >= #{minlng,jdbcType=DECIMAL}",
			"and (longitude+0) <= #{maxlng,jdbcType=DECIMAL}",
			"and (latitude+0) >= #{minlat,jdbcType=DECIMAL}",
			"and (latitude+0) <= #{maxlat,jdbcType=DECIMAL}",
			"and status = 0 and park_id is null and is_delete = 0" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL),
			@Result(column = "bike_type_name", property = "bikeTypeName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "bike_type_url", property = "bikeTypeUrl", jdbcType = JdbcType.VARCHAR) })
	List<Bike> selectOutParkBike(@Param("minlng") Double minlng,
			@Param("maxlng") Double maxlng, @Param("minlat") Double minlat,
			@Param("maxlat") Double maxlat);

	@Select({
			"select",
			"id, bike_no, password, longitude, latitude, status, bike_type_id, two_bar_codes, park_id, is_delete",
			"from bike", "where bike_no = #{bikeNo,jdbcType=VARCHAR}",
			"and bike_type_id = #{typeId,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "two_bar_codes", property = "twoBarCodes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL),
			@Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.CHAR)})
	Bike getBikeByBikeNo(@Param("bikeNo") String bikeNo,
			@Param("typeId") Long typeId);

	@Select({
			"select",
			"id, bike_no, password, longitude, latitude, status, bike_type_id, two_bar_codes, park_id, is_delete ",
			"from bike",
			"where two_bar_codes = #{twoBarCodes,jdbcType=VARCHAR}",
			"and bike_type_id = #{typeId,jdbcType=DECIMAL}" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "two_bar_codes", property = "twoBarCodes", jdbcType = JdbcType.VARCHAR),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL),
			@Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.CHAR) })
	Bike getBikeByTwoBarCodes(@Param("twoBarCodes") String twoBarCodes,
			@Param("typeId") Long typeId);

	@Select({
			"select",
			"bike.id, bike_no, password, longitude, latitude, status, bike_type_id, park_id, is_delete, bike_type_name",
			"from bike, bike_type", "where park_id = #{park_id,jdbcType=DECIMAL} and is_delete = 0 and bike_type.id = bike.bike_type_id" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL),
			@Result(column = "bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
			@Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.CHAR) })
	List<Bike> getBikesByPark(Long park_id);

	@Select({
			"select",
			"id, bike_no, password, longitude, latitude, status, bike_type_id, park_id, is_delete",
			"from bike", "where park_id = #{park_id,jdbcType=DECIMAL} and is_delete = 0 and status = 2" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.DECIMAL, id = true),
			@Result(column = "bike_no", property = "bikeNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "longitude", property = "longitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "latitude", property = "latitude", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.CHAR),
			@Result(column = "bike_type_id", property = "bikeTypeId", jdbcType = JdbcType.INTEGER),
			@Result(column = "park_id", property = "parkId", jdbcType = JdbcType.DECIMAL),
			@Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.CHAR) })
	List<Bike> getBikesFixByPark(Long park_id);
}
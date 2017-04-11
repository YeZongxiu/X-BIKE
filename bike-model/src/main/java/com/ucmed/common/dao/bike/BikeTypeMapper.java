package com.ucmed.common.dao.bike;

import com.ucmed.common.dataobj.bike.BikeType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface BikeTypeMapper {
    @Delete({
        "delete from bike_type",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into bike_type (id, bike_type_name, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=DECIMAL}, #{bikeTypeName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(BikeType record);

    @InsertProvider(type=BikeTypeSqlProvider.class, method="insertSelective")
    int insertSelective(BikeType record);

    @Select({
        "select",
        "id, bike_type_name, create_time, update_time",
        "from bike_type",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    BikeType selectByPrimaryKey(Integer id);

    @UpdateProvider(type=BikeTypeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BikeType record);

    @Update({
        "update bike_type",
        "set bike_type_name = #{bikeTypeName,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(BikeType record);

    @Select({
            "select",
            "id, bike_type_name, create_time, update_time",
            "from bike_type"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="bike_type_name", property="bikeTypeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BikeType> selectAll();
}
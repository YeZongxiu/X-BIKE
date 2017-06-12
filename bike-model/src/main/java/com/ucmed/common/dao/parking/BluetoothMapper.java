package com.ucmed.common.dao.parking;

import com.ucmed.common.dataobj.parking.Bluetooth;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface BluetoothMapper {
    @Delete({
        "delete from bluetooth",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into bluetooth (id, space_id, ",
        "mac)",
        "values (#{id,jdbcType=DECIMAL}, #{spaceId,jdbcType=DECIMAL}, ",
        "#{mac,jdbcType=VARCHAR})"
    })
    int insert(Bluetooth record);

    @InsertProvider(type=BluetoothSqlProvider.class, method="insertSelective")
    int insertSelective(Bluetooth record);

    @Select({
        "select",
        "id, space_id, mac",
        "from bluetooth",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="space_id", property="spaceId", jdbcType=JdbcType.DECIMAL),
        @Result(column="mac", property="mac", jdbcType=JdbcType.VARCHAR)
    })
    Bluetooth selectByPrimaryKey(Long id);

    @UpdateProvider(type=BluetoothSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Bluetooth record);

    @Update({
        "update bluetooth",
        "set space_id = #{spaceId,jdbcType=DECIMAL},",
          "mac = #{mac,jdbcType=VARCHAR},",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(Bluetooth record);

    @Select({
            "select",
            "id, space_id, mac",
            "from bluetooth",
            "where mac = #{mac,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="space_id", property="spaceId", jdbcType=JdbcType.DECIMAL),
            @Result(column="mac", property="mac", jdbcType=JdbcType.VARCHAR)
    })
    Bluetooth selectByMac(String mac);

    @Select({
            "select",
            "id, space_id, mac",
            "from bluetooth",
            "where bluetooth_no = #{no,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="space_id", property="spaceId", jdbcType=JdbcType.DECIMAL),
            @Result(column="mac", property="mac", jdbcType=JdbcType.VARCHAR)
    })
    Bluetooth getBluetoothByNo(String no);
}
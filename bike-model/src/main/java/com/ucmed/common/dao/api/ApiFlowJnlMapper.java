package com.ucmed.common.dao.api;

import com.ucmed.common.dataobj.api.ApiFlowJnl;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ApiFlowJnlMapper {
    @Delete({
        "delete from api_flow_jnl",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into api_flow_jnl (id, client_datetime, ",
        "client_date, hour, month, ",
        "day, year, client_transName, ",
        "client_user, data, ",
        "return_data, ip, ",
        "tmp5)",
        "values (#{id,jdbcType=DECIMAL}, #{clientDatetime,jdbcType=TIMESTAMP}, ",
        "#{clientDate,jdbcType=VARCHAR}, #{hour,jdbcType=CHAR}, #{month,jdbcType=CHAR}, ",
        "#{day,jdbcType=CHAR}, #{year,jdbcType=CHAR}, #{clientTransname,jdbcType=VARCHAR}, ",
        "#{clientUser,jdbcType=VARCHAR}, #{data,jdbcType=VARCHAR}, ",
        "#{returnData,jdbcType=VARCHAR}, #{ip,jdbcType=VARCHAR}, ",
        "#{tmp5,jdbcType=VARCHAR})"
    })
    int insert(ApiFlowJnl record);

    @InsertProvider(type=ApiFlowJnlSqlProvider.class, method="insertSelective")
    int insertSelective(ApiFlowJnl record);

    @Select({
        "select",
        "id, client_datetime, client_date, hour, month, day, year, client_transName, ",
        "client_user, data, return_data, ip, tmp5",
        "from api_flow_jnl",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="client_datetime", property="clientDatetime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="client_date", property="clientDate", jdbcType=JdbcType.VARCHAR),
        @Result(column="hour", property="hour", jdbcType=JdbcType.CHAR),
        @Result(column="month", property="month", jdbcType=JdbcType.CHAR),
        @Result(column="day", property="day", jdbcType=JdbcType.CHAR),
        @Result(column="year", property="year", jdbcType=JdbcType.CHAR),
        @Result(column="client_transName", property="clientTransname", jdbcType=JdbcType.VARCHAR),
        @Result(column="client_user", property="clientUser", jdbcType=JdbcType.VARCHAR),
        @Result(column="data", property="data", jdbcType=JdbcType.VARCHAR),
        @Result(column="return_data", property="returnData", jdbcType=JdbcType.VARCHAR),
        @Result(column="ip", property="ip", jdbcType=JdbcType.VARCHAR),
        @Result(column="tmp5", property="tmp5", jdbcType=JdbcType.VARCHAR)
    })
    ApiFlowJnl selectByPrimaryKey(Long id);

    @UpdateProvider(type=ApiFlowJnlSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ApiFlowJnl record);

    @Update({
        "update api_flow_jnl",
        "set client_datetime = #{clientDatetime,jdbcType=TIMESTAMP},",
          "client_date = #{clientDate,jdbcType=VARCHAR},",
          "hour = #{hour,jdbcType=CHAR},",
          "month = #{month,jdbcType=CHAR},",
          "day = #{day,jdbcType=CHAR},",
          "year = #{year,jdbcType=CHAR},",
          "client_transName = #{clientTransname,jdbcType=VARCHAR},",
          "client_user = #{clientUser,jdbcType=VARCHAR},",
          "data = #{data,jdbcType=VARCHAR},",
          "return_data = #{returnData,jdbcType=VARCHAR},",
          "ip = #{ip,jdbcType=VARCHAR},",
          "tmp5 = #{tmp5,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(ApiFlowJnl record);
}
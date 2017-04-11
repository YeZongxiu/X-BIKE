package com.ucmed.common.dao.fix;

import com.ucmed.common.dataobj.fix.Problem;
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

public interface ProblemMapper {
    @Delete({
        "delete from problem",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into problem (id, problem_name, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=DECIMAL}, #{problemName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Problem record);

    @InsertProvider(type=ProblemSqlProvider.class, method="insertSelective")
    int insertSelective(Problem record);

    @Select({
        "select",
        "id, problem_name, create_time, update_time",
        "from problem",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
        @Result(column="problem_name", property="problemName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Problem selectByPrimaryKey(Long id);

    @UpdateProvider(type=ProblemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Problem record);

    @Update({
        "update problem",
        "set problem_name = #{problemName,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=DECIMAL}"
    })
    int updateByPrimaryKey(Problem record);

    @Select({
            "select",
            "id, problem_name, create_time, update_time",
            "from problem"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.DECIMAL, id=true),
            @Result(column="problem_name", property="problemName", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Problem> selectAll();
}
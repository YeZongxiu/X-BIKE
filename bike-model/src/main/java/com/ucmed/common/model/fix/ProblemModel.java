package com.ucmed.common.model.fix;

import com.ucmed.common.service.fix.ProblemService;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ProblemModel {
    private Long id;

    private String problemName;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public JSONObject toJsonObject(){
        JSONObject json = new JSONObject();
        if (StringUtil.isNotBlank(this.id)){
            json.put("problem_id", this.id);
        }
        if (StringUtil.isNotBlank(this.problemName)){
            json.put("problem_name", this.problemName);
        }
        return  json;
    }
}
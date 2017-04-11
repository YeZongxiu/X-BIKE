package com.ucmed.common.model.bike;

import com.ucmed.common.service.bike.BikeTypeService;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

public class BikeTypeModel {
    private Long id;

    private String bikeTypeName;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBikeTypeName() {
        return bikeTypeName;
    }

    public void setBikeTypeName(String bikeTypeName) {
        this.bikeTypeName = bikeTypeName;
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
            json.put("type_id", this.id);
        }
        if (StringUtil.isNotBlank(this.bikeTypeName)){
            json.put("type_name", this.bikeTypeName);
        }
        return  json;
    }
}
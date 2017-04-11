package com.ucmed.common.dataobj.bike;

import java.util.Date;

public class BikeType {
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
}
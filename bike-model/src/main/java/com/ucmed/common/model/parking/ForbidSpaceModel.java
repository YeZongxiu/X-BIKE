package com.ucmed.common.model.parking;

import com.ucmed.common.util.DateUtil;
import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.Date;

public class ForbidSpaceModel {
    private Integer id;

    private String longitude;

    private String latitude;

    private Double distance;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public JSONObject getJsonObject(){
        JSONObject json = new JSONObject();
        if (StringUtil.isNotBlank(this.id)){
            json.put("id", this.id);
        }
        if (StringUtil.isNotBlank(this.longitude)){
            json.put("longitude", this.longitude);
        }
        if (StringUtil.isNotBlank(this.latitude)){
            json.put("latitude", this.latitude);
        }
        if (StringUtil.isNotBlank(this.startTime)){
            json.put("start_time", DateUtil.dateToString1(this.startTime));
        }
        if (StringUtil.isNotBlank(this.endTime)){
            json.put("end_time", DateUtil.dateToString1(this.endTime));
        }
        if (this.distance != null){
            json.put("radius", this.distance);
        }
        if (StringUtil.isNotBlank(this.message)){
            json.put("message", this.message);
        }
        return  json;
    }
}
package com.ucmed.common.model.fix;

import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.Date;

public class FixOrderModel {
    private Long id;

    private Long bikeTypeId;

    private String bikeNo;

    private String longitude;

    private String latitude;

    private String photo;

    private String remark;

    private String twoBarCodes;

    private String problem;

    private String bikeTypeName;

    private String status;

    private Long parkId;

    private Date createTime;

    private Date updateTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
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

    public String getBikeTypeName() {
        return bikeTypeName;
    }

    public void setBikeTypeName(String bikeTypeName) {
        this.bikeTypeName = bikeTypeName;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBikeTypeId() {
        return bikeTypeId;
    }

    public void setBikeTypeId(Long bikeTypeId) {
        this.bikeTypeId = bikeTypeId;
    }

    public String getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(String bikeNo) {
        this.bikeNo = bikeNo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTwoBarCodes() {
        return twoBarCodes;
    }

    public void setTwoBarCodes(String twoBarCodes) {
        this.twoBarCodes = twoBarCodes;
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
        if (StringUtil.isNotBlank(this.bikeTypeName)){
            json.put("bike_type_name", this.bikeTypeName);
        }
        if (StringUtil.isNotBlank(this.bikeNo)){
            json.put("bike_no", this.bikeNo);
        }
        if (StringUtil.isNotBlank(this.status)){
            json.put("status", FixStatus.equals(Long.parseLong(this.status)).getStatusName());
        }
        return  json;
    }

    public enum FixStatus {
        UNTREATED(0l,"未处理"),PROCESSIONG(1l,"前往处理"),NOFOUND(2l,"未找到"),PROCESSED(3l,"已处理"),PROBLEM(4l,"问题车辆（未发现故障）");
        private Long statusId;
        private String statusName;

        FixStatus(Long statusId, String statusName) {
            this.statusId = statusId;
            this.statusName = statusName;
        }

        public Long getStatusId() {
            return statusId;
        }

        public String getStatusName() {
            return statusName;
        }

        public static FixStatus equals(Long statusId){
            for(FixStatus fixStatus :FixStatus.values()){
               if (fixStatus.getStatusId() == statusId)
                   return fixStatus;
            }
            return null;
        }
    }
}
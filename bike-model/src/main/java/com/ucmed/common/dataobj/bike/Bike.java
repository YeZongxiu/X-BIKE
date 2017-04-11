package com.ucmed.common.dataobj.bike;

import java.util.Date;

public class Bike {
    private Long id;

    private String bikeNo;

    private String password;

    private String longitude;

    private String latitude;

    private String status;

    private Long bikeTypeId;

    private String twoBarCodes;

    private Date createTime;

    private Date updateTime;
    
    private Long parkId;
    
    private String bikeTypeName;
    
    private String bikeTypeUrl;

    private String isDelete;

    private String parkLongitude;

    private String parkLatitude;

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTwoBarCodes() {
        return twoBarCodes;
    }

    public void setTwoBarCodes(String twoBarCodes) {
        this.twoBarCodes = twoBarCodes;
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
    
    public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public String getBikeTypeName() {
		return bikeTypeName;
	}

	public void setBikeTypeName(String bikeTypeName) {
		this.bikeTypeName = bikeTypeName;
	}

	public String getBikeTypeUrl() {
		return bikeTypeUrl;
	}

	public void setBikeTypeUrl(String bikeTypeUrl) {
		this.bikeTypeUrl = bikeTypeUrl;
	}
	
}
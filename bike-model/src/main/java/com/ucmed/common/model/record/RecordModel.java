package com.ucmed.common.model.record;

import java.util.Date;

public class RecordModel {
    private Long id;

    private Long bikeId;

    private Integer cost;

    private Date startTime;

    private Date endTime;
    
    private Long userId;
    
    private String bikeTypeName;
    
    private String bikeNo;

    private String twoBarCodes;

    private String password;

    public String getTwoBarCodes() {
        return twoBarCodes;
    }

    public void setTwoBarCodes(String twoBarCodes) {
        this.twoBarCodes = twoBarCodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getBikeNo() {
		return bikeNo;
	}

	public void setBikeNo(String bikeNo) {
		this.bikeNo = bikeNo;
	}

	public String getBikeTypeName() {
		return bikeTypeName;
	}

	public void setBikeTypeName(String bikeTypeName) {
		this.bikeTypeName = bikeTypeName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
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
}
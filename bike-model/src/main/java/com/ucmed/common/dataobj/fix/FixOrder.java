package com.ucmed.common.dataobj.fix;

public class FixOrder {
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
}
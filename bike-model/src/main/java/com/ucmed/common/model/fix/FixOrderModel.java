package com.ucmed.common.model.fix;

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
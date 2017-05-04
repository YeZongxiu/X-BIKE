package com.ucmed.common.model.parking;

import com.ucmed.common.util.StringUtil;
import net.sf.json.JSONObject;

public class ParkingSpaceModel {
    private Long id;

    private String longitude;

    private String latitude;

    private Long bikeNumber;

    private Long parkNumber;
    
    private Double percentum;

    private Long fixNumber;

    private Double fixPercentum;

    private String[] macList;

    public String[] getMacList() {
        return macList;
    }

    public void setMacList(String[] macList) {
        this.macList = macList;
    }

    public Long getFixNumber() {
        return fixNumber;
    }

    public void setFixNumber(Long fixNumber) {
        this.fixNumber = fixNumber;
    }

    public Double getFixPercentum() {
        return fixPercentum;
    }

    public void setFixPercentum(Double fixPercentum) {
        this.fixPercentum = fixPercentum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(Long bikeNumber) {
        this.bikeNumber = bikeNumber;
    }

    public Long getParkNumber() {
        return parkNumber;
    }

    public void setParkNumber(Long parkNumber) {
        this.parkNumber = parkNumber;
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

	public Double getPercentum() {
		return percentum;
	}

	public void setPercentum(Double percentum) {
		this.percentum = percentum;
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
        if (StringUtil.isNotBlank(this.percentum)){
            json.put("percentum", this.percentum);
        }
        if (StringUtil.isNotBlank(this.bikeNumber)){
            json.put("quantity", this.bikeNumber);
        }
        if (StringUtil.isNotBlank(this.fixPercentum)){
            json.put("fix_percentum", this.fixPercentum);
        }
        if (StringUtil.isNotBlank(this.fixNumber)){
            json.put("fix_quantity", this.fixNumber);
        }
        if (this.parkNumber != null){
            json.put("park_number", this.parkNumber);
        }
        return  json;
    }
    
}
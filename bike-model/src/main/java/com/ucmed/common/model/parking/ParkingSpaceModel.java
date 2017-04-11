package com.ucmed.common.model.parking;

public class ParkingSpaceModel {
    private Long id;

    private String longitude;

    private String latitude;

    private Long bikeNumber;

    private Long parkNumber;
    
    private Double percentum;

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
    
}
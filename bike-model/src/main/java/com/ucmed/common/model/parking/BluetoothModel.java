package com.ucmed.common.model.parking;

public class BluetoothModel {
    private Long id;

    private Long spaceId;

    private String mac;

    private String bluetoothNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBluetoothNo() {
        return bluetoothNo;
    }

    public void setBluetoothNo(String bluetoothNo) {
        this.bluetoothNo = bluetoothNo;
    }
}
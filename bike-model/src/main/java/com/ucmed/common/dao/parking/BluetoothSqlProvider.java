package com.ucmed.common.dao.parking;

import com.ucmed.common.dataobj.parking.Bluetooth;
import com.ucmed.common.dataobj.parking.ParkingSpace;

import static org.apache.ibatis.jdbc.SqlBuilder.*;


public class BluetoothSqlProvider {

    public String insertSelective(Bluetooth record) {
        BEGIN();
        INSERT_INTO("bluetooth");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=DECIMAL}");
        }
        
        if (record.getSpaceId() != null) {
            VALUES("space_id", "#{spaceId,jdbcType=DECIMAL}");
        }
        
        if (record.getMac() != null) {
            VALUES("mac", "#{mac,jdbcType=VARCHAR}");
        }

        if (record.getBluetoothNo() != null) {
            VALUES("bluetooth_no", "#{bluetoothNo,jdbcType=VARCHAR}");
        }

        return SQL();
    }

    public String updateByPrimaryKeySelective(Bluetooth record) {
        BEGIN();
        UPDATE("bluetooth");
        
        if (record.getSpaceId() != null) {
            SET("space_id = #{spaceId,jdbcType=DECIMAL}");
        }
        
        if (record.getMac() != null) {
            SET("mac = #{mac,jdbcType=VARCHAR}");
        }

        WHERE("id = #{id,jdbcType=DECIMAL}");
        
        return SQL();
    }

    public String getBluetoothList(String type){
        StringBuffer sb = new StringBuffer();
        sb.append("select * from bluetooth");
        if ("1".equals(type)){
            sb.append("where space_id = null");
        } else if ("2".equals(type)){
            sb.append("where space_id != null");
        }
        return sb.toString();
    }
}
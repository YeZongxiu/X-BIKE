package com.ucmed.common.service.parking;

import com.ucmed.common.dao.parking.BluetoothMapper;
import com.ucmed.common.dao.parking.BluetoothSqlProvider;
import com.ucmed.common.dataobj.parking.Bluetooth;
import com.ucmed.common.model.parking.BluetoothModel;
import com.ucmed.common.util.ModelDataObjectUtil;
import com.ucmed.common.util.StringUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ucmed on 2017/3/16.
 */
public class BluetoothServiceImpl implements BluetoothService {
	private BluetoothMapper bluetoothMapper;

	public void setBluetoothMapper(BluetoothMapper bluetoothMapper) {
		this.bluetoothMapper = bluetoothMapper;
	}

	@Override
	public BluetoothModel getBluetoothByMac(String mac) {
		BluetoothModel model = null;
		Bluetooth bluetooth;
		if (StringUtil.isNotBlank(mac)){
			bluetooth = bluetoothMapper.selectByMac(mac);
			if (bluetooth != null)
				model =  ModelDataObjectUtil.model2do(bluetooth, BluetoothModel.class);
		}
		return model;
	}

	@Override
	public void addBluetooth(BluetoothModel model) {
		Bluetooth bluetooth = ModelDataObjectUtil.model2do(model, Bluetooth.class);
		bluetoothMapper.insertSelective(bluetooth);
		model.setId(bluetooth.getId());
	}

	@Override
	public BluetoothModel getBluetoothByNo(String no) {
		BluetoothModel model = null;
		Bluetooth bluetooth;
		if (StringUtil.isNotBlank(no)){
			bluetooth = bluetoothMapper.getBluetoothByNo(no);
			if (bluetooth != null)
				model =  ModelDataObjectUtil.model2do(bluetooth, BluetoothModel.class);
		}
		return model;
	}

	@Override
	public List<BluetoothModel> getBluetoothList(String type) {
		List<Bluetooth> models = bluetoothMapper.getBluetoothList(type);
		List<BluetoothModel> list = new ArrayList<>();
		if (null != models && 0 != models.size()){
			for (BluetoothModel model : list){
				list.add(ModelDataObjectUtil.model2do(model, BluetoothModel.class));
			}
		}
		return list;
	}

	@Delete({
        "delete from bluetooth",
        "where id = #{id,jdbcType=DECIMAL}"
    })
	public int deleteByPrimaryKey(Long id) {
		return bluetoothMapper.deleteByPrimaryKey(id);
	}

	@InsertProvider(type=BluetoothSqlProvider.class, method="insertSelective")
	public int insertSelective(Bluetooth record) {
		return bluetoothMapper.insertSelective(record);
	}

	@Select({
            "select",
            "id, space_id, mac",
            "from bluetooth",
            "where mac = #{mac,jdbcType=VARCHAR}"
    })
	@Results({
            @Result(column="id", property="id", jdbcType= JdbcType.DECIMAL, id=true),
            @Result(column="space_id", property="spaceId", jdbcType=JdbcType.DECIMAL),
            @Result(column="mac", property="mac", jdbcType=JdbcType.VARCHAR)
    })
	public Bluetooth selectByMac(String mac) {
		return bluetoothMapper.selectByMac(mac);
	}

	@Update({
        "update bluetooth",
        "set space_id = #{spaceId,jdbcType=DECIMAL},",
          "mac = #{mac,jdbcType=VARCHAR},",
        "where id = #{id,jdbcType=DECIMAL}"
    })
	public int updateByPrimaryKey(Bluetooth record) {
		return bluetoothMapper.updateByPrimaryKey(record);
	}

	@UpdateProvider(type=BluetoothSqlProvider.class, method="updateByPrimaryKeySelective")
	public int updateByPrimaryKeySelective(Bluetooth record) {
		return bluetoothMapper.updateByPrimaryKeySelective(record);
	}

	@Select({
        "select",
        "id, space_id, mac",
        "from bluetooth",
        "where id = #{id,jdbcType=DECIMAL}"
    })
	@Results({
        @Result(column="id", property="id", jdbcType= JdbcType.DECIMAL, id=true),
        @Result(column="space_id", property="spaceId", jdbcType=JdbcType.DECIMAL),
        @Result(column="mac", property="mac", jdbcType=JdbcType.VARCHAR)
    })
	public Bluetooth selectByPrimaryKey(Long id) {
		return bluetoothMapper.selectByPrimaryKey(id);
	}

	@Insert({
        "insert into bluetooth (id, space_id, ",
        "mac)",
        "values (#{id,jdbcType=DECIMAL}, #{spaceId,jdbcType=DECIMAL}, ",
        "#{mac,jdbcType=VARCHAR})"
    })
	public int insert(Bluetooth record) {
		return bluetoothMapper.insert(record);
	}
}

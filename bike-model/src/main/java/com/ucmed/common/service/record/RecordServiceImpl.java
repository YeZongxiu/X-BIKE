package com.ucmed.common.service.record;

import java.util.ArrayList;
import java.util.List;

import com.ucmed.common.dao.record.RecordMapper;
import com.ucmed.common.dataobj.record.Record;
import com.ucmed.common.model.record.RecordModel;
import com.ucmed.common.util.ModelDataObjectUtil;

/**
 * Created by ucmed on 2017/3/16.
 */
public class RecordServiceImpl implements RecordService {
	private RecordMapper recordMapper;

	public void setRecordMapper(RecordMapper recordMapper) {
		this.recordMapper = recordMapper;
	}

	@Override
	public void addRecordInfo(RecordModel model) {
		Record record = ModelDataObjectUtil.model2do(model, Record.class);
		recordMapper.insertSelective(record);
        model.setId(record.getId());
	}

	@Override
	public void updateRecord(RecordModel model) {
		recordMapper.updateByPrimaryKey(ModelDataObjectUtil.model2do(model, Record.class));
	}

	@Override
	public RecordModel getRecordByUserId(Long userId) {
		Record record = recordMapper.selectByUserId(userId);
        return ModelDataObjectUtil.do2model(record, RecordModel.class);
    }

	@Override
	public List<RecordModel> getUseList(Long userId) {
		List<Record> list = recordMapper.getUseList(userId);
		if (null == list || list.isEmpty()) {
			return new ArrayList<RecordModel>();
		}
		List<RecordModel> models = new ArrayList<>();
		for (Record record : list) {
			models.add(ModelDataObjectUtil.do2model(record, RecordModel.class));
		}
		return models;
	}

	@Override
	public List<RecordModel> getRecordAndBike(Long userId) {
		List<Record> list = recordMapper.getRecordAndBike(userId);
		if (null == list || list.isEmpty()) {
			return new ArrayList<RecordModel>();
		}
		List<RecordModel> models = new ArrayList<>();
		for (Record record : list) {
			models.add(ModelDataObjectUtil.do2model(record, RecordModel.class));
		}
		return models;
	}
}

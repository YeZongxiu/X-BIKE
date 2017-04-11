package com.ucmed.common.service.record;

import java.util.List;

import com.ucmed.common.model.record.RecordModel;

/**
 * Created by ucmed on 2017/3/16.
 */
public interface RecordService {
	public void addRecordInfo(RecordModel model);
	
	public void updateRecord(RecordModel model);
	
	public RecordModel getRecordByUserId(Long userId);
	
	public List<RecordModel> getUseList(Long userId);

	public List<RecordModel> getRecordAndBike(Long userId);
}

package com.ucmed.common.config;

public class CommonConfig {
	private String hospitalName;// 医院名
	private String hospitalId;// 医院内部id
	private String zwjkHospitalId;// 掌握健康医院id
	private String HospitalDistID;// 医院内部分区id
	private String rubikxImageUrl;// 访问医生是的头部地址
	private String onlineUserHeadDefault;// 专家在线用户默认头像
	private String onlineDoctorHeadDefault;// 专家在线医生默认头像
	private String onlineUserPushAppkey;// 专家在线用户信鸽推送appkey
	private String onlineDoctorPushAppkey;// 专家在线医生信鸽推送appkey

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getZwjkHospitalId() {
		return zwjkHospitalId;
	}

	public void setZwjkHospitalId(String zwjkHospitalId) {
		this.zwjkHospitalId = zwjkHospitalId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalDistID() {
		return HospitalDistID;
	}

	public void setHospitalDistID(String hospitalDistID) {
		HospitalDistID = hospitalDistID;
	}

	public String getRubikxImageUrl() {
		return rubikxImageUrl;
	}

	public void setRubikxImageUrl(String rubikxImageUrl) {
		this.rubikxImageUrl = rubikxImageUrl;
	}

	public String getOnlineUserHeadDefault() {
		return onlineUserHeadDefault;
	}

	public void setOnlineUserHeadDefault(String onlineUserHeadDefault) {
		this.onlineUserHeadDefault = onlineUserHeadDefault;
	}

	public String getOnlineDoctorHeadDefault() {
		return onlineDoctorHeadDefault;
	}

	public void setOnlineDoctorHeadDefault(String onlineDoctorHeadDefault) {
		this.onlineDoctorHeadDefault = onlineDoctorHeadDefault;
	}

	public String getOnlineUserPushAppkey() {
		return onlineUserPushAppkey;
	}

	public void setOnlineUserPushAppkey(String onlineUserPushAppkey) {
		this.onlineUserPushAppkey = onlineUserPushAppkey;
	}

	public String getOnlineDoctorPushAppkey() {
		return onlineDoctorPushAppkey;
	}

	public void setOnlineDoctorPushAppkey(String onlineDoctorPushAppkey) {
		this.onlineDoctorPushAppkey = onlineDoctorPushAppkey;
	}

}

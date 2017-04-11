package com.ucmed.common.api;

import com.ucmed.common.api.web.Api;

import java.util.Map;

public class ApiFactoryImpl implements ApiFactory {

	private Map<String, Api> map;
	
	public final void setMap(Map<String, Api> map) {
		this.map = map;
	}

	@Override
	public Api getApi(String apiName) {
		return map.get(apiName);
	}

}

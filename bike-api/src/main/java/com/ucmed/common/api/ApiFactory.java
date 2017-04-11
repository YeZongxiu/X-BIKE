package com.ucmed.common.api;

import com.ucmed.common.api.web.Api;

public interface ApiFactory {

	public Api getApi(String apiName);

}

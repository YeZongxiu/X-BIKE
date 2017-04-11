package com.ucmed.common.api.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ucmed.common.api.ApiFactory;
import com.ucmed.common.util.Loggers;

/**
 * for zhejiang 10086.
 * @author shin
 */
@Controller
@RequestMapping("/api/exec.htm")
public class ApiController extends AbsApiController {
    private static final Logger LOG = Logger.getLogger(Loggers.API);
    @Autowired
    private ApiFactory apiFactory;
    /**
     * ApiController worker.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param map ModelMap
     * @return Return message for action.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String api(HttpServletRequest request, HttpServletResponse response,
            ModelMap map) {
        return absApi(request, response, map);
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    protected ApiFactory getApiFactory() {
        return this.apiFactory;
    }
}

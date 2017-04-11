package com.ucmed.common.filter;

import com.ucmed.common.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 。 记录缓存。
 *
 * @author shin
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(SessionInterceptor.class);
    private List<String> paths;

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public static Logger getLog() {
        return LOG;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object hanlder) {
        try {
            String path = request.getRequestURI();
            String[] tempPath = path.split("/");
            String temp = "";
            boolean isPass = false;
            for (int i = 0; i < paths.size(); i++) {
                if (paths.get(i).equals(path)) {
                    isPass = true;
                    break;
                }
            }
            if (tempPath.length > 1)
                temp = tempPath[1];
            if (!isPass && !"api".equals(temp)) {
                if (null == request.getSession().getAttribute("pt_admin")) {
                    response.sendRedirect("/login/index.htm");
                    return false;
                }
            }
        } catch (Exception e) {
            LOG.error("", e);
            return false;
        }
		return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, java.lang.Exception ex) throws java.lang.Exception {
        if (ex != null) {
            response.sendRedirect("/error/other.htm");
        }
        return;
    }
}

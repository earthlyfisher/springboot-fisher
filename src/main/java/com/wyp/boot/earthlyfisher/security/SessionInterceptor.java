package com.wyp.boot.earthlyfisher.security;

import com.wyp.boot.earthlyfisher.common.CommonConstant;
import com.wyp.boot.earthlyfisher.pojo.User;
import com.wyp.boot.earthlyfisher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义拦截器.
 *
 * @author earthlyfisher
 */
public class SessionInterceptor implements HandlerInterceptor {

    private static final String DEFAULT_PAGE = "/login.html";

    private static final List<String> EXCLUDE_URLS = new ArrayList<>();

    static {
        EXCLUDE_URLS.add("/");
        EXCLUDE_URLS.add("/users/login");
    }

    @Autowired
    private UserService userService;

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        return true;
       /* String uri = request.getRequestURI();
        if (EXCLUDE_URLS.contains(uri)) {
            return true;
        }

        if (validSession(request)) {
            return true;
        }

        String redirectPath = request.getContextPath() + DEFAULT_PAGE;
        response.sendRedirect(redirectPath);
        return false;*/
    }

    /**
     * 验证session有效性.
     *
     * @param request
     * @return
     */
    private boolean validSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null != session) {
            User currentUser = (User) session.getAttribute(CommonConstant.SESSION_CURRENT_USER);
            if (currentUser != null) {
                return true;
            }
        }
        return false;
    }
}

package pers.fairy.miusa.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.service.UserService;
import pers.fairy.miusa.utils.RedisKeyUtil;
import pers.fairy.miusa.utils.WebUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 19:50
 */
@Slf4j
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    HostHolder hostHolder;
    @Autowired
    RedisAdapter redisAdapter;
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token"); // 如果是移动端，从请求参数中取token
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
        }
        User user = null;
        if (!StringUtils.isEmpty(token)) {
            String key = RedisKeyUtil.tokenKey(token);
            user = JSON.parseObject(redisAdapter.get(key), User.class);
        }
        if (user == null) {
            log.info("用户未登录，请求返回.");
            String header = request.getHeader("X-Requested-With");
            if (header != null && header.equals("XMLHttpRequest"))
                WebUtil.modifyResponse(response, Result.LOGIN_ERROR("未登录，请登录"));
            else
                response.sendRedirect("/login");
            return false;
        }
        hostHolder.setUser(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //modelAndView.addObject("user", hostHolder.getUser());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}

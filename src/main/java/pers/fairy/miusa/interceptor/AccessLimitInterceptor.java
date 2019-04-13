package pers.fairy.miusa.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.common.access.AccessLimit;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.service.RedisService;
import pers.fairy.miusa.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/11 20:39
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) { // 如果不是处理器方法直接返回
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取注解
        AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
        if (accessLimit == null)
            return true;
        // 获取各个字段的值
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        String url = request.getRequestURI();
        // 保存当前用户的访问次数（保存 seconds 秒）
        User user  = hostHolder.getUser();
        Long count = redisService.accessCount(user.getId(), url,seconds);
        if (count >= maxCount) { // 如果超过最大访问次数，表示访问频繁
            WebUtil.modifyResponse(response,Result.ACCESS_ERROR("访问频繁，请稍后再试"));
            return false;
        }
        return true;
    }
}

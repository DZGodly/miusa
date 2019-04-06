package pers.fairy.miusa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.exception.GlobalException;
import pers.fairy.miusa.service.RedisService;
import pers.fairy.miusa.service.UserService;
import pers.fairy.miusa.utils.UUIDUtil;
import pers.fairy.miusa.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 14:15
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @GetMapping
    public String getLoginForm() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result login(@Valid LoginVo loginForm, HttpServletResponse response) {
        User user = userService.login(Long.valueOf(loginForm.getMobile()), loginForm.getPassword());
        Cookie tokenCookie = setTokenForUser(user);
        response.addCookie(tokenCookie);
        return Result.SUCCESS("登录成功，等待跳转...");
    }

    private Cookie setTokenForUser(User user) {
        String token = UUIDUtil.token();
        redisService.setToken(user, token);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(2592000); // 2592000 = 60*60*24*30 eq 30 days
        cookie.setPath("/");
        return cookie;
    }
}

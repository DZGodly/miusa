package pers.fairy.miusa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.exception.GlobalException;
import pers.fairy.miusa.mapper.UserMapper;
import pers.fairy.miusa.utils.MD5Util;
import pers.fairy.miusa.utils.UUIDUtil;

import java.util.Date;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/4 21:20
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisAdapter redisAdapter;

    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public boolean registerUser(Long id, String nickname, String password) {
        if (userMapper.selectByPrimaryKey(id) != null) {
            return false;
        }
        User user = new User();
        user.setId(id);
        user.setNickname(nickname);
        String salt = UUIDUtil.salt();
        user.setSalt(salt);
        user.setPassword(MD5Util.encrypt(password + salt)); // 加盐
        user.setRegisterDate(new Date());
        return userMapper.insertSelective(user) > 0;
    }

    public User login(Long id, String password) {
        User user;
        if ((user = userMapper.selectByPrimaryKey(id)) == null) {
            throw new GlobalException("登录失败，请检查手机是否输入错误");
        }
        String salt = user.getSalt();
        if (!MD5Util.encrypt(password + salt).equals(user.getPassword())) {
            throw new GlobalException("登录失败，请检查密码是否输入错误");
        }
        return user;
    }
}

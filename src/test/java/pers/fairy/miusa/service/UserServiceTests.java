package pers.fairy.miusa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.mapper.UserMapper;
import pers.fairy.miusa.utils.MD5Util;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/4 21:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    public void registerUserTest(){
        String salt="1a2b3c4d";
        String str = ""+salt.charAt(0)+salt.charAt(2) + 111 +salt.charAt(5) + salt.charAt(4);
        //System.out.println(userService.registerUser(18959240395L,"admin",MD5Util.encrypt(str)));
        System.out.println(userService.login(18959240395L,MD5Util.encrypt(str)));
    }
}

package pers.fairy.miusa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.mapper.UserMapper;
import pers.fairy.miusa.utils.MD5Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/4 21:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    BlockingQueue<String> q = new LinkedBlockingQueue<>();
    @Autowired
    UserService userService;

    @Test
    public void registerUserTest() {
        String salt = "1a2b3c4d";
        String str = "" + salt.charAt(0) + salt.charAt(2) + 111 + salt.charAt(5) + salt.charAt(4);
        //System.out.println(userService.registerUser(18959240395L,"admin",MD5Util.encrypt(str)));
        System.out.println(userService.registerUser(13737373737L, "DSAD", MD5Util.encrypt(str)));
    }

    @Test
    public void loginTest() throws Exception {
        final int userCnt = 10000;
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            try {
                File fout = new File("C:\\Users\\Public\\Documents\\user.txt");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));
                String line;
                for (int i = 0; i < userCnt; i++) {
                    line = q.take();
                    bw.write(line);
                    bw.newLine();
                    System.out.println("line" + i);
                }
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("done");
        });
        CountDownLatch c = new CountDownLatch(userCnt);
        for (int i = 0; i < userCnt; i++) {
            long base = 58120395 + i;
            Long phone = Long.valueOf("189" + base);
            String pwd = 111 + "";
            executor.execute(() -> {
                try {
                    userService.registerUser(phone, "1", pwd);
                    q.put(phone + "");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    c.countDown();
                }
            });
        }
        c.await();
        ((ExecutorService) executor).shutdown();
    }
}

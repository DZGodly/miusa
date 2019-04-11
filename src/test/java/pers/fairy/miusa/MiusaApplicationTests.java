package pers.fairy.miusa;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.rabbitmq.MiusaMessage;
import pers.fairy.miusa.utils.ValidationUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@TestComponent
public class MiusaApplicationTests {

    BlockingQueue<String> q = new LinkedBlockingQueue<>();

    @Test
    public void contextLoads() {
       /* Executor executor = Executors.newCachedThreadPool();
        //ConcurrentHashMap<Long, String> map = new ConcurrentHashMap<>(50);
        try {
            File fout = new File("C:\\Users\\Public\\Documents\\user.txt");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout)));
            CountDownLatch c = new CountDownLatch(50);
            for (int i = 0; i < 50; i++) {
                long base = 59120395 + i;
                Long phone = Long.valueOf("189" + base);
                String pwd = 111 + "";
            *//*executor.execute(() -> {
                userService.registerUser(phone, "1", pwd);
                //map.put(phone, pwd);
                c.countDown();
            });*//*
                executor.execute(() -> {
                    try {
                        q.put(phone + "," + pwd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        c.countDown();
                    }
                });
            }
            c.await();
            ((ExecutorService) executor).shutdown();
            String line;
            try {
                for (int i = 0; i < q.size(); i++) {
                    line = q.take();
                    bw.write(line);
                    bw.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            bw.close();
            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*String[] strings = null;
        for (String s : strings){
            System.out.println(s);
        }*/
        //System.out.println(" "+null+"sd"+null);
      /*  Object s = "stadj";
        Map<String, Integer> map =new HashMap<>();
        map.put("sd",1);
        map.put("ds",2);
        System.out.println(JSON.toJSONString(123));*/
      /*String str =17912345678L+"";
        System.out.println( ValidationUtil.isMobile(str));*/
    }

}

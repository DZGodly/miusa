package pers.fairy.miusa;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.utils.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

@TestComponent
public class MiusaApplicationTests {

    @Test
    public void contextLoads() {
        String[] strings = null;
        for (String s : strings){
            System.out.println(s);
        }
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

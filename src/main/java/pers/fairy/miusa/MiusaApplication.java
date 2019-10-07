package pers.fairy.miusa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("pers.fairy.miusa.mapper")
public class MiusaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiusaApplication.class, args);
    }

}

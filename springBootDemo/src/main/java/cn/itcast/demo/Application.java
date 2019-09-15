package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // 此注解标识此类为引导类(启动类)
public class Application {

    public static void main(String[] args) {

        // 把自身的类传过来
        SpringApplication.run(Application.class, args);
    }
}

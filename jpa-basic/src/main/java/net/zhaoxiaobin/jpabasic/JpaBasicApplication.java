package net.zhaoxiaobin.jpabasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaBasicApplication {

    public static void main(String[] args) {
//        System.setProperty("hibernate.dialect.storage_engine", "innodb");
        SpringApplication.run(JpaBasicApplication.class, args);
    }

}

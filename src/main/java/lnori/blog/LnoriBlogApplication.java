package lnori.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Lnori
 */

@SpringBootApplication
@MapperScan("lnori.blog.mapper")
public class LnoriBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LnoriBlogApplication.class, args);
    }

}

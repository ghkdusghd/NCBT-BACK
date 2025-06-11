package kr.kh.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"kr.kh.backend.common", "kr.kh.backend.v1", "kr.kh.backend.v2"})
@MapperScan(basePackages = {"kr.kh.backend.v1.mapper", "kr.kh.backend.v2.mapper"})
@EntityScan(basePackages = "kr.kh.backend.v2.entity")
@EnableAsync
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}

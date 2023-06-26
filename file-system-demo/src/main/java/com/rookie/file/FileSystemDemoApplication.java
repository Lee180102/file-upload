package com.rookie.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.BatchUpdateException;

@MapperScan("com.rookie.file.mapper")
@SpringBootApplication
public class FileSystemDemoApplication {

    @Value("${file.base-path}")
    private String fileBasePath;

    public static void main(String[] args) {
        SpringApplication.run(FileSystemDemoApplication.class, args);
    }
    @Bean
    public Path path(){
        return Paths.get(fileBasePath);
    }

}

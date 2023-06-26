package com.rookie.file.config;

import com.rookie.file.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileUploadConfiguration implements CommandLineRunner {

    @Autowired
    Path path;

    @Override
    public void run(String... args) throws Exception {
        boolean exists = FileUtil.exists(path);
        if (!exists){
            FileUtil.init(path);
        }
    }
}

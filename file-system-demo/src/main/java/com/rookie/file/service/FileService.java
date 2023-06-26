package com.rookie.file.service;

import com.rookie.file.pojo.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
* @author lijie
* @description 针对表【file】的数据库操作Service
* @createDate 2023-06-16 14:54:13
*/
public interface FileService extends IService<File> {


    File save(MultipartFile multipartFile);

    Stream<Path> load();
}

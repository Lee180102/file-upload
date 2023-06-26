package com.rookie.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.file.pojo.File;
import com.rookie.file.service.FileService;
import com.rookie.file.mapper.FileMapper;
import com.rookie.file.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author lijie
 * @description 针对表【file】的数据库操作Service实现
 * @createDate 2023-06-16 14:54:13
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
        implements FileService {

    @Autowired
    Path path;

    @Autowired
    FileMapper fileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public File save(MultipartFile multipartFile) {
        Assert.isTrue(!multipartFile.isEmpty(), "文件上传失败。");
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String modelUri = contentType + java.io.File.separator + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + java.io.File.separator;
        Long size = multipartFile.getSize();
        Path resolve = this.path.resolve(modelUri);
        File file = new File(null, fileName, modelUri, contentType, size);
        this.save(file);
        if (!FileUtil.exists(resolve)) {
            FileUtil.init(resolve);
        }
        try {
            Files.copy(multipartFile.getInputStream(), this.path.resolve(modelUri + java.io.File.separator + file.getUuid()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
        return file;
    }

    @Override
    public Stream<Path> load() {
        try {
            return Files.walk(this.path)
                    .filter(path -> !path.equals(this.path) && !path.getFileName().toString().startsWith(".") && Files.isRegularFile(path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }

}





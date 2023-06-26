package com.rookie.file.controller;

import com.rookie.file.pojo.File;
import com.rookie.file.service.FileService;
import com.rookie.file.utils.FileUtil;
import com.rookie.file.vo.ElUploadFileVo;
import com.rookie.file.vo.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


@RestController()
@RequestMapping("/file")
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileService fileService;

    @Autowired
    private Path path;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] file) {
        List<ElUploadFileVo> elUploadFileVoList = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : file) {
                File fileData = fileService.save(multipartFile);
                String name = fileData.getFileName();
                String uuid = fileData.getUuid();
                String url = MvcUriComponentsBuilder
                        .fromMethodName(FileController.class,
                                "loadFile",
                                fileData.getUuid()
                        ).build().toString();
                elUploadFileVoList.add(new ElUploadFileVo(name, url, uuid));
            }
            return ResponseEntity.ok(elUploadFileVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new Message("Could not upload the file:" + Arrays.stream(file).map(MultipartFile::getOriginalFilename).collect(Collectors.joining())));
        }
    }


    @RequestMapping("/load/{uuid}")
    public ResponseEntity<Resource> loadFile(@PathVariable("uuid") String uuid) throws UnsupportedEncodingException {
        File file = fileService.getById(uuid);
        Path resolve = path.resolve(file.getPath()+file.getUuid());
        Resource resource = FileUtil.load(resolve);
        String mime = file.getContentType();
        String disp = "attachment; filename*=utf-8''" + filenameEncode(resource.getFilename());


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        disp)
                .header(HttpHeaders.CONTENT_TYPE, mime)
                .body(resource);
    }

    @RequestMapping("/delete")
    public ResponseEntity<Object> delete(@RequestParam("id") String id) throws UnsupportedEncodingException {
        fileService.removeById(id);
        return ResponseEntity.ok("删除成功");
    }


    @RequestMapping("/load")
    public ResponseEntity<Object> load() throws UnsupportedEncodingException {
        List<File> list = fileService.list();

        List<ElUploadFileVo> loadFile = list.stream().map(file -> {
            String name = file.getFileName();
            String uuid = file.getUuid();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class,
                            "loadFile",
                            file.getUuid()
                    ).build().toString();
            return new ElUploadFileVo(name, url, uuid);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(loadFile);
    }

    @RequestMapping("/loads")
    public ResponseEntity<Object> files(){
        List<ElUploadFileVo> loadFile = fileService.load()
                .map(path -> {
                    String fileName = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileController.class,
                                    "loadFile",
                                    path.getFileName().toString()
                            ).build().toString();
                    return new ElUploadFileVo(fileName, url, null);
                }).collect(Collectors.toList());
        return ResponseEntity.ok(loadFile);
    }



    public static String filenameEncode(String name) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(name, "UTF-8").replace("+", "%20");
    }
}

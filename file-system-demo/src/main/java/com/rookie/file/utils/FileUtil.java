package com.rookie.file.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class FileUtil {

    public static void init(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public static void save(MultipartFile multipartFile, Path path) {
        try {
            Files.copy(multipartFile.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error:" + e.getMessage());
        }
    }

    public static boolean exists(Path path) {
        URI uri = path.toUri();
        try {
            UrlResource resource = new UrlResource(uri);
            return resource.exists() || resource.isReadable();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Resource load(String filename, Path path) {
        Path file = path.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    public static Resource load(Path path) {

        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }


    public static Stream<Path> loadAll(Path path) {
        try {
            return Files.walk(path, 1)
                    .filter(p -> !p.equals(path))
                    .map(path::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }

    public static void clear(Path path) {
        FileSystemUtils.deleteRecursively(path.toFile());
    }

}

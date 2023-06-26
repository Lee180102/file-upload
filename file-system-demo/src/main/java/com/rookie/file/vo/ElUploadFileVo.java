package com.rookie.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElUploadFileVo {
    private String name;
    private String url;
    private String id;
}

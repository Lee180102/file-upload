package com.rookie.file.mapper;

import com.rookie.file.pojo.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lijie
* @description 针对表【file】的数据库操作Mapper
* @createDate 2023-06-16 14:54:13
* @Entity com.rookie.file.pojo.File
*/
@Mapper
public interface FileMapper extends BaseMapper<File> {

}





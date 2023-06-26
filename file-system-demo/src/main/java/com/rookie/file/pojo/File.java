package com.rookie.file.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName file
 */
@TableName(value = "file")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File implements Serializable {
    /**
     * 唯一标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 保存路径
     */
    private String path;

    /**
     *
     */
    private String contentType;

    /**
     *
     */
    private Long size;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        File other = (File) that;
        return (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
                && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
                && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
                && (this.getContentType() == null ? other.getContentType() == null : this.getContentType().equals(other.getContentType()))
                && (this.getSize() == null ? other.getSize() == null : this.getSize().equals(other.getSize())) ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getContentType() == null) ? 0 : getContentType().hashCode());
        result = prime * result + ((getSize() == null) ? 0 : getSize().hashCode()); ;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", fileName=").append(fileName);
        sb.append(", path=").append(path);
        sb.append(", contentType=").append(contentType);
        sb.append(", size=").append(size);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
package cn.codemodel.common.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@TableName("pe_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;
    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;

    private String pid;

    private Integer enVisible;

    public Permission(String name, Integer type, String code, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }
}

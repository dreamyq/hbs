package cn.codemodel.common.model.entity.company;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("co_department")
@Data
public class Department implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String companyId;
    private String pid;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门编码，同级部门不可重复
     */
    private String code;
    /**
     * 负责人ID
     */
    private String managerId;
    /**
     * 负责人名称
     */
    private String manager;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 创建时间
     */
    private Date createTime;
}

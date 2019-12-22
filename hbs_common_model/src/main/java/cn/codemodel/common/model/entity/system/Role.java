package cn.codemodel.common.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pe_role")
public class Role extends Model<Role> implements Serializable {
    private static final long serialVersionUID=1L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;

    @TableField(exist = false)
    private List<User> users;//角色与用户   多对多
    @TableField(exist = false)
    private List<Permission> permissions;//角色与模块  多对多
}

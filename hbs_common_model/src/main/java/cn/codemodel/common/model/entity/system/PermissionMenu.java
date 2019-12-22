package cn.codemodel.common.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("pe_permission_menu")
@Data
public class PermissionMenu implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    //展示图标
    private String menuIcon;

    //排序号
    private String menuOrder;
}

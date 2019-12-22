package cn.codemodel.recruitment.model.entity;

import cn.codemodel.common.model.entity.system.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("tb_question")
public class Question implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String questionId;
    private String title;
    private String answer;
    private String error;
    @TableField(exist = false)
    private User userId;
    @TableField(exist = false)
    private User mender;
}

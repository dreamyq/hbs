package cn.codemodel.recruitment.model.entity;

import cn.codemodel.common.model.entity.system.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Recruit implements Serializable {
    private String recruitId;
    private String recruitName;
    private int num;
    private String recruitDescription;
    private User userId;

}

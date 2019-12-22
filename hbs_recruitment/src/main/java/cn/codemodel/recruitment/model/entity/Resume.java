package cn.codemodel.recruitment.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Resume implements Serializable {
    private String resumeId;
    private String name;
    private String sex;
    private Date birthday;
}

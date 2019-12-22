package cn.codemodel.common.model.entity.company;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("co_company")
@Data
public class Company implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String name;
    private String managerId;//企业登录账号ID
    private String version;
    private Date renewalDate;//续费时间
    private Date expirationDate;//到期时间
    private String companyArea;
    private String companyAddress;
    private String businessLicenseId;//营业执照-图片ID
    private String legalRepresentative;//法人代表
    private String companyPhone;
    private String mailbox;
    private String companySize;
    private String industry;
    private String remarks;
    private String auditState;//审核状态
    private Integer state;
    private Double balance;
    private Date createTime;
}

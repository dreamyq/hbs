package cn.codemodel.common.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TableName("bs_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /*
     * 手机号码
     */
    private String mobile;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer enableState;
    /**
     * 创建时间
     */
    private Date createTime;

    private String companyId;

    private String companyName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 入职时间
     */
    private Date timeOfEntry;

    /**
     * 聘用形式
     */
    private Integer formOfEmployment;

    /**
     * 工号
     */
    private String workNumber;

    /**
     * 管理形式
     */
    private String formOfManagement;

    /**
     * 工作城市
     */
    private String workingCity;

    /**
     * 转正时间
     */
    private Date correctionTime;

    /**
     * 在职状态 1.在职  2.离职
     */
    private Integer inServiceStatus;
    private String level;
    private String departmentName;
    //    //用户与角色   多对多
//    private Set<Role> roles = new HashSet<Role>();
    @TableField(exist = false)
    private List<Role> roles;
    private String staffPhoto;

    public User(Object[] objs, String companyId, String companyName) {
        //默认手机号excel读取为字符串会存在科学记数法问题，转化处理
        this.mobile = new DecimalFormat("#").format(objs[2]);
        this.username = objs[1].toString();
        this.createTime = new Date();
        this.timeOfEntry = (Date) objs[5];
        this.formOfEmployment = ((Double) objs[4]).intValue();
        this.workNumber = new DecimalFormat("#").format(objs[3]).toString();
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public User(Object [] values) {
        //用户名	手机号	工号	聘用 形式	入职 时间	部门编码
        this.username = values[1].toString();
        this.mobile = values[2].toString();
        this.workNumber = new DecimalFormat("#").format(values[3]).toString();
        this.formOfEmployment =((Double) values[4]).intValue();
        this.timeOfEntry = (Date) values[5];
        this.departmentId = values[6].toString(); //部门编码 != 部门id
    }

}

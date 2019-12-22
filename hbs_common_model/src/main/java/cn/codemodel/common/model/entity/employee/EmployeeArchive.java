package cn.codemodel.common.model.entity.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@TableName( "em_archive")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeArchive implements Serializable {
    private static final long serialVersionUID = 5768915936056289957L;
    /**
     * ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 操作人
     */
    private String opUser;
    /**
     * 月份
     */
    private String month;
    /**
     * 企业ID
     */
    private String companyId;
    /**
     * 总人数
     */
    private Integer totals;
    /**
     * 在职人数
     */
    private Integer payrolls;
    /**
     * 离职人数
     */
    private Integer departures;
    /**
     * 数据
     */
    private String data;
    /**
     * 创建时间
     */
    private Date createTime;
}

package cn.codemodel.employee.dao;

import cn.codemodel.common.model.entity.employee.EmployeePositive;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 数据访问接口
 */
public interface PositiveDao extends BaseMapper<EmployeePositive> {
    EmployeePositive findByUserId(String uid);
}
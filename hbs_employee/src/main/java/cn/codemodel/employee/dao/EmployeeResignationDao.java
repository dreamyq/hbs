package cn.codemodel.employee.dao;

import cn.codemodel.common.model.entity.employee.EmployeeResignation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 数据访问接口
 */
public interface EmployeeResignationDao extends BaseMapper<EmployeeResignation> {
    EmployeeResignation findByUserId(String uid);
}
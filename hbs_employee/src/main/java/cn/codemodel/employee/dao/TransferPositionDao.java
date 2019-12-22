package cn.codemodel.employee.dao;


import cn.codemodel.common.model.entity.employee.EmployeeTransferPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 数据访问接口
 */
public interface TransferPositionDao extends BaseMapper<EmployeeTransferPosition> {
    EmployeeTransferPosition findByUserId(String uid);
}
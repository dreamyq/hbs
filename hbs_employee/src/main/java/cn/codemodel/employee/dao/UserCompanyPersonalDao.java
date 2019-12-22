package cn.codemodel.employee.dao;

import cn.codemodel.common.model.entity.employee.UserCompanyPersonal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface UserCompanyPersonalDao extends BaseMapper<UserCompanyPersonal> {
    UserCompanyPersonal findByUserId(String userId);
}
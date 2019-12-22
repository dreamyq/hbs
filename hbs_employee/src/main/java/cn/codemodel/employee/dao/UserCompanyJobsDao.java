package cn.codemodel.employee.dao;

import cn.codemodel.common.model.entity.employee.UserCompanyJobs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 数据访问接口
 */
public interface UserCompanyJobsDao extends BaseMapper<UserCompanyJobs> {
    UserCompanyJobs findByUserId(String userId);
}
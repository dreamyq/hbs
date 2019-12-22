package cn.codemodel.employee.service;

import cn.codemodel.common.model.entity.employee.UserCompanyJobs;
import cn.codemodel.employee.dao.UserCompanyJobsDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/19 9:52
 * Description:
 */
@Service
public class UserCompanyJobsService extends ServiceImpl<UserCompanyJobsDao, UserCompanyJobs> {
    @Autowired
    private UserCompanyJobsDao userCompanyJobsDao;

    @Override
    public boolean save(UserCompanyJobs entity) {
        return super.save(entity);
    }

    public UserCompanyJobs findById(String userId) {
        return userCompanyJobsDao.findByUserId(userId);
    }
}

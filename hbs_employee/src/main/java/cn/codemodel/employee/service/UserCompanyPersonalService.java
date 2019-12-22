package cn.codemodel.employee.service;

import cn.codemodel.common.model.entity.employee.UserCompanyPersonal;
import cn.codemodel.employee.dao.UserCompanyPersonalDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 */
@Service
public class UserCompanyPersonalService extends ServiceImpl<UserCompanyPersonalDao, UserCompanyPersonal> {
    @Autowired
    private UserCompanyPersonalDao userCompanyPersonalDao;


}

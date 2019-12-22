package cn.codemodel.employee.service;

import cn.codemodel.common.model.entity.employee.EmployeeResignation;
import cn.codemodel.employee.dao.EmployeeResignationDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/20 14:23
 * Description:
 */
@Service
public class ResignationService extends ServiceImpl<EmployeeResignationDao , EmployeeResignation> {
    @Autowired
    EmployeeResignationDao resignationDao;

    @Override
    public boolean save(EmployeeResignation entity) {
        entity.setCreateTime(new Date());
        return super.save(entity);
    }

    public EmployeeResignation findById(String userId) {
        return resignationDao.findByUserId(userId);
    }
}

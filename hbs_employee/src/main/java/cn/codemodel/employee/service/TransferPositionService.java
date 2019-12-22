package cn.codemodel.employee.service;

import cn.codemodel.common.model.entity.employee.EmployeeTransferPosition;
import cn.codemodel.employee.dao.TransferPositionDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/22 10:57
 * Description:
 */
@Service
public class TransferPositionService extends ServiceImpl<TransferPositionDao, EmployeeTransferPosition> {
    @Autowired
    private TransferPositionDao transferPositionDao;

    public EmployeeTransferPosition findById(String uid, Integer status) {
        EmployeeTransferPosition transferPosition = transferPositionDao.findByUserId(uid);
        if (status != null && transferPosition != null) {
            if (transferPosition.getEstatus() != status) {
                transferPosition = null;
            }
        }
        return transferPosition;
    }

    @Override
    public boolean save(EmployeeTransferPosition entity) {
        entity.setCreateTime(new Date());
        entity.setEstatus(1); //未执行
        return super.save(entity);
    }
}

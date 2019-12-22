package cn.codemodel.system.dao;

import cn.codemodel.common.model.entity.system.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Test
    public void getRolesByUserIdtest(){
        List<Role> roles = userDao.getRolesByUserId("1066370498633486336");
        for (Role role : roles) {
            System.out.println(role);
        }
    }
    @Test
    public void getByRoleId(){
        Role byId = roleDao.findById("1062944989845262336");
        System.out.println(byId);
    }

}

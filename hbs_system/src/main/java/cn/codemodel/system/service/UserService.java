package cn.codemodel.system.service;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.common.model.entity.system.Permission;
import cn.codemodel.common.model.entity.system.Role;
import cn.codemodel.common.model.entity.system.User;
import cn.codemodel.system.client.DepartmentFeignClient;
import cn.codemodel.system.dao.CompanyDao;
import cn.codemodel.system.dao.PermissionDao;
import cn.codemodel.system.dao.UserDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService extends ServiceImpl<UserDao, User> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private CompanyDao companyDao;


    public User findByUserAndPassword(String mobile, String password) {
        User user = userDao.findByName(mobile);
        for (int i = 0; i < user.getRoles().size(); i++) {
            List<Permission> permissionList = permissionDao.findPermissionByRoleId(user.getRoles().get(i).getId());
            user.getRoles().get(i).setPermissions(permissionList);
        }
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void sendSms(String mobile) { //1.生成6位短信验证码
        Random random = new Random();
        int max = 999999;//最大数
        int min = 100000;//最小数
        int code = random.nextInt(max);//随机生成
        if (code < min) {
            code = code + min;
        }
        System.out.println(mobile + "收到验证码是:" + code);
        //2.将验证码放入redis
        redisTemplate.opsForValue().set("smscode_" + mobile, code + "", 5, TimeUnit.MINUTES);//五分钟过期
        //3.将验证码和手机号发动到rabbitMQ中
        Map<String, String> map = new HashMap();
        map.put("mobile", mobile);
        map.put("code", code + "");
        rabbitTemplate.convertAndSend("sms", map);
    }

    public boolean judgeCode(String mobile, String code) {
        String syscode = (String) redisTemplate.opsForValue().get("smscode_" + mobile);
        if (code.equals(syscode)) {
            return true;
        }
        return false;
    }

    /**
     * 添加用户
     *
     * @param entity
     * @return
     */
    public void save(User entity, String code) {
        String syscode = (String) redisTemplate.opsForValue().get("smscode_" + entity.getMobile());
        if (syscode == null) {
            throw new RuntimeException("请点击获取短信验证码");
        }
        if (!syscode.equals(code)) {
            throw new RuntimeException("验证码输入不正确");
        }
        entity.setEnableState(1);
        entity.setCreateTime(new Date());
        String newPassword = encoder.encode(entity.getPassword());
        entity.setPassword(newPassword);
        userDao.insert(entity);
    }

    public Page<User> findSearch(User user, int page, int size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", user.getCompanyId());
        Page<User> page1 = new Page<>(page, size);
        IPage<User> userIPage = userDao.selectPage(page1, queryWrapper);
        return (Page<User>) userIPage;
    }

    public void changeDept(String deptId, String deptName, List<String> ids) {
        for (String id : ids) {
            User user = userDao.selectById(id);
            user.setDepartmentName(deptName);
            user.setDepartmentId(deptId);
            userDao.updateById(user);
        }
    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId, List<String> roleIds) {
        User user = userDao.selectById(userId);
        userDao.deleteByUserRole(userId);
        userDao.addRolesForUser(userId, roleIds);
    }

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        List<Role> rolesByUserId = userDao.getRolesByUserId((String) id);
        user.setRoles(rolesByUserId);
        for (int i = 0; i < user.getRoles().size(); i++) {
            List<Permission> permissionList = permissionDao.findPermissionByRoleId(user.getRoles().get(i).getId());
            user.getRoles().get(i).setPermissions(permissionList);
        }
        return user;
    }

    public void saveAll(List<User> list, String parseCompanyId, String parseCompanyName) {
    }

    public void reg(Company company, User user) {
        user.setEnableState(1);
        user.setCreateTime(new Date());
        user.setLevel("coAdmin");
        user.setCompanyName(company.getName());
        String newPassword = encoder.encode(user.getPassword());
        user.setPassword(newPassword);
        userDao.insert(user);
        company.setManagerId(user.getId());
        companyDao.insert(company);
        user.setCompanyId(company.getId());
        userDao.updateById(user);
    }

//    @Transactional
//    public void save(List<User> users) throws Exception {
//        for (User user : users) {
//            //配置密码
//            user.setPassword(new Md5Hash("123456", user.getMobile(), 3).toString()); //配置id
//            //其他基本属性
//            user.setInServiceStatus(1);
//            user.setEnableState(1);
//            user.setLevel("user");
//            //获取部门信息
//            Department dept =
//                    departmentFeignClient.findById(user.getDepartmentId(), user.getCompanyId());
//            if (dept != null) {
//                user.setDepartmentId(dept.getId());
//                user.setDepartmentName(dept.getName());
//            }
//            userDao.save(user);
//        }
}
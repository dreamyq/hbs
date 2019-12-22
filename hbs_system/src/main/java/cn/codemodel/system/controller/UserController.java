package cn.codemodel.system.controller;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.common.model.entity.response.ProfileResult;
import cn.codemodel.common.model.entity.system.Permission;
import cn.codemodel.common.model.entity.system.Role;
import cn.codemodel.common.model.entity.system.User;
import cn.codemodel.controller.BaseController;
import cn.codemodel.model.entity.PageResult;
import cn.codemodel.model.entity.Result;
import cn.codemodel.model.enums.ResultCode;
import cn.codemodel.system.service.PermissionService;
import cn.codemodel.system.service.UserService;
import cn.codemodel.util.JwtUtil;
import cn.codemodel.util.PermissionConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.xml.bind.JAXBIntrospector.getValue;

@RestController
@CrossOrigin
@RequestMapping("/system")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        System.out.println(user);
        user = userService.findByUserAndPassword(user.getMobile(), user.getPassword());

        System.out.println(user);
        if (user != null) {
            StringBuilder sb = new StringBuilder();
            //获取到所有的可访问API权限
            for (Role role : user.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    if (perm.getType() == PermissionConstants.PY_API) {
                        sb.append(perm.getCode()).append(",");
                    }
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("apis", sb.toString());
            //可访问的api权限字符串
            map.put("companyId", user.getCompanyId());
            map.put("companyName", user.getCompanyName());
            map.put("name", user.getUsername());
            System.out.println("UserCroller======" + user.getCompanyId());

            String token = jwtUtil.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS, token);
        }
        return new Result(ResultCode.UNAUTHENTICATED);
    }

    /**
     * 得到用户信息
     *
     * @param request
     * @return
     */
    @PostMapping("/profile")
    public Result info(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        String userId = claims.getId();
        User user = userService.getById(userId);

        ProfileResult result = null;

        if ("user".equals(user.getLevel())) {
            result = new ProfileResult(user);
        } else {
            Map map = new HashMap();
            if ("coAdmin".equals(user.getLevel())) {
                map.put("enVisible", "1");
            }
            List<Permission> list = permissionService.findAll(map);
            result = new ProfileResult(user, list);
        }
        System.out.println(result);
        return new Result(ResultCode.SUCCESS, result);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/user/{code}")
    public Result add(@RequestBody User user, @PathVariable("code") String code) throws Exception {
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user, code);
        return Result.SUCCESS();
    }

    @PostMapping("/step1/{mobile}/{code}")
    public Result step1(@PathVariable("mobile") String mobile, @PathVariable("code") String code) {
        System.out.println("mobile" + mobile);
        System.out.println("code" + code);
        boolean judgeCode = userService.judgeCode(mobile, code);
        if (judgeCode) {
            return Result.SUCCESS();
        }
        return Result.ERROR();
    }

    @PostMapping("/step2")
    public Result step2(@RequestBody Map<String, Object> map) {
        String mobile = (String) map.get("mobile");
        String code = (String) map.get("verificationCode");
        String name = (String) map.get("company");
        String password = (String) map.get("password");
        String username = (String) map.get("username");
        String email = (String) map.get("email");
        boolean judgeCode = userService.judgeCode(mobile, code);
        if (judgeCode) {
            Company company = new Company();
            company.setName(name);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setMobile(mobile);
            userService.reg(company, user);
            return Result.SUCCESS();
        }
        return new Result(ResultCode.FAIL);
    }

    /**
     * 发送验证码
     *
     * @param map
     * @return
     */
    @PostMapping("/sendAuthCode")
    public Result sendAuthCode(@RequestBody Map<String, Object> map) {
        String mobile = (String) map.get("mobile");
        userService.sendSms(mobile);
        return Result.SUCCESS();
    }


    //更新用户
    @PutMapping("/user/{id}")
    public Result update(@PathVariable(name = "id") String id, @RequestBody User user)
            throws Exception {
        userService.updateById(user);
        return Result.SUCCESS();
    }

    //删除用户
    @DeleteMapping(value = "/user/{id}", name = "API-USER-DELETE")
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        userService.removeById(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/user/{id}")
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        User user = userService.getById(id);

        return new Result(ResultCode.SUCCESS, user);
    }

    @PostMapping("/user")
    public Result add(@RequestBody User user) {
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分页查询用户
     */
    @PostMapping("/user/search/{page}/{size}")
    public Result findByPage(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody User user) throws Exception {
        user.setCompanyId(companyId);
        Page<User> users = userService.findSearch(user, page, size);
        PageResult<User> pr = new PageResult(users.getTotal(), users.getRecords());
        return new Result(ResultCode.SUCCESS, pr);
    }

    /**
     * 分配角色
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String, Object> map) {
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId, roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    //批量导入
    @PostMapping("/user/import")
    public Result importExcel(@RequestParam(name = "file") MultipartFile attachment) throws Exception {
        //1.解析Excel
        //1.1.根据Excel文件创建工作簿
        Workbook wb = new XSSFWorkbook(attachment.getInputStream());
        //1.2.获取Sheet
        Sheet sheet = wb.getSheetAt(0);//参数：索引
        //1.3.获取Sheet中的每一行，和每一个单元格
        //2.获取用户数据列表
        List<User> list = new ArrayList<>();
        System.out.println(sheet.getLastRowNum());
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);//根据索引获取每一个行
            Object[] values = new Object[row.getLastCellNum()];
            for (int cellNum = 1; cellNum < row.getLastCellNum(); cellNum++) {
                Cell cell = row.getCell(cellNum);
                Object value = getCellValue(cell);
                values[cellNum] = value;
            }
            User user = new User(values);
            list.add(user);
        }
        //3.批量保存用户
        userService.saveAll(list, companyId, companyName);

        return new Result(ResultCode.SUCCESS);
    }

    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellType();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                } else {
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }

}

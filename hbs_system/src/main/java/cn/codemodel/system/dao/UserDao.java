package cn.codemodel.system.dao;

import cn.codemodel.common.model.entity.company.Company;
import cn.codemodel.common.model.entity.system.Role;
import cn.codemodel.common.model.entity.system.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDao  extends BaseMapper<User> {
    public User findByName(@Param("username") String username);

    /**
     * 根据用户id查询所有的角色
     * @param userId
     * @return
     */
    public List<Role> getRolesByUserId(String userId);

    public int addRolesForUser(@Param("userId") String userId,@Param("roles") List<String> roles);

    public int deleteByUserRole(@Param("userId") String userId);

    public int insertCom(Company company);

    @Select("select * from bs_user where mobile=#{username}")
    @Results({
            @Result(column="hero_id",property="heroId"),
            @Result(column="hero_id",property="leagues",
                    many=@Many(
                            select="cn.stylefeng.guns.modular.dotaunderlords.mapper.DotaLeagueMapper.getListByHeroId"
                    )
            )
    })
    public User getUserByNae(@Param("username") String username);
}

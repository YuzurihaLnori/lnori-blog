package lnori.blog.mapper;

import lnori.blog.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Lnori
 */
@Repository
public interface UserMapper extends Mapper<User> {

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Select("select * from tb_user where user_id = #{userId}")
    User findUserByTypeId(Long userId);

}

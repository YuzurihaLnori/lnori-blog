package lnori.blog.service.impl;

import lnori.blog.entity.User;
import lnori.blog.mapper.UserMapper;
import lnori.blog.service.UserService;
import lnori.blog.util.Md5Util;
import lnori.blog.util.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User checkUser = new User();
        checkUser.setUsername(username);

        User user = userMapper.selectOne(checkUser);
        if(StringUtils.isNull(user)) {
            throw new UnknownAccountException("账户不存在");
        }


        if(!user.getPassword().equals(Md5Util.hash(password))) {
            throw new IncorrectCredentialsException("密码错误");
        }

        return user;
    }

}

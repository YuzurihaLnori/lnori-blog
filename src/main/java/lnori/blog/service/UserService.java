package lnori.blog.service;

import lnori.blog.entity.User;

/**
 * @author Lnori
 */
public interface UserService {

    /**
     * 验证登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User checkUser(String username, String password);

}

package lnori.blog.shiro;

import lnori.blog.entity.User;
import lnori.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.checkUser(token.getUsername(), String.valueOf(token.getPassword()));
        SecurityUtils.getSubject().getSession().setAttribute("user",user);
        log.info("---------------->进入认证步骤");
        return new SimpleAuthenticationInfo(user, token.getCredentials(), this.getName());
    }

}

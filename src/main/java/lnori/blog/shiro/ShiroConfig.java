package lnori.blog.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Lnori
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(AccountRealm accountRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/admin/login", "anon");
        hashMap.put("/admin/logout", "anon");
        hashMap.put("/static/**", "anon");
        hashMap.put("/admin/**", "authc");

        //配置登录的url和登录成功的url
        shiroFilterFactoryBean.setLoginUrl("/admin");
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        //配置未授权跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);

        return shiroFilterFactoryBean;
    }

}

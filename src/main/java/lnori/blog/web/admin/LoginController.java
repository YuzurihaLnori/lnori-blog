package lnori.blog.web.admin;

import lnori.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("admin")
public class LoginController {
    @Resource
    private UserService userService;

    @GetMapping
    public ModelAndView page() {
        return new ModelAndView("admin/login");
    }

    @PostMapping("login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        try {
            //登录成功 返回首页
            SecurityUtils.getSubject().login(token);
            modelAndView.setViewName("admin/index");
            return modelAndView;
        } catch (UnknownAccountException e) {//抛出异常用户名不存在
            modelAndView.addObject("message", "用户名不存在");
            modelAndView.setViewName("admin/login");
            return modelAndView;
        } catch (IncorrectCredentialsException e) {//抛出异常密码不存在
            modelAndView.addObject("message", "密码错误");
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
    }

    @GetMapping("logout")
    public ModelAndView logout() {
        SecurityUtils.getSubject().logout();
        return new ModelAndView("redirect:/admin");
    }

}

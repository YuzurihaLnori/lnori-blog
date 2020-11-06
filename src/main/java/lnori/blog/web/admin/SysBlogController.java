package lnori.blog.web.admin;

import lnori.blog.entity.blog.Blog;
import lnori.blog.entity.SearchBlog;
import lnori.blog.entity.User;
import lnori.blog.service.BlogService;
import lnori.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("admin/blog")
public class SysBlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;

    @GetMapping
    public ModelAndView blog(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size) {
        ModelAndView modelAndView = new ModelAndView("admin/blog");
        modelAndView.addObject("page", blogService.findAllBlog(page, size));
        modelAndView.addObject("typeList", typeService.findAll());
        return modelAndView;
    }

    @PostMapping("search")
    public ModelAndView search(@RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "size", defaultValue = "5") Integer size, SearchBlog searchBlog) {
        ModelAndView modelAndView = new ModelAndView("admin/blog :: blogList");
        System.out.println(searchBlog);
        modelAndView.addObject("page", blogService.findBlogPage(page, size, searchBlog));
        return modelAndView;
    }

    @GetMapping("save")
    public ModelAndView save() {
        ModelAndView modelAndView = new ModelAndView("admin/blog-input");
        modelAndView.addObject("typeList", typeService.findAll());
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveBlog(Blog blog, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getUserId());
        blogService.saveBlog(blog);
        attributes.addFlashAttribute("message", "新增博客成功");
        return new ModelAndView("redirect:/admin/blog");
    }

    @GetMapping("edit/{blogId}")
    public ModelAndView edit(@PathVariable Long blogId) {
        ModelAndView modelAndView = new ModelAndView("admin/blog-input");
        modelAndView.addObject("typeList", typeService.findAll());
        modelAndView.addObject("blog", blogService.findBlogById(blogId));
        return modelAndView;
    }

    @PostMapping("edit")
    public ModelAndView editBlog(@Valid Blog blog, RedirectAttributes attributes) {
        blogService.editBlog(blog);
        attributes.addFlashAttribute("message", "更新博客成功");
        return new ModelAndView("redirect:/admin/blog");
    }

    @GetMapping("delete/{blogId}")
    public ModelAndView deleteBlog(@PathVariable Long blogId, RedirectAttributes attributes) {
        blogService.deleteBlog(blogId);
        attributes.addFlashAttribute("message", "删除博客成功");
        return new ModelAndView("redirect:/admin/blog");
    }

}

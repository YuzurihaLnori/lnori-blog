package lnori.blog.web;

import lnori.blog.service.BlogService;
import lnori.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lnori
 */
@Controller
public class IndexController {
    @Resource
    private BlogService blogService;
    @Resource
    private CommentService commentService;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                              @RequestParam(name = "size", defaultValue = "5") Integer size) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("page", blogService.findBlogPage(page, size));
        modelAndView.addObject("blogRecommendList", blogService.findBlogRecommend());
        return modelAndView;
    }

    @GetMapping("blog/{blogId}")
    public ModelAndView blog(@PathVariable Long blogId) {
        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("blog", blogService.findBlogDetailedById(blogId));
        modelAndView.addObject("commentList", commentService.findCommentByBlogId(blogId));
        return modelAndView;
    }

    @GetMapping("footer/blogInformation")
    public ModelAndView blogInformation() {
        ModelAndView modelAndView = new ModelAndView("_fragments :: information");
        List<Integer> blogInformation = blogService.findBlogInformation();
        modelAndView.addObject("blogTotal", blogInformation.get(0));
        modelAndView.addObject("blogViewsTotal", blogInformation.get(1));
        modelAndView.addObject("blogCommentTotal", blogInformation.get(2));
        modelAndView.addObject("blogMessageTotal", blogInformation.get(3));
        return modelAndView;
    }

}

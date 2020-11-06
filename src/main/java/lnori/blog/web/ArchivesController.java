package lnori.blog.web;

import lnori.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("archives")
public class ArchivesController {
    @Resource
    private BlogService blogService;

    @GetMapping
    public ModelAndView archives() {
        ModelAndView modelAndView = new ModelAndView("archives");
        modelAndView.addObject("blogArchivesList", blogService.findArchivesBlog());
        return modelAndView;
    }

}

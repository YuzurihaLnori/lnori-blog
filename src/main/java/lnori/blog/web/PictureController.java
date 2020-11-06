package lnori.blog.web;

import lnori.blog.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("picture")
public class PictureController {
    @Resource
    private PictureService pictureService;

    @GetMapping
    public ModelAndView picture() {
        ModelAndView modelAndView = new ModelAndView("picture");
        modelAndView.addObject("pictureList", pictureService.findAllPicture());
        return modelAndView;
    }

}

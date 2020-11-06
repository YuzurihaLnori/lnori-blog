package lnori.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("about")
public class AboutController {

    @GetMapping
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}

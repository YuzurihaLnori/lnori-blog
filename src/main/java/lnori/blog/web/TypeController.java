package lnori.blog.web;

import com.github.pagehelper.PageInfo;
import lnori.blog.entity.Type;
import lnori.blog.entity.blog.vo.BlogVO;
import lnori.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("type")
public class TypeController {
    @Resource
    private TypeService typeService;

    @GetMapping("{typeId}")
    public ModelAndView type(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size, @PathVariable Long typeId) {
        ModelAndView modelAndView = new ModelAndView("type");
        List<Type> typeList = typeService.findAllByBlog();
        if (typeId == -1){
            typeId = typeList.get(0).getTypeId();
        }
        PageInfo<BlogVO> pageInfo = typeService.findBlogPageByTypeId(page, size, typeId);
        modelAndView.addObject("typeId", typeId);
        modelAndView.addObject("typeList", typeList);
        modelAndView.addObject("page", pageInfo);
        return modelAndView;
    }

}

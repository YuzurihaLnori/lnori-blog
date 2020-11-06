package lnori.blog.web.admin;

import lnori.blog.entity.Type;
import lnori.blog.service.TypeService;
import lnori.blog.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("admin/type")
public class SysTypeController {
    @Resource
    private TypeService typeService;

    @GetMapping
    public ModelAndView list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size) {
        ModelAndView modelAndView = new ModelAndView("admin/type");
        modelAndView.addObject("page", typeService.findTypeByPage(page, size));
        return modelAndView;
    }

    @GetMapping("save")
    public ModelAndView save() {
        ModelAndView modelAndView = new ModelAndView("admin/type-input");
        modelAndView.addObject("type", new Type());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveType(Type type, BindingResult result, RedirectAttributes attributes) {
        Type typeByName = typeService.findTypeByName(type.getTypeName());
        if (StringUtils.isNotNull(typeByName)) {
            result.rejectValue("typeName", "nameError", "您新增的分类已存在");
            return new ModelAndView("admin/type-input");
        }
        typeService.saveType(type);
        attributes.addFlashAttribute("message", "新增分类成功");
        return new ModelAndView("redirect:/admin/type");
    }

    @GetMapping("edit/{typeId}")
    public ModelAndView edit(@PathVariable Long typeId) {
        ModelAndView modelAndView = new ModelAndView("admin/type-input");
        modelAndView.addObject("type", typeService.findTypeById(typeId));
        return modelAndView;
    }

    @PostMapping("edit")
    public ModelAndView editType(Type type, BindingResult result, RedirectAttributes attributes) {
        Type typeByName = typeService.findTypeByName(type.getTypeName());
        if (StringUtils.isNotNull(typeByName)) {
            result.rejectValue("typeName", "nameError", "您修改的分类名称已存在");
            return new ModelAndView("admin/type-input");
        }
        typeService.editType(type);
        attributes.addFlashAttribute("message", "更新分类成功");
        return new ModelAndView("redirect:/admin/type");
    }

    @GetMapping("delete/{typeId}")
    public ModelAndView deleteType(@PathVariable Long typeId, RedirectAttributes attributes) {
        typeService.deleteType(typeId);
        attributes.addFlashAttribute("message", "删除分类成功");
        return new ModelAndView("redirect:/admin/type");
    }

}

package lnori.blog.web.admin;

import lnori.blog.entity.Picture;
import lnori.blog.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("admin/picture")
public class SysPictureController {
    @Resource
    private PictureService pictureService;

    @GetMapping
    public ModelAndView list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "5") Integer size) {
        ModelAndView modelAndView = new ModelAndView("admin/picture");
        modelAndView.addObject("page", pictureService.findPictureByPage(page, size));
        return modelAndView;
    }

    @GetMapping("save")
    public ModelAndView save() {
        ModelAndView modelAndView = new ModelAndView("admin/picture-input");
        modelAndView.addObject("picture", new Picture());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView savePicture(Picture picture, RedirectAttributes attributes) {
        pictureService.savePicture(picture);
        attributes.addFlashAttribute("message", "新增照片成功");
        return new ModelAndView("redirect:/admin/picture");
    }

    @GetMapping("edit/{pictureId}")
    public ModelAndView edit(@PathVariable Long pictureId) {
        ModelAndView modelAndView = new ModelAndView("admin/picture-input");
        modelAndView.addObject("picture", pictureService.findPictureByPictureId(pictureId));
        return modelAndView;
    }

    @PostMapping("edit")
    public ModelAndView editPicture(Picture picture, RedirectAttributes attributes) {
        pictureService.editPicture(picture);
        attributes.addFlashAttribute("message", "更新照片成功");
        return new ModelAndView("redirect:/admin/picture");
    }

    @GetMapping("delete/{pictureId}")
    public ModelAndView deletePicture(@PathVariable Long pictureId, RedirectAttributes attributes) {
        pictureService.deletePicture(pictureId);
        attributes.addFlashAttribute("message", "删除照片成功");
        return new ModelAndView("redirect:/admin/picture");
    }

}

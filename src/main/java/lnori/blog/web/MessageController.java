package lnori.blog.web;

import lnori.blog.entity.Message;
import lnori.blog.entity.User;
import lnori.blog.service.MessageService;
import lnori.blog.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Lnori
 */
@Controller
@RequestMapping("message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @GetMapping
    public ModelAndView allMessage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("messageList", messageService.findAllMessage());
        return modelAndView;
    }

    @GetMapping("message")
    public ModelAndView message(){
        ModelAndView modelAndView = new ModelAndView("message :: messageList");
        modelAndView.addObject("messageList", messageService.findAllMessage());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveMessage(Message message, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("redirect:/message/message");
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotNull(user)){
            message.setAvatar(user.getAvatar());
            message.setIsAdminMessage(true);
        }else{
            message.setIsAdminMessage(false);
        }
        messageService.saveMessage(message);
        return modelAndView;
    }

    @GetMapping("delete/{messageId}")
    public ModelAndView deleteMessage(@PathVariable Long messageId){
        messageService.deleteMessage(messageId);
        return new ModelAndView("redirect:/message/message");
    }

}

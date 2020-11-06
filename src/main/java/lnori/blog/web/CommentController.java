package lnori.blog.web;

import lnori.blog.entity.Comment;
import lnori.blog.entity.User;
import lnori.blog.service.CommentService;
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
@RequestMapping("comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("{blogId}")
    public ModelAndView comment(@PathVariable Long blogId) {
        ModelAndView modelAndView = new ModelAndView("blog :: commentList");
        modelAndView.addObject("commentList", commentService.findCommentByBlogId(blogId));
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveComment(Comment comment, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("redirect:/comment/" + comment.getBlogId());
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNotNull(user)) {
            comment.setAvatar(user.getAvatar());
            comment.setIsAdminComment(true);
        } else {
            comment.setIsAdminComment(false);
        }
        commentService.saveComment(comment);
        return modelAndView;
    }

    @GetMapping("delete/{commentId}")
    public ModelAndView deleteComment(@PathVariable Long commentId) {
        Long blogId = commentService.deleteComment(commentId);
        return new ModelAndView("redirect:/comment/" + blogId);
    }

}
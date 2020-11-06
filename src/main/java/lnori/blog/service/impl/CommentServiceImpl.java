package lnori.blog.service.impl;

import lnori.blog.entity.Comment;
import lnori.blog.entity.blog.Blog;
import lnori.blog.enums.BlogExceptionEnum;
import lnori.blog.exception.BlogException;
import lnori.blog.mapper.BlogMapper;
import lnori.blog.mapper.CommentMapper;
import lnori.blog.service.CommentService;
import lnori.blog.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lnori
 */
@Service("commentService")
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private BlogMapper blogMapper;
    private List<Comment> tempReplyList = new ArrayList<>();

    @Override
    public List<Comment> findCommentByBlogId(Long blogId) {
        return eachComment(commentMapper.findCommentByBlogId(blogId));
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param commentList 需要循环的评论集合
     * @return 循环好的评论集合
     */
    private List<Comment> eachComment(List<Comment> commentList) {
        List<Comment> commentListView = new ArrayList<>();
        for (Comment comment : commentList) {
            if (StringUtils.isNotEmpty(comment.getReplyCommentList())) {
                List<Comment> replyCommentList = comment.getReplyCommentList();
                for (Comment reply : replyCommentList) {
                    tempReplyList.add(reply);
                    recursively(reply);
                }
                comment.setReplyCommentList(tempReplyList);
                tempReplyList = new ArrayList<>();
            }
            commentListView.add(comment);
        }
        return commentListView;
    }

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     */
    public void recursively(Comment comment) {
        if (StringUtils.isNotEmpty(comment.getReplyCommentList())) {
            List<Comment> replyCommentList = comment.getReplyCommentList();
            for (Comment reply : replyCommentList) {
                tempReplyList.add(reply);
                if (StringUtils.isNotEmpty(reply.getReplyCommentList())) {
                    recursively(reply);
                }
            }
        }
    }

    @Override
    public void saveComment(Comment comment) {
        int count = commentMapper.insertSelective(comment);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.COMMENT_SAVE_ERROR);
        }
        Comment parent = new Comment();
        if (comment.getParentId() > 0) {
            parent.setCommentId(comment.getParentId());
            parent.setIsParent(true);
            commentMapper.updateByPrimaryKeySelective(parent);
        }
        updateBlogCommentTotal(comment.getBlogId());
    }

    @Override
    public Long deleteComment(Long commentId) {
        Comment comment = commentMapper.findCommentByCommentId(commentId);
        List<Comment> commentList = eachComment(Collections.singletonList(comment));
        List<Long> commentIdList = commentList.get(0).getReplyCommentList().stream().map(Comment::getCommentId).collect(Collectors.toList());
        commentIdList.add(commentId);
        commentMapper.deleteByIdList(commentIdList);
        updateBlogCommentTotal(comment.getBlogId());
        return comment.getBlogId();
    }

    private void updateBlogCommentTotal(Long blogId) {
        Integer commentCount = commentMapper.findCommentTotalById(blogId);
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setCommentCount(commentCount);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

}

package lnori.blog.service;

import lnori.blog.entity.Comment;

import java.util.List;

/**
 * @author Lnori
 */
public interface CommentService {

    /**
     * 根据博客ID查询评论
     * @param blogId 博客ID
     * @return 评论集合
     */
    List<Comment> findCommentByBlogId(Long blogId);

    /**
     * 保存评论
     * @param comment 评论
     */
    void saveComment(Comment comment);

    /**
     * 删除评论信息
     * @param commentId 评论ID
     * @return 博客ID
     */
    Long deleteComment(Long commentId);

}

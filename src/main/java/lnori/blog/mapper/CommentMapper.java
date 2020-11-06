package lnori.blog.mapper;

import lnori.blog.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Lnori
 */
@Repository
public interface CommentMapper extends Mapper<Comment>, IdListMapper<Comment, Long> {

    /**
     * 根据博客ID查询评论
     *
     * @param blogId 博客ID
     * @return 评论集合
     */
    @Select("select * from tb_comment where blog_id = #{blogId} and parent_id = -1 order by create_time desc")
    @Results(id = "commentMap", value = {
            @Result(id = true, property = "commentId", column = "comment_id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "blogId", column = "blog_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "content", column = "content"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "isParent", column = "is_parent"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "parentComment", column = "parent_id", javaType = Comment.class, one = @One(select = "lnori.blog.mapper.CommentMapper.findCommentByParentId")),
            @Result(property = "replyCommentList", column = "comment_id", javaType = java.util.List.class, many = @Many(select = "lnori.blog.mapper.CommentMapper.findCommentById"))
    })
    List<Comment> findCommentByBlogId(Long blogId);

    /**
     * 根据父ID查询父评论
     *
     * @param parentId 父ID
     * @return 父评论
     */
    @Select("select * from tb_comment where comment_id = #{parentId}")
    Comment findCommentByParentId(Long parentId);

    /**
     * 根据评论ID查询子评论
     *
     * @param commentId 评论ID
     * @return 评论集合
     */
    @Select("select * from tb_comment where parent_id = #{commentId}")
    @ResultMap(value = "commentMap")
    List<Comment> findCommentById(Long commentId);

    /**
     * 根据评论ID查询评论
     *
     * @param commentId 评论ID
     * @return 评论
     */
    @Select("select * from tb_comment where comment_id = #{commentId}")
    @ResultMap(value = "commentMap")
    Comment findCommentByCommentId(Long commentId);

    /**
     * 根据博客ID查询评论总量
     *
     * @param blogId 博客ID
     * @return 博客评论总量
     */
    @Select("select count(blog_id) from tb_comment b where b.blog_id = #{blogId}")
    Integer findCommentTotalById(Long blogId);

}

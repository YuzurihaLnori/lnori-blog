package lnori.blog.mapper;

import lnori.blog.entity.Type;
import lnori.blog.entity.User;
import lnori.blog.entity.blog.Blog;
import lnori.blog.entity.blog.vo.BlogArchivesVO;
import lnori.blog.entity.blog.vo.BlogDetailedVO;
import lnori.blog.entity.blog.vo.BlogRecommendVO;
import lnori.blog.entity.blog.vo.BlogVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Lnori
 */
@Repository
public interface BlogMapper extends Mapper<Blog>, IdListMapper<Blog, Long> {

    /**
     * 查询时间轴绚烂博客
     *
     * @return 博客集合
     */
    @Select("select b.blog_id,b.create_time,b.title from tb_blog b order by create_time desc")
    List<BlogArchivesVO> findArchivesBlog();

    /**
     * 查询最新推荐博客
     *
     * @return 最新推荐博客
     */
    @Select("select b.blog_id,b.first_picture,b.title from tb_blog b where recommend = 1 order by create_time desc limit 0,3")
    List<BlogRecommendVO> findBlogRecommend();

    /**
     * 查询首页展示博客
     *
     * @return 首页展示博客
     */
    @Select("select b.blog_id,b.title,b.description,b.views,b.comment_count,b.first_picture,b.create_time,u.nick_name,u.avatar,t.type_id,t.type_name from tb_blog b left join tb_user u on b.user_id = u.user_id left join tb_type t on b.type_id = t.type_id order by create_time desc")
    @Results(id = "blogMap", value = {
            @Result(id = true, property = "blogId", column = "blog_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "views", column = "views"),
            @Result(property = "commentCount", column = "comment_count"),
            @Result(property = "firstPicture", column = "first_picture"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "userNickName", column = "nick_name"),
            @Result(property = "userAvatar", column = "avatar"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "typeName", column = "type_name")
    })
    List<BlogVO> findBlogPage();

    /**
     * 根据博客ID查询博客
     *
     * @param blogId 博客ID
     * @return 博客
     */
    @Select("select b.blog_id,b.title,b.description,b.views,b.comment_count,b.first_picture,b.create_time,u.nick_name,u.avatar,t.type_id,t.type_name from tb_blog b left join tb_user u on b.user_id = u.user_id left join tb_type t on b.type_id = t.type_id where b.blog_id = #{blogId}")
    @ResultMap(value = "blogMap")
    BlogVO findBlogById(Long blogId);

    /**
     * 根据分类ID查询对应ID博客
     *
     * @param typeId 分类ID
     * @return 博客集合
     */
    @Select("select b.blog_id,b.title,b.description,b.views,b.comment_count,b.first_picture,b.create_time,u.nick_name,u.avatar from tb_blog b left join tb_user u on b.user_id = u.user_id where b.type_id = #{typeId} order by create_time desc")
    @ResultMap(value = "blogMap")
    List<BlogVO> findBlogPageByTypeId(Long typeId);

    /**
     * 根据博客ID查询博客
     *
     * @param blogId 博客ID
     * @return 博客
     */
    @Select("select * from tb_blog where blog_id = #{blogId}")
    @Results(id = "blogDetailedMap", value = {
            @Result(id = true, property = "blogId", column = "blog_id"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "firstPicture", column = "first_picture"),
            @Result(property = "flag", column = "flag"),
            @Result(property = "views", column = "views"),
            @Result(property = "commentCount", column = "comment_count"),
            @Result(property = "description", column = "description"),
            @Result(property = "appreciation", column = "appreciation"),
            @Result(property = "shareStatement", column = "shareStatement"),
            @Result(property = "comment", column = "comment"),
            @Result(property = "recommend", column = "recommend"),
            @Result(property = "published", column = "published"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "type", column = "type_id", javaType = Type.class, one = @One(select = "lnori.blog.mapper.TypeMapper.findTypeByTypeId")),
            @Result(property = "user", column = "user_id", javaType = User.class, one = @One(select = "lnori.blog.mapper.UserMapper.findUserByTypeId")),
    })
    BlogDetailedVO findBlogDetailedById(Long blogId);

    /**
     * 后端查询显示博客
     *
     * @return 博客集合
     */
    @Select("select * from tb_blog")
    @ResultMap(value = "blogDetailedMap")
    List<BlogDetailedVO> findAllBlog();

    /**
     * 根据博客ID修改博客访问数
     *
     * @param blogId 博客ID
     * @param views  访问数
     */
    @Update("update tb_blog set views = #{views} where blog_id = #{blogId}")
    void updateViewById(@Param("blogId") Long blogId, @Param("views") Integer views);

    /**
     * 查询博客总数
     *
     * @return 博客总数
     */
    @Select("select count(blog_id) from tb_blog")
    Integer findBlogCount();

    /**
     * 查询博客访问总数
     *
     * @return 博客访问总数
     */
    @Select("select sum(views) from tb_blog")
    Integer findBlogViewsCount();

    /**
     * 查询博客评论总数
     *
     * @return 博客评论总数
     */
    @Select("select count(comment_id) from tb_comment")
    Integer findBlogCommentCount();

    /**
     * 查询博客留言总数
     *
     * @return 博客留言总数
     */
    @Select("select count(message_id) from tb_message")
    Integer findBlogMessageCount();

}

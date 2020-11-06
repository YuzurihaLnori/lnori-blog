package lnori.blog.mapper;

import lnori.blog.entity.Type;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Lnori
 */
@Repository
public interface TypeMapper extends Mapper<Type> {

    /**
     * 查询有博客的分类
     *
     * @return 有博客的分类
     */
    @Select("select * from tb_type t where exists(select 1 from tb_blog b where b.type_id = t.type_id limit 1)")
    @Results(id = "typeMap", value = {
            @Result(id = true, property = "typeId", column = "type_id"),
            @Result(property = "typeName", column = "type_name"),
            @Result(property = "blogCount", column = "type_id", javaType = Integer.class, one = @One(select = "lnori.blog.mapper.TypeMapper.findBlogCountByTypeId")),
    })
    List<Type> selectHaveBlog();

    /**
     * 根据分类ID查询对应ID的博客总数
     *
     * @param typeId 分类ID
     * @return 博客总数
     */
    @Select("select count(blog_id) from tb_blog where type_id = #{typeId}")
    Integer findBlogCountByTypeId(Long typeId);

    /**
     * 根据分类ID查询分类
     *
     * @param typeId 分类ID
     * @return 分类
     */
    @Select("select * from tb_type where type_id = #{typeId}")
    Type findTypeByTypeId(Long typeId);

}

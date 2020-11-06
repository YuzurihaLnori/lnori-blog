package lnori.blog.service;

import com.github.pagehelper.PageInfo;
import lnori.blog.entity.Type;
import lnori.blog.entity.blog.vo.BlogVO;

import java.util.List;

/**
 * @author Lnori
 */
public interface TypeService {

    /**
     * 查询所有博客
     *
     * @return 分类集合
     */
    List<Type> findAll();

    /**
     * 查询分类显示有博客的分类
     *
     * @return 分类集合
     */
    List<Type> findAllByBlog();

    /**
     * 根据分类ID查询分类显示博客
     *
     * @param page 分页起始数
     * @param size 分页总数
     * @param typeId 分类ID
     * @return 分页博客集合
     */
    PageInfo<BlogVO> findBlogPageByTypeId(Integer page, Integer size, Long typeId);

    /**
     * 根据分类ID查询分类
     *
     * @param typeId 分类ID
     * @return 分类
     */
    Type findTypeById(Long typeId);

    /**
     * 根据分类名称查询分类
     *
     * @param typeName 分类名称
     * @return 分类
     */
    Type findTypeByName(String typeName);

    /**
     * 查询分页分类
     *
     * @param page 分页起始数
     * @param size 分页总数
     * @return 分页分类集合
     */
    PageInfo<Type> findTypeByPage(Integer page, Integer size);

    /**
     * 新增分类
     *
     * @param type 分类
     */
    void saveType(Type type);

    /**
     * 更新分类
     *
     * @param type 分类
     */
    void editType(Type type);

    /**
     * 删除分类
     *
     * @param typeId 分类ID
     */
    void deleteType(Long typeId);

}

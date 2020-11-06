package lnori.blog.service;

import com.github.pagehelper.PageInfo;
import lnori.blog.entity.SearchBlog;
import lnori.blog.entity.blog.Blog;
import lnori.blog.entity.blog.vo.*;

import java.util.List;

/**
 * @author Lnori
 */
public interface BlogService {

    /**
     * 根据博客Id查询博客
     *
     * @param blogId 博客ID
     * @return 博客
     */
    BlogVO findBlogById(Long blogId);

    /**
     * 根据博客Id查询博客并处理博客内容
     *
     * @param blogId 博客ID
     * @return 博客
     */
    BlogDetailedVO findBlogDetailedById(Long blogId);

    /**
     * 查询博客首页最新推荐博客
     *
     * @return 博客集合
     */
    List<BlogRecommendVO> findBlogRecommend();

    /**
     * 查询博客信息
     *
     * @return 博客信息集合
     */
    List<Integer> findBlogInformation();


    /**
     * 查询时间轴绚烂博客
     *
     * @return 博客集合
     */
    List<BlogArchivesVO> findArchivesBlog();

    /**
     * 查询博客首页显示博客
     *
     * @param page 分页起始数
     * @param size 分页总数
     * @return 分页博客集合
     */
    PageInfo<BlogVO> findBlogPage(Integer page, Integer size);

    /**
     * 后台查询显示博客
     *
     * @param page 分页起始数
     * @param size 分页总数
     * @return 分页博客集合
     */
    PageInfo<BlogDetailedVO> findAllBlog(Integer page, Integer size);

    /**
     * 根据条件查询显示博客
     *
     * @param page       分页起始数
     * @param size       分页总数
     * @param searchBlog 查询条件
     * @return 分页博客集合
     */
    PageInfo<Blog> findBlogPage(Integer page, Integer size, SearchBlog searchBlog);

    /**
     * 新增博客
     *
     * @param blog 博客
     */
    void saveBlog(Blog blog);

    /**
     * 更新博客
     *
     * @param blog 博客
     */
    void editBlog(Blog blog);

    /**
     * 删除博客
     *
     * @param blogId 博客ID
     */
    void deleteBlog(Long blogId);

}

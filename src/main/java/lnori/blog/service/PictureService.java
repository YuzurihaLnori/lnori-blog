package lnori.blog.service;

import com.github.pagehelper.PageInfo;
import lnori.blog.entity.Picture;

import java.util.List;

/**
 * @author Lnori
 */
public interface PictureService {

    /**
     * 根据图片ID查询图片
     *
     * @param pictureId 图片ID
     * @return 图片
     */
    Picture findPictureByPictureId(Long pictureId);

    /**
     * 查询照片墙显示图片
     *
     * @return 图片
     */
    List<Picture> findAllPicture();

    /**
     * 后台查询显示图片
     *
     * @param page 分页起始数
     * @param size 分页总数
     * @return 分页图片集合
     */
    PageInfo<Picture> findPictureByPage(Integer page, Integer size);

    /**
     * 新增图片
     *
     * @param picture 图片
     */
    void savePicture(Picture picture);

    /**
     * 更新图片
     *
     * @param picture 图片
     */
    void editPicture(Picture picture);

    /**
     * 删除图片
     *
     * @param pictureId 图片ID
     */
    void deletePicture(Long pictureId);
    
}

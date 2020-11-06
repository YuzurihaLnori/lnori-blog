package lnori.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lnori.blog.entity.Picture;
import lnori.blog.enums.BlogExceptionEnum;
import lnori.blog.exception.BlogException;
import lnori.blog.mapper.PictureMapper;
import lnori.blog.service.PictureService;
import lnori.blog.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lnori
 */
@Service("pictureService")
public class PictureServiceImpl implements PictureService {
    @Resource
    private PictureMapper pictureMapper;

    @Override
    public Picture findPictureByPictureId(Long pictureId) {
        Picture picture = pictureMapper.selectByPrimaryKey(pictureId);
        if (StringUtils.isNull(picture)) {
            throw new BlogException(BlogExceptionEnum.PICTURE_NOT_FOUND);
        }
        return picture;
    }

    @Override
    public List<Picture> findAllPicture() {
        return pictureMapper.findAllPicture();
    }

    @Override
    public PageInfo<Picture> findPictureByPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Picture> pictureList = pictureMapper.findAllPicture();
        if (StringUtils.isEmpty(pictureList)) {
            throw new BlogException(BlogExceptionEnum.PICTURE_NOT_FOUND);
        }
        return new PageInfo<>(pictureList);
    }

    @Override
    public void savePicture(Picture picture) {
        int count = pictureMapper.insertSelective(picture);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.PICTURE_SAVE_ERROR);
        }
    }

    @Override
    public void editPicture(Picture picture) {
        int count = pictureMapper.updateByPrimaryKeySelective(picture);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.PICTURE_UPDATE_ERROR);
        }
    }

    @Override
    public void deletePicture(Long pictureId) {
        int count = pictureMapper.deleteByPrimaryKey(pictureId);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.PICTURE_DELETE_ERROR);
        }
    }

}

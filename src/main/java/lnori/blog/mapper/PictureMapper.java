package lnori.blog.mapper;

import lnori.blog.entity.Picture;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Lnori
 */
@Repository
public interface PictureMapper extends Mapper<Picture> {

    /**
     * 查询照片墙显示图片
     *
     * @return 图片
     */
    @Select("select * from tb_picture order by create_time desc")
    List<Picture> findAllPicture();

}

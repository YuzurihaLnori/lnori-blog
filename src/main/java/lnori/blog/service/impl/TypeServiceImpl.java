package lnori.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lnori.blog.entity.Type;
import lnori.blog.entity.blog.vo.BlogVO;
import lnori.blog.enums.BlogExceptionEnum;
import lnori.blog.exception.BlogException;
import lnori.blog.mapper.BlogMapper;
import lnori.blog.mapper.TypeMapper;
import lnori.blog.service.TypeService;
import lnori.blog.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Lnori
 */
@Service("typeService")
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl implements TypeService {
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private BlogMapper blogMapper;

    @Override
    public List<Type> findAll() {
        List<Type> typeList = typeMapper.selectAll();
        if (StringUtils.isEmpty(typeList)) {
            throw new BlogException(BlogExceptionEnum.TYPE_NOT_FOUND);
        }
        return typeList;
    }

    @Override
    public List<Type> findAllByBlog() {
        List<Type> typeList = typeMapper.selectHaveBlog();
        if (StringUtils.isEmpty(typeList)) {
            throw new BlogException(BlogExceptionEnum.TYPE_NOT_FOUND);
        }
        return typeList;
    }

    @Override
    public PageInfo<BlogVO> findBlogPageByTypeId(Integer page, Integer size, Long typeId) {
        PageHelper.startPage(page, size);
        List<BlogVO> blogVOList = blogMapper.findBlogPageByTypeId(typeId);
        return new PageInfo<> (blogVOList);
    }

    @Override
    public Type findTypeById(Long typeId) {
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (StringUtils.isNull(type)) {
            throw new BlogException(BlogExceptionEnum.TYPE_NOT_FOUND);
        }
        return type;
    }

    @Override
    public Type findTypeByName(String typeName) {
        Type type = new Type();
        type.setTypeName(typeName);
        return typeMapper.selectOne(type);
    }

    @Override
    public PageInfo<Type> findTypeByPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Type> typeList = typeMapper.selectAll();
        if (StringUtils.isEmpty(typeList)) {
            throw new BlogException(BlogExceptionEnum.TYPE_NOT_FOUND);
        }
        return new PageInfo<>(typeList);
    }

    @Override
    public void saveType(Type type) {
        int count = typeMapper.insertSelective(type);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.TYPE_SAVE_ERROR);
        }
    }

    @Override
    public void editType(Type type) {
        int count = typeMapper.updateByPrimaryKeySelective(type);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.TYPE_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteType(Long typeId) {
        int count = typeMapper.deleteByPrimaryKey(typeId);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.TYPE_DELETE_ERROR);
        }
    }

}

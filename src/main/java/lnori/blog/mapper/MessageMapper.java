package lnori.blog.mapper;

import lnori.blog.entity.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Lnori
 */
@Repository
public interface MessageMapper extends Mapper<Message>, IdListMapper<Message, Long> {

    /**
     * 查询留言版显示留言
     *
     * @return 留言集合
     */
    @Select("select * from tb_message where parent_id = -1 order by create_time desc")
    @Results(id = "messageMap", value = {
            @Result(id = true, property = "messageId", column = "message_id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "content", column = "content"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "isParent", column = "is_parent"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "parentMessage", column = "parent_id", javaType = Message.class, one = @One(select = "lnori.blog.mapper.MessageMapper.findMessageByParentId")),
            @Result(property = "replyMessageList", column = "message_id", javaType = java.util.List.class, many = @Many(select = "lnori.blog.mapper.MessageMapper.findMessageById"))
    })
    List<Message> findMessage();

    /**
     * 根据父ID查询父留言
     *
     * @param parentId 父ID
     * @return 父留言
     */
    @Select("select * from tb_message where message_id = #{parentId}")
    Message findMessageByParentId(Long parentId);

    /**
     * 根据留言ID查询留言
     *
     * @param messageId 评论ID
     * @return 留言集合
     */
    @Select("select * from tb_message where parent_id = #{messageId}")
    @ResultMap(value = "messageMap")
    List<Message> findMessageById(Long messageId);

    /**
     * 根据留言ID查询留言
     *
     * @param messageId 留言ID
     * @return 留言
     */
    @Select("select * from tb_message where message_id = #{messageId}")
    @ResultMap(value = "messageMap")
    Message findMessageByMessageId(Long messageId);
}

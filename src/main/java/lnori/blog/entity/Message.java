package lnori.blog.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lnori
 */
@Data
@Table(name = "tb_message")
public class Message {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long messageId;
    private Long parentId;
    private String nickName;
    private String email;
    private String content;
    private String avatar;
    private Boolean isParent;
    private Boolean isAdminMessage;
    private Date createTime;

    @Transient
    private Message parentMessage;
    @Transient
    private List<Message> replyMessageList = new ArrayList<>();
}

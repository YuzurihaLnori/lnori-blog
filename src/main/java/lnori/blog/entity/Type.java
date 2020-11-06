package lnori.blog.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Lnori
 */
@Data
@Table(name = "tb_type")
public class Type {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long typeId;
    private String typeName;

    @Transient
    private Integer blogCount;
}

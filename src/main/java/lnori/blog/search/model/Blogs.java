package lnori.blog.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author Lnori
 */
@Data
@Document(indexName = "blogs", type = "docs", shards = 1, replicas = 0)
public class Blogs {

    @Id
    private Long blogId;
    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String all;
    private String title;
    private String description;
    private Integer views;
    private Integer commentCount;
    private String firstPicture;
    @Field(type = FieldType.Date)
    private Date createTime;
    @Field(type = FieldType.Keyword)
    private String userNickName;
    private String userAvatar;
    private Long typeId;
    @Field(type = FieldType.Keyword)
    private String typeName;
}

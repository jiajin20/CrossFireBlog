package cn.gzy.vo;

import cn.gzy.pojo.Blog;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class BlogVo{
    private Integer blogId;
    private String title;
    private String content;
    private Integer author;
    private String authorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  //json序列化格式
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)     // 序列化
    private LocalDateTime createDate;
    private Integer typeId;
    private String typeName;
    private Integer readed;
    private Integer countOfLiked;
    private Integer countOfFavorite;

    public BlogVo(Blog blog){
        this.blogId = blog.getBlogId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.author = blog.getAuthor();
        this.createDate = blog.getCreateDate();
        this.typeId = blog.getTypeId();
        this.readed = blog.getReaded();
    }

}

package cn.gzy.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Favorite {
    private Integer favoriteId;
    private Integer fUserId;
    @JsonProperty("fBlogId")
    private Integer fBlogId;
    private LocalDateTime favoriteDate;
}

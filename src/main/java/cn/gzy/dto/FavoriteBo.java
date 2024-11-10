package cn.gzy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavoriteBo {
    @JsonProperty("fBlogId")
    private Integer fBlogId;
    @JsonProperty("isFavorite")
    private Boolean isFavorite;
}

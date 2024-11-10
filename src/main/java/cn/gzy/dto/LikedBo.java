package cn.gzy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LikedBo {
    @JsonProperty("iBlogId")
    private Integer iBlogId;
    @JsonProperty("isLike")
    private Boolean isLike;
}

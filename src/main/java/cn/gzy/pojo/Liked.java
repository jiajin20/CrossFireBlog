package cn.gzy.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Liked {
    private Integer likedId;
    @JsonProperty("iBlogId")
    private Integer iBlogId;
    private Integer iUserId;
    private LocalDateTime likeDate;
}

package cn.gzy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attention {
    private Integer attentionId;
    private Integer from;
    private Integer to;
    private LocalDateTime attentionDate;

}

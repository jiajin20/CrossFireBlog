package cn.gzy.vo;

import lombok.Data;

@Data
public class Follow {
    private Integer userId;
    private String nickname;
    private String avatar;
    private Integer isMutual; //互关
}

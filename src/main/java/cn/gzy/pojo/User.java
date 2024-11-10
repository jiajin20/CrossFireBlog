package cn.gzy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String account;
    private String password;
    private String nickname;
    private String email;
    private String avatar;
    private Integer status;
    private Date createTime;

}

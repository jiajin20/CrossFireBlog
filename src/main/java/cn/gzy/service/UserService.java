package cn.gzy.service;

import cn.gzy.dto.LoginUser;
import cn.gzy.pojo.User;
import cn.gzy.util.PageUtil;
import cn.gzy.vo.Follow;

public interface UserService {
  String login(LoginUser loginUser);

  User findByUserId(Integer id);

  PageUtil<Follow> findFollowers(Integer id, Integer current, Integer size, String key);

  PageUtil<Follow> findFollowed(Integer id,Integer current, Integer size,String key);

  Integer updateUser(User user);

  Integer updateUserPassword(User user);

  Integer delAttention(Integer from,Integer to);

  // 检查账号是否已存在
  boolean isAccountExist(String account);

  // 检查邮箱是否已存在
  boolean isEmailExist(String email);

  // 执行用户注册
  Integer registerUser(User user);
}

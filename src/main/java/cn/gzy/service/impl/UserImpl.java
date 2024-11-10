package cn.gzy.service.impl;

import cn.gzy.dto.LoginUser;
import cn.gzy.mapper.UserMapper;
import cn.gzy.pojo.User;
import cn.gzy.service.UserService;
import cn.gzy.util.JWTUtil;
import cn.gzy.util.PageUtil;
import cn.gzy.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserImpl implements UserService {
  @Autowired
  private UserMapper userMapper;

  // 检查账号是否已存在
  @Override
  public boolean isAccountExist(String account) {
    User user = new User();
    user.setAccount(account);
    List<User> list = userMapper.searchByColumn(user);
    return list.size() > 0;
  }

  // 检查邮箱是否已存在
  @Override
  public boolean isEmailExist(String email) {
    User user = new User();
    user.setEmail(email);
    List<User> list = userMapper.searchByColumn(user);
    return list.size() > 0;
  }

  // 用户注册
  @Override
  public Integer registerUser(User user) {
    // 设置默认状态为 1（表示激活）
    user.setStatus(1);
    user.setCreateTime(new Date()); // 设置注册时间为当前时间

    // 密码加密（假设使用了一个简单的加密方法，你可以选择使用 BCrypt 等方法）
    String encryptedPassword = encryptPassword(user.getPassword());
    user.setPassword(encryptedPassword);

    // 插入用户数据到数据库
    return userMapper.insertUser(user);
  }

  // 简单的密码加密方法（你可以根据需要换成更安全的加密方式）
  private String encryptPassword(String password) {
    // 这里使用简单的加密方式，实际项目中应该使用更安全的加密算法
    return password; // 暂时直接返回密码（不加密）
  }




  @Override
  public String login(LoginUser loginUser) {
    User user = new User(null,loginUser.getAccount(),loginUser.getPassword(),null,null,null,null,null);
    List<User> list = userMapper.searchByColumn(user);
    if(list.size() == 1){
      return JWTUtil.createToken(list.get(0).getUserId());
    }
    return null;
  }

  @Override
  public User findByUserId(Integer id) {
    User user = new User();
    user.setUserId(id);
    return userMapper.searchByColumn(user).get(0);
  }

  @Override
  public PageUtil<Follow> findFollowers(Integer id, Integer current, Integer size, String key) {
    return findFollow(id,1,current,size,key);
  }

  private PageUtil<Follow> findFollow(Integer id,Integer type, Integer current, Integer size,String key){
    Integer totalCount = userMapper.countOfFollow(id,type,key);
    PageUtil<Follow> followPage = new PageUtil<>(current,size,totalCount);
    followPage.setList(userMapper.followship(id,type,(current-1)*size,size,key));
    return followPage;
  }

  @Override
  public PageUtil<Follow> findFollowed(Integer id, Integer current, Integer size,String key) {
    return findFollow(id,0,current,size,key);
  }

  @Override
  public Integer updateUser(User user) {
    User u = new User();
    u.setUserId(user.getUserId());
    return userMapper.updateByCondition(user,u);
  }

  @Override
  public Integer updateUserPassword(User user) {
    User u = new User();
    u.setAccount(user.getAccount());
    return userMapper.updateByCondition(user,u);
  }

  @Override
  public Integer delAttention(Integer from, Integer to) {
    return userMapper.delAttention(from,to);
  }
}

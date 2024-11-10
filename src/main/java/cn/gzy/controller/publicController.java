package cn.gzy.controller;

import cn.gzy.dto.LoginUser;
import cn.gzy.pojo.User;
import cn.gzy.service.BlogService;
import cn.gzy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class publicController {
  @Autowired
  private UserService userService;
  @Autowired
  private BlogService blogService;
  private final String pre = "forward:/statics/html";
  private final String suf = ".html";

  @PostMapping("/login")
  @ResponseBody
  public Map<String,String> login( @RequestBody LoginUser user){
    Map<String,String> token = new HashMap<String,String>();
    String t = Optional.ofNullable(userService.login(user)).orElse("");
    token.put("token",t);
    return token;
  }


  @GetMapping({"/blog/*","/index","/personInfo/*","/person/*","/users/*","/login"})
  public String common(HttpServletRequest req){
    System.out.println(pre + req.getRequestURI() + suf);
    // 去掉restful的参数，直接访问html文件
    return pre + "/"+req.getRequestURI().split("[/]")[1] + suf;
  }

  @GetMapping("/person/{id}")
  public User personal(HttpServletRequest req){
    Integer userId = (Integer) req.getAttribute("id");
    return userService.findByUserId(userId);
  }


}

package cn.gzy.controller;

import cn.gzy.dto.DelAttention;
import cn.gzy.dto.PageBo;
import cn.gzy.dto.UpdatePasswordUser;
import cn.gzy.pojo.User;
import cn.gzy.service.UserService;
import cn.gzy.util.PageUtil;
import cn.gzy.vo.Follow;
import cn.gzy.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    private UserService userService;



    // 用户注册接口
    @PostMapping("/LoginReg")
    public ResponseEntity<String> register(@RequestBody User user) {
        // 1. 检查账号是否已存在
        if (userService.isAccountExist(user.getAccount())) {
            return ResponseEntity.badRequest().body("账号已存在");
        }

        // 2. 检查邮箱是否已存在
        if (userService.isEmailExist(user.getEmail())) {
            return ResponseEntity.badRequest().body("邮箱已存在");
        }

        // 3. 注册用户
        int result = userService.registerUser(user);
        if (result > 0) {
            return ResponseEntity.ok("注册成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }



    @PostMapping("/personalInfomation")
    public User personalInfo(HttpServletRequest req){
        return userService.findByUserId((Integer)req.getAttribute("id"));
    }
    @PostMapping("/person/{id}")
    public User personal(HttpServletRequest req){
        return userService.findByUserId((Integer)req.getAttribute("id"));
    }

    @PostMapping("/followers")
    public List<Follow> followersInIndex(HttpServletRequest req){
        return userService.findFollowers((Integer)req.getAttribute("id"),1,9,null).getList();
    }
    @PostMapping("/followersOfPage")
    public Page followersOfPage(@RequestBody PageBo page, @RequestParam("key")String key, HttpServletRequest req){
        PageUtil userPage =  userService.findFollowers((Integer)req.getAttribute("id"),page.getCurrent(),page.getSize(),key);
        return new Page(userPage);
    }

    @PostMapping("/followeds")
    public List<Follow> followedsInIndex(HttpServletRequest req){
        return userService.findFollowed((Integer)req.getAttribute("id"),1,9,null).getList();
    }
    @PostMapping("/followedsOfPage")
    public Page followedsOfPage(@RequestBody PageBo page,@RequestParam("key")String key,HttpServletRequest req){
        PageUtil userPage =  userService.findFollowed((Integer)req.getAttribute("id"),page.getCurrent(),page.getSize(),key);
        return new Page(userPage);
    }
    @PostMapping("/personInfo/{userId}")

    public User personalInfo(@PathVariable("userId")Integer userId){
        return userService.findByUserId(userId);
    }




    @PostMapping("/updateAvatar")
    public Integer updateAvatar(MultipartFile avatar,HttpServletRequest req) throws IOException {
        String pathInServer = req.getServletContext().getRealPath("/statics/images/");
        if(pathInServer == null){
            pathInServer = req.getServletContext().getRealPath("/") + File.separator + "statics" + File.separator + "images" + File.separator;
            new File(pathInServer).mkdirs(); // 创建新目录
        }
        String fromFileName = avatar.getOriginalFilename();
        int index = fromFileName.lastIndexOf(46);
        String sufName = fromFileName.substring(index);
        String fileName = fromFileName.substring(0,index);
        String newName = new StringBuffer(fileName).append(new Random().nextInt(10000)).append(sufName).toString();
        avatar.transferTo(new File(pathInServer,newName));
        User user = new User();
        user.setUserId((Integer)req.getAttribute("id"));
        user.setAvatar("/statics/images/" + newName);
        return userService.updateUser(user);
    }

    @PostMapping("/updateUser")
    public Integer updateUser(@RequestBody User user , HttpServletRequest req){
        Integer myId = (Integer)req.getAttribute("id");
        user.setUserId(myId);
        return userService.updateUser(user);
    }

    @PostMapping("/updatePassword")
    public Integer updatePassword(@RequestBody UpdatePasswordUser user){
        if(user.getPassword().equals(user.getCheckPassword())){
            User u = new User(null,user.getAccount(),user.getPassword(),null,null,null,null,null);
            return userService.updateUserPassword(u);
        }
        return null;
    }

    @PostMapping("/delAttention")
    public Integer delAttention(@RequestBody DelAttention delAttention,HttpServletRequest req){
        Integer myId = (Integer)req.getAttribute("id");
        Integer result = 0;
        if(delAttention.getType() == 1){
            result = userService.delAttention(myId,delAttention.getId());
        }else{
            result = userService.delAttention(delAttention.getId(),myId);
        }
        return result;
    }
}

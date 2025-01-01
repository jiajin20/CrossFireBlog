package cn.gzy.controller;

import cn.gzy.pojo.BaseResponse;
import cn.gzy.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);
    @Autowired
    private SendMail emailUtil;
    private static final int EXPIRATION_TIME = 5 * 60 * 1000;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @GetMapping("/getCode")
    @ResponseBody
    public BaseResponse sendVerificationCode(@RequestParam("email") String email, HttpSession session) {
        try {
            String existingCaptcha = (String) session.getAttribute("captcha");
            if (existingCaptcha != null) {
                return BaseResponse.error("该邮箱已有未过期的验证码，请稍后再试");
            }
            String captcha = String.valueOf(new Random().nextInt(899999) + 100000);
            logger.info("Generating captcha for email: {}", email);
            boolean sent = emailUtil.sendMail(email, "您的验证码为：" + captcha + " (5分钟内有效)");
            if (!sent) {
                logger.error("邮件发送失败，邮件地址：{}", email);
                return BaseResponse.error("邮件发送失败，请稍后再试");
            }
            session.setAttribute("captcha", captcha);
            logger.info("验证码已发送并存储到 session 中，邮箱地址：{}", email);
            scheduler.schedule(() -> session.removeAttribute("captcha"), EXPIRATION_TIME, TimeUnit.MILLISECONDS);
            return BaseResponse.success("验证码已发送，请查收邮件");
        } catch (Exception e) {
            logger.error("发送验证码时发生错误", e);
            return BaseResponse.error("邮件发送失败，请稍后再试");
        }
    }
}

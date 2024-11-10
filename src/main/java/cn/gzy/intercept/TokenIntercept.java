package cn.gzy.intercept;

import cn.gzy.exception.TokenErrorException;
import cn.gzy.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class TokenIntercept implements HandlerInterceptor {
    //静态网页由get访问，其余均由post访问
    //get请求则检查cookie中是否有token
    //如果post请求头中有token，并且不为空，则放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("token拦截，/**，非html结尾，非/login");
        String token = null;
        if("get".equals(request.getMethod().toLowerCase())){
            if ("/index".equals(request.getRequestURI())
                    // restful路径匹配正则
                || Arrays.asList("[/]blog[/][0-9]+","[/]personInfo[/][0-9]+","[/]users[/](followers|followed)").
                    stream().filter(i -> {
                          return request.getRequestURI().matches(i);
                    }).count() > 0){
                Cookie[] cookies = request.getCookies();
                if(cookies != null){
                    List<Cookie> list = Arrays.stream(request.getCookies())
                            .filter(cookie -> "token".equals(cookie.getName()))
                            .collect(Collectors.toList());

                    token = list.get(0).getValue();
                }else{
                    response.setStatus(401);
                    response.getWriter().write("there is no cookie");
                    System.err.println("there is no cookie");
                    return false;
                }
            }
        }else{
            token = request.getHeader("token");
        }
        if(token != null){
            try{
                Integer id = JWTUtil.getUserId(token);
                request.setAttribute("id",id);  // 方便每次请求获得id
                return true;
            }catch (TokenErrorException e){
                response.setStatus(401);
                response.getWriter().write(e.getMessage());
                System.err.println(e.getMessage());
                return false;
            }
        }

        return false;  // 这里改为true，放行所有请求，方便调试
    }
}

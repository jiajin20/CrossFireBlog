package cn.gzy.controller;

import cn.gzy.dto.FavoriteBo;
import cn.gzy.dto.LikedBo;
import cn.gzy.pojo.*;
import cn.gzy.service.BlogService;
import cn.gzy.util.PageUtil;
import cn.gzy.vo.BlogVo;
import cn.gzy.vo.CommentVo;
import cn.gzy.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/blogs")
    public Page blogs(@RequestParam Integer current,@RequestParam Integer size, @RequestBody(required = false) Map<String,Object> map){
        PageUtil<BlogVo> pages = blogService.findRecently(current,size,map);
        return new Page(pages);
    }

    @PostMapping("/types")
    public List<BlogType> types(){
        return blogService.findAllType();
    }

    @PostMapping("/addBlog")
    public Integer addBlog(@RequestBody Blog blog){
        blog.setCreateDate(LocalDateTime.now());
        return blogService.addBlog(blog);
    }

    @PostMapping("/blog/{blogId}")
    public BlogVo showBlog(@PathVariable("blogId")Integer id){
        return blogService.showOneBlog(id);
    }

    @PostMapping("/comments")
    public List<CommentVo> comments(@RequestBody Map<String,Integer> map){
        return blogService.findCommentsByBlogId(map.get("blogId"));
    }

    @PostMapping("/recommend")
    public List<Blog> recommend(){
        return blogService.findRecommend();
    }

    @PostMapping("/newComment")
    public Integer newComment(@RequestBody Comment comment, HttpServletRequest req){
        comment.setReviewer((Integer)req.getAttribute("id"));
        comment.setReviewDate(LocalDateTime.now());
        return blogService.newComment(comment);
    }
@PostMapping("/liked")
public Integer liked(@RequestBody LikedBo likedBo, HttpServletRequest req){
    Liked liked = new Liked();
    liked.setIBlogId(likedBo.getIBlogId());
    liked.setIUserId((Integer)req.getAttribute("id"));
    liked.setLikeDate(LocalDateTime.now());
    if(!likedBo.getIsLike()){
        return blogService.liked(liked);
    }else{
        return blogService.noLike(liked);
    }
}
    @PostMapping("/favorited")
    public Integer favorite(@RequestBody FavoriteBo favoriteBo, HttpServletRequest req){
        Favorite favorite = new Favorite();
        favorite.setFBlogId(favoriteBo.getFBlogId());
        favorite.setFUserId((Integer)req.getAttribute("id"));
        favorite.setFavoriteDate(LocalDateTime.now());
        if(!favoriteBo.getIsFavorite()){
            return blogService.favorite(favorite);
        }else{
            return blogService.noFavorite(favorite);
        }

    }
    @PostMapping("/isLiked/{blogId}")
    public Integer liked(@PathVariable Integer blogId, HttpServletRequest req){
        return blogService.isLiked(blogId,(Integer)req.getAttribute("id"));
    }
    @PostMapping("/isFavorited/{blogId}")
    public Integer favorite(@PathVariable Integer blogId, HttpServletRequest req){
        return blogService.isFavorited(blogId,(Integer)req.getAttribute("id"));
    }
}

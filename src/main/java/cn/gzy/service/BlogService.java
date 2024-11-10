package cn.gzy.service;

import cn.gzy.pojo.*;
import cn.gzy.util.PageUtil;
import cn.gzy.vo.BlogVo;
import cn.gzy.vo.CommentVo;

import java.util.List;
import java.util.Map;

public interface BlogService {
    PageUtil<BlogVo> findRecently(Integer current, Integer size, Map<String,Object> map);

    List<BlogType> findAllType();

    Integer addBlog(Blog blog);

    BlogVo showOneBlog(Integer id);

    List<CommentVo> findCommentsByBlogId(Integer blogId);

    List<Blog> findRecommend();

    Integer newComment(Comment comment);

    Integer updateReaded(Blog blog);

    Integer liked(Liked liked);

    Integer favorite(Favorite favorite);

    Integer noLike(Liked liked);

    Integer isLiked(Integer blogId, Integer userId);

    Integer noFavorite(Favorite favorite);

    Integer isFavorited(Integer blogId, Integer userId);
}

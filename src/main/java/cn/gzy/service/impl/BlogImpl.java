package cn.gzy.service.impl;

import cn.gzy.mapper.BlogMapper;
import cn.gzy.mapper.BlogTypeMapper;
import cn.gzy.mapper.UserMapper;
import cn.gzy.pojo.*;
import cn.gzy.service.BlogService;
import cn.gzy.util.PageUtil;
import cn.gzy.vo.BlogVo;
import cn.gzy.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BlogImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogTypeMapper blogTypeMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageUtil<BlogVo> findRecently(Integer current, Integer size, Map<String,Object> map) {
        Integer count = blogMapper.searchCountByCondition(map);
        PageUtil<BlogVo> page = new PageUtil<>(current,size,count);
        page.setList(blogMapper.searchByCondition(map,(page.getCurrent()-1) * page.getSize(),page.getSize()));
        page.getList().stream().forEach(blogVo -> {
            User author = userMapper.searchById(blogVo.getAuthor());
            blogVo.setAuthorName(author.getNickname());
        });
        return page;
    }

    @Override
    public List<BlogType> findAllType() {
        return blogTypeMapper.searchAllType();
    }

    @Override
    public Integer addBlog(Blog blog) {
        return blogMapper.addBlog(blog);
    }

@Override
public BlogVo showOneBlog(Integer id) {
    BlogVo blogVo = blogMapper.findById(id);
    Blog blog = new Blog();
    blog.setBlogId(blogVo.getBlogId());
    blog.setReaded(blogVo.getReaded()+1); //修改点击量
    blogVo.setReaded(blogVo.getReaded()+1);
    blogMapper.updateById(blog);
    return blogVo;
}

    @Override
    public List<CommentVo> findCommentsByBlogId(Integer blogId) {
        return blogMapper.findCommentsByBlogId(blogId);
    }

    @Override
    public List<Blog> findRecommend() {
        return blogMapper.searchRecommend();
    }

    @Override
    public Integer newComment(Comment comment) {

        return blogMapper.newComment(comment);
    }

    @Override
    public Integer updateReaded(Blog blog) {
        return blogMapper.updateById(blog);
    }

    @Override
    public Integer liked(Liked liked) {
        blogMapper.liked(liked);
        return blogMapper.countOfLiked(liked.getIBlogId());
    }

    @Override
    public Integer noLike(Liked liked) {
        blogMapper.noLike(liked);
        return blogMapper.countOfLiked(liked.getIBlogId());
    }

    @Override
    public Integer isLiked(Integer blogId, Integer userId) {
        return blogMapper.isLiked(blogId,userId);
    }

    public Integer favorite(Favorite favorite) {
        blogMapper.favorite(favorite);
        return blogMapper.countOfFavorite(favorite.getFBlogId());
    }
    @Override
    public Integer noFavorite(Favorite favorite) {
        blogMapper.noFavorite(favorite);
        return blogMapper.countOfFavorite(favorite.getFBlogId());
    }

    @Override
    public Integer isFavorited(Integer blogId, Integer userId) {
        return blogMapper.isFavorited(blogId,userId);
    }

    @Override
    public Integer deleteComment(Integer commentId) {
        return blogMapper.deleteComment(commentId);
    }
    @Override
    public List<BlogVo> findTopClickedBlogs() {
        return blogMapper.selectTopClickedBlogs();
    }

    @Override
    public List<Blog> getTopClicks() {
        return blogMapper.getTopClicks();
    }




}

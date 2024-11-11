package cn.gzy.mapper;

import cn.gzy.pojo.*;
import cn.gzy.vo.BlogVo;
import cn.gzy.vo.CommentVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlogMapper {

    @Select("<script>" +
            "select count(1) from blog " +
            "<if test='map != null'>" +
            "<where>" +
            "   <foreach collection='map.keys' item='key'>" +
            "        <if test='key != \"key\"'>" +
            "           and ${key} = #{map[${key}]}" +
            "        </if>" +
            "        <if test='key == \"key\" &amp;&amp; map[key] != \"\"'>" +
            "           and title like concat('%',#{map[${key}]},'%') or content like concat('%',#{map[${key}]},'%')" +
            "        </if>" +
            "   </foreach>" +
            "</where>" +
            "</if>" +
            "</script>")
    Integer searchCountByCondition(Map<String,Object> map);
    @Select("<script>" +
            "select * from blog " +
            "<if test='map != null'>" +
            "<where>" +
            "   <foreach collection='map.keys' item='key'>" +
            "        <if test='key != \"key\"'>" +
            "           and ${key} = #{map[${key}]}" +
            "        </if>" +
            "        <if test='key == \"key\" &amp;&amp; map[key] != \"\"'>" +
            "           and title like concat('%',#{map[${key}]},'%') or content like concat('%',#{map[${key}]},'%')" +
            "        </if>" +
            "   </foreach>" +
            "</where>" +
            "</if>" +
            "order by readed desc limit #{current},#{size}" +
            "</script>")
    List<BlogVo> searchByCondition(
            @Param("map")Map<String,Object> map,
            @Param("current")Integer current,
            @Param("size")Integer size);
    @Insert("insert into blog values (null,#{title},#{content},#{author},#{createDate},#{typeId},#{readed})")
    Integer addBlog(Blog blog);

    @Select("select b.*,u.nickname,(select count(1) from liked where iBlogId = #{id}) countOfLiked," +
            "(select count(1) from favorite where fBlogId = #{id}) countOfFavorite from blog b,users u where b.blogId = #{id} and b.author = u.userId")
    @Results(id="blogVo",value = {
            @Result(id = true,column="blogId",property = "blogId"),
            @Result(column = "title",property = "title"),
            @Result(column = "content",property = "content"),
            @Result(column = "author",property = "author"),
            @Result(column = "createDate",property = "createDate"),
            @Result(column = "typeId",property = "typeId"),
            @Result(column = "readed",property = "readed"),
            @Result(column = "countOfLiked",property = "countOfLiked"),
            @Result(column = "countOfFavorite",property = "countOfFavorite"),
            @Result(property = "authorName",column = "nickname")
    })
    BlogVo findById(Integer id);
    @Select("select * from comment where cBlogId = #{id} order by reviewDate asc")
    @Results({
          @Result(id = true,column = "commentId",property = "commentId"),
          @Result(column = "cBlogId",property = "cBlogId"),
          @Result(column = "comment",property = "comment"),
          @Result(column = "reviewDate",property = "reviewDate"),
          @Result(property = "reviewer",column = "reviewer",javaType = User.class,one = @One(select = "cn.gzy.mapper.UserMapper.searchById"))
    })
    List<CommentVo> findCommentsByBlogId(Integer blogId);
    @Select("select * from blog order by readed desc limit 10")
    List<Blog> searchRecommend();


    @Insert("insert into comment value (null,#{cBlogId},#{reviewer},#{comment},#{reviewDate})")
    Integer newComment(Comment comment);






    @Update("<script>" +
            "update blog <set> " +
            "<if test='readed != null'>" +
            "  readed = #{readed}," +
            "</if>" +
            "</set> where blogId = #{blogId}" +
            "</script>") // 可以在set元素中添加更多if条件，从而更为灵活
    Integer updateById(Blog blog);
    @Insert("insert into liked (iBlogId,iUserId,likeDate) select #{iBlogId},#{iUserId},#{likeDate} from dual " +
            "where not exists(select 1 from liked where iUserId = #{iUserId} and iBlogId = #{iBlogId})")
    //  使用dual
    Integer liked(Liked liked);
    @Insert("insert into favorite (fUserId,fBlogId,favoriteDate) select #{fUserId},#{fBlogId},#{favoriteDate} from dual " +
            "where not exists(select 1 from favorite where fUserId = #{fUserId} and fBlogId = #{fBlogId})")
    Integer favorite(Favorite favorite);
    @Select("select count(1) from liked where iBlogId = #{iBlogId}")
    Integer countOfLiked(Integer iBlogId);
    @Select("select count(1) from favorite where fBlogId = #{fBlogId}")
    Integer countOfFavorite(Integer fBlogId);
    @Delete("delete from liked where iBlogId = #{iBlogId} and iUserId = #{iUserId}")
    Integer noLike(Liked liked);
    @Select("select count(1) from liked where iBlogId = #{blogId} and iUserId = #{userId}")
    Integer isLiked(@Param("blogId") Integer blogId, @Param("userId") Integer userId);
    @Delete("delete from favorite where fBlogId = #{fBlogId} and fUserId = #{fUserId}")
    Integer noFavorite(Favorite favorite);
    @Select("select count(1) from favorite where fBlogId = #{blogId} and fUserId = #{userId}")
    Integer isFavorited(@Param("blogId") Integer blogId, @Param("userId") Integer userId);


    @Delete("DELETE FROM comment WHERE commentId = #{commentId}")
    Integer deleteComment(Integer commentId);




}

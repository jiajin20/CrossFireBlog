package cn.gzy.mapper;

import cn.gzy.pojo.User;
import cn.gzy.vo.Follow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

  List<User> searchByColumn(User user);

  List<User> searchInId(List<Integer> ids);

  Integer updateByCondition(@Param("user") User user,@Param("condition") User condition);
  Integer countOfFollow(@Param("id")Integer id,
                        @Param("type") Integer type,
                        @Param("key")String key);
  List<Follow> followship(@Param("id")Integer id,
                          @Param("type") Integer type,
                          @Param("current")Integer current,
                          @Param("size")Integer size,@Param("key")String key);
  @Select("select userId,account,nickname,email,avatar from users where userId = #{id}")
  User searchById(Integer id);

  @Delete("delete from attention where `from` = #{from} and `to` = #{to}")
  Integer delAttention(@Param("from")Integer from,@Param("to")Integer to);


  @Insert("INSERT INTO users (account, password, nickname, email, avatar) " +
          "VALUES (#{account}, #{password}, #{nickname}, #{email}, #{avatar})")
  int insertUser(User user);

}

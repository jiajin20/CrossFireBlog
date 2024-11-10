package cn.gzy.mapper;

import cn.gzy.pojo.BlogType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogTypeMapper {
    @Select("select * from blogtype")
    List<BlogType> searchAllType();
}

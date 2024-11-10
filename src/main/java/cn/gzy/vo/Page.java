package cn.gzy.vo;

import cn.gzy.util.PageUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
public record Page (Integer current,Integer size,Integer totalSize,Integer totalNumber,List list){
    public Page(PageUtil util){
        this(util.getCurrent(),util.getSize(),util.getTotalSize(),util.getTotalNumber(),util.getList());
    }
}
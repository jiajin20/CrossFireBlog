package cn.gzy.util;

import cn.gzy.pojo.Blog;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class PageUtil<T> {
    private Integer current;
    private Integer size;
    @Setter(AccessLevel.NONE)  // 不允许手动设置最大页码
    private Integer totalSize;
    private Integer totalNumber;
    private List<T> list;

    // 构造方法：传入当前页、每页条数和总数
    public PageUtil(Integer current, Integer size, Integer totalNumber) {
        if (totalNumber < 0) {
            throw new IllegalArgumentException("Total number of items cannot be negative.");
        }
        setSize(size);
        setTotalNumber(totalNumber);
        setCurrent(current);
    }

    // 设置总记录数，并计算总页数
    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
        if (totalNumber == 0) {
            this.totalSize = 0;  // 如果总数为0，总页数也应为0
        } else {
            this.totalSize = (totalNumber + this.size - 1) / this.size; // 计算总页数，避免除法结果为0
        }
    }

    // 设置每页条数，保证在合法范围内
    public void setSize(Integer size) {
        if (size < 1) {
            this.size = 1; // 每页最少1条
        } else if (size > 100) {
            this.size = 100;  // 每页最多100条
        } else {
            this.size = size;
        }
    }

    // 设置当前页，保证当前页在合法范围内
    public void setCurrent(Integer current) {
        // 使用 Math.max 和 Math.min 来确保当前页不小于1且不大于总页数
        this.current = Math.min(Math.max(current, 1), this.totalSize);
    }

    @Override
    public String toString() {
        return "PageUtil{" +
                "current=" + current +
                ", size=" + size +
                ", totalSize=" + totalSize +
                ", totalNumber=" + totalNumber +
                ", list=" + list +
                '}';
    }
}

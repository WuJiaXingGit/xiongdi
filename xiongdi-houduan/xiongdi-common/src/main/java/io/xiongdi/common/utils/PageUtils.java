package io.xiongdi.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * @author  wujiaxing
 * @date 2019-07-14
 */
@Getter
@Setter
public class PageUtils implements Serializable {
    private static final long serialVersionUID = 1668636041458807938L;
    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 当前页数
     */
    private int currPage;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 列表数据
     */
    private List<?> list;

    /**
     *
     * @param list 列表数据
     * @param totalCount 总记录数
     * @param pageSize 每页记录数
     * @param currPage 当前页数
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double)totalCount/pageSize);
    }

    /**
     *
     * @param page mybatis plus 的分页
     */
    public PageUtils(IPage<?> page) {
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
        this.list = page.getRecords();
    }

}

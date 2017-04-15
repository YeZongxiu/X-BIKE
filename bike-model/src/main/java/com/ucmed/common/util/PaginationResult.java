/**
 * 
 */
package com.ucmed.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author John Lee
 */
public class PaginationResult<T> {

    private List<T> list;

    private Long pageCount;

    private Long totalCount;

    public PaginationResult() {
        list = new ArrayList<T>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}

package com.ucmed.common.util;

/**
 * Pagination utils.
 * @author plz
 */
public class PageUtil {
    /**
     * 默认的页大小.
     */
    public static final Long DEFAULT_PAGE_SIZE = 30L;
    /**
     * 默认的页号.
     */
    public static final Long DEFAULT_PAGE_NO = 1L;

    /**
     * 根据对象总数和页容量计算总的页数.
     * @param count
     *            对象总数。
     * @param pageSize
     *            页容量。
     * @return 总的页数；如果参数为空则返回0.
     */
    public static Long getPageCount(Long count, Long pageSize) {
        if(count == null || count <= 0l || pageSize == null || pageSize <= 0l) {
            return 0L;
        }
        return count % pageSize > 0 ? count / pageSize + 1 : count / pageSize;
    }

    /**
     * 根据页号和页大小计算开始值.
     * @param pageNo
     *            页号
     * @param pageSize
     *            页大小
     * @return 计算分页的开始记录的行号
     */
    public static Long getStartRecord(Long pageNo, Long pageSize) {
        if(null == pageNo || pageNo.longValue() <= 0l) {
            pageNo = DEFAULT_PAGE_NO;
        }
        if(null == pageSize || pageSize.longValue() <= 0l) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return (pageNo.longValue() - 1l) * pageSize.longValue();
    }

    /**
     * 根据页号和页大小计算最后的记录.
     * @param pageNo
     *            页号
     * @param pageSize
     *            页
     * @return 分页最后一条记录的行号
     */
    public static Long getEndRecord(Long pageNo, Long pageSize) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = DEFAULT_PAGE_NO;
        }
        if(pageSize == null || pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return getStartRecord(pageNo, pageSize) + pageSize;
    }

    /**
     * 校验页号辅助函数.
     * @param pageNo
     *            页号
     * @return 页号；如果页号小于0或者为空，则返回默认的页号。
     */
    public static Long checkPageNo(Long pageNo) {
        if(pageNo == null || pageNo <= 0l) {
            return DEFAULT_PAGE_NO;
        }
        return pageNo;
    }

    /**
     * 校验页大小辅助函数.
     * @param pageSize
     *            页大小
     * @return 页大小；如果页大小为空或者小于0，则返回默认的页大小。
     */
    public static Long checkPageSize(Long pageSize) {
        if(pageSize == null || pageSize <= 0l) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    /**
     * 检查总记录条数函数.
     * @param count
     *            查询到的总记录条数.
     * @return 合法的总记录条数.
     */
    public static Long getTotalCount(Long count) {
        if(null == count || 0 >= count.longValue()) {
            return 0L;
        }
        return count;
    }

    public static final String PARAMS_KEY_PAGE_SIZE = "pageSize";
    public static final String PARAMS_KEY_PAGE_NO = "page";

}

package com.flyingcow.fastgo.server.common.utils;


import com.flyingcow.fastgo.server.common.filters.SQLFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-14 23:15
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int size;

    public Query(Map<String, Object> params){

        this.putAll(params);

        //分页参数
        if (params.get("page") != null && params.get("limit")!= null){
            this.page = Integer.parseInt(params.get("page").toString());
            this.size = Integer.parseInt(params.get("limit").toString());
            this.put("offset", (page - 1) * size);
            this.put("page", page);
            this.put("limit", size);
        }

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        if(StringUtils.isNotBlank(sidx)){
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if(StringUtils.isNotBlank(order)){
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

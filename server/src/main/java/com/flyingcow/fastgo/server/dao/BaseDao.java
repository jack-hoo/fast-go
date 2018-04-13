package com.flyingcow.fastgo.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/11 16:16
 * @Description:
 */
@Mapper
@Repository
public interface BaseDao<T> {
    void save(T var1);

    void save(Map<String, Object> var1);

    int saveBatch(List<T> var1);

    int update(T var1);

    int update(Map<String, Object> var1);

    int delete(Object var1);

    Integer deleteLogically(Object var1);

    int delete(Map<String, Object> var1);

    int deleteBatch(Object[] var1);

    Integer deleteBatchLogically(Object[] var1);

    T queryObject(Object var1);

    List<T> queryList(Map<String, Object> var1);

    List<T> queryList(Object var1);

    Integer queryTotal(Map<String, Object> var1);

    Integer queryTotal();

    Integer deleteOfMine(@Param("accountId") Integer var1, @Param("ids") Integer[] var2);

    Integer deleteOfMineLogically(@Param("accountId") Integer var1, @Param("ids") Object[] var2);
}


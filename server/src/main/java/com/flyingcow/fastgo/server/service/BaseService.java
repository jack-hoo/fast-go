package com.flyingcow.fastgo.server.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/11 16:33
 * @Description:
 */

public interface BaseService<T, K> {
    T queryObject(K var1);

    List<T> queryList(Map<String, Object> var1);

    Integer queryTotal(Map<String, Object> var1);

    void save(T var1);

    Integer saveBatch(List<T> var1);

    void update(T var1);

    void delete(K var1);

    void deleteLogically(K var1);

    void deleteBatch(K[] var1);

    void deleteBatchLogically(K[] var1);

    Integer deleteOfMine(Integer var1, Integer[] var2);

    Integer deleteOfMineLogically(Integer var1, K[] var2);
}

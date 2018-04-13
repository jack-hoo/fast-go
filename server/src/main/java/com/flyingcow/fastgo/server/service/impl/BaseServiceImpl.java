package com.flyingcow.fastgo.server.service.impl;

import com.flyingcow.fastgo.server.dao.BaseDao;
import com.flyingcow.fastgo.server.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/11 16:34
 * @Description: 基础服务实现类
 */
@Service
public class BaseServiceImpl<T, K> implements BaseService<T, K> {
    @Autowired
    private BaseDao<T> baseDao;

    public BaseServiceImpl() {
    }

    public T queryObject(K id) {
        return this.baseDao.queryObject(id);
    }

    public List<T> queryList(Map<String, Object> map) {
        return baseDao.queryList(map);
    }

    public Integer queryTotal(Map<String, Object> map) {
        return this.baseDao.queryTotal(map);
    }

    public void save(T goodsType) {
        this.baseDao.save(goodsType);
    }

    public Integer saveBatch(List<T> goodsTypes) {
        return this.baseDao.saveBatch(goodsTypes);
    }

    public void update(T goodsType) {
        this.baseDao.update(goodsType);
    }

    public void delete(K id) {
        this.baseDao.delete(id);
    }

    public void deleteLogically(K id) {
        this.baseDao.deleteLogically(id);
    }

    public void deleteBatch(K[] ids) {
        this.baseDao.deleteBatch(ids);
    }

    public void deleteBatchLogically(K[] ids) {
        this.baseDao.deleteBatchLogically(ids);
    }

    public Integer deleteOfMine(Integer myAccountId, Integer[] ids) {
        return this.baseDao.deleteOfMine(myAccountId, ids);
    }

    public Integer deleteOfMineLogically(Integer myAccountId, K[] ids) {
        return this.baseDao.deleteOfMineLogically(myAccountId, ids);
    }
}
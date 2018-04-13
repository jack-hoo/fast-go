package com.flyingcow.fastgo.server.dao;

import com.flyingcow.fastgo.server.entity.SysRoleAuthEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/13 09:39
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleAuthDaoTest {
    @Autowired
    private SysRoleAuthDao sysRoleAuthDao;
    @Test
    public void testRoleAuthDao() {
        List<SysRoleAuthEntity> sysRoleAuthEntities = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            SysRoleAuthEntity roleAuthEntity = new SysRoleAuthEntity();
            roleAuthEntity.setRoleId(1)
                    .setAuthId(i);
            sysRoleAuthEntities.add(roleAuthEntity);
        }

        sysRoleAuthDao.saveBatch(sysRoleAuthEntities);
    }

}
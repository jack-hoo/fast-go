package com.flyingcow.fastgo.server.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/11 16:27
 * @Description:
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
    private long createTime;
    private long opTime;
    private boolean valid;
}

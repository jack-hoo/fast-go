-- 插入基础权限
INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_auth_list', '查看列表', '/sys_role_auth', 'get', 'sys_role_auth', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_auth_add', '保存', '/sys_role_auth', 'post', 'sys_role_auth', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
VALUES ('sys_role_auth_update', '更新', '/sys_role_auth', 'put', 'sys_role_auth', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_auth_delete', '删除', '/sys_role_auth', 'delete', 'sys_role_auth', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_auth_info', '查看一个信息', '/sys_role_auth', 'delete', 'sys_role_auth', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
VALUES ('get_auth_by_role_id', '根据角色获取权限列表', '/get_auth', 'get', 'sys_authority', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
VALUES ('batch_add_auth_for_role', '批量给角色添加权限', '/batch', 'post', 'sys_role_auth', '接口');


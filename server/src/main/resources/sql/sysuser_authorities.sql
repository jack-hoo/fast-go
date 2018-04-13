-- 插入基础权限
INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_user_list', '查看列表', '/sys_user', 'get', 'sys_user', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_user_add', '保存', '/sys_user', 'post', 'sys_user', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
VALUES ('sys_user_update', '更新', '/sys_user', 'put', 'sys_user', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_user_delete', '删除', '/sys_user', 'delete', 'sys_user', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_user_info', '查看一个信息', '/sys_user', 'delete', 'sys_user', '接口');




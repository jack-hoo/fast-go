-- 插入基础权限
INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_list', '查看列表', '/sys_role', 'get', 'sys_role', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_add', '保存', '/sys_role', 'post', 'sys_role', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
VALUES ('sys_role_update', '更新', '/sys_role', 'put', 'sys_role', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_delete', '删除', '/sys_role', 'delete', 'sys_role', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_role_info', '查看一个信息', '/sys_role', 'delete', 'sys_role', '接口');




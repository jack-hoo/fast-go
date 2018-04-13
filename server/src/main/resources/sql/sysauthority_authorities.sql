-- 插入基础权限
INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_authority_list', '查看列表', '/sys_authority', 'get', 'sys_authority', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_authority_add', '保存', '/sys_authority', 'post', 'sys_authority', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
VALUES ('sys_authority_update', '更新', '/sys_authority', 'put', 'sys_authority', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_authority_delete', '删除', '/sys_authority', 'delete', 'sys_authority', '接口');

INSERT INTO `sys_authority` (`auth_code`, `auth_name`, `url`, `method`, `resource_name`, `resource_type`)
    VALUES ('sys_authority_info', '查看一个信息', '/sys_authority', 'delete', 'sys_authority', '接口');




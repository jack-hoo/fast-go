import fetch from '../libs/fetch';
//系统权限级别接口

export function getAuthsByRoleId(roleId) {
    return fetch({
        url: '/role_auth/get_auth',
        method: 'get',
        params: roleId
    });
}
/**
 * 添加sysRoleAuth,供后台使用
 */
export function add_sys_role_auth (sysRoleAuth) {
    return fetch({
        url: '/sys_role_auth',
        method: 'post',
        data: sysRoleAuth
    })
}
/**
 * 查询所有sysRoleAuth纪录,供后台使用
 */
export function get_sys_role_auth_list(params){
	return fetch({
		url: '/sys_role_auth',
		method: 'get',
		params: params
	})
}
/**
 * 查询sysRoleAuth供后台使用
 */
export function get_sys_role_auth (id) {
    return fetch({
        url: '/sys_role_auth/'+id,
        method: 'get',
    })
}

/**
 * 修改sysRoleAuth,供后台使用
 */
export function update_sys_role_auth(sysRoleAuth) {
	return fetch({
		url: '/sys_role_auth',
		method: 'put',
		data: sysRoleAuth
	})
}
/**
 * 删除某个sysRoleAuth资源,供后台使用
 */
export function delete_sys_role_auth(ids) {
	return fetch({
		url: '/sys_role_auth',
		method: 'delete',
		data: ids
	})
}

export function addPermsForRole (roleId,auths) {
    return fetch({
        url: '/role_auth/batch',
        method:'post',
        params:roleId,
        data:auths
    })
}

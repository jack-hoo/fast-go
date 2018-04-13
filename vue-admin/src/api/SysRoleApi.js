import fetch from '../libs/fetch';
//系统权限级别接口
/**
 * 添加sysRole,供后台使用
 */
export function add_sys_role (sysRole) {
    return fetch({
        url: '/sys_role',
        method: 'post',
        data: sysRole
    })
}
/**
 * 查询所有sysRole纪录,供后台使用
 */
export function get_sys_role_list(params){
	return fetch({
		url: '/sys_role',
		method: 'get',
		params: params
	})
}
/**
 * 查询sysRole供后台使用
 */
export function get_sys_role (id) {
    return fetch({
        url: '/sys_role/'+id,
        method: 'get',
    })
}

/**
 * 修改sysRole,供后台使用
 */
export function update_sys_role(sysRole) {
	return fetch({
		url: '/sys_role',
		method: 'put',
		data: sysRole
	})
}
/**
 * 删除某个sysRole资源,供后台使用
 */
export function delete_sys_role(ids) {
	return fetch({
		url: '/sys_role',
		method: 'delete',
		data: ids
	})
}

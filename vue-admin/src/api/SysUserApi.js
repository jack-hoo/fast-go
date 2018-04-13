import fetch from '../libs/fetch';
//系统权限级别接口
/**
 * 添加sysUser,供后台使用
 */
export function add_sys_user (sysUser) {
    return fetch({
        url: '/sys_user',
        method: 'post',
        data: sysUser
    })
}
/**
 * 查询所有sysUser纪录,供后台使用
 */
export function get_sys_user_list(params){
	return fetch({
		url: '/sys_user',
		method: 'get',
		params: params
	})
}
/**
 * 查询sysUser供后台使用
 */
export function get_sys_user (id) {
    return fetch({
        url: '/sys_user/'+id,
        method: 'get',
    })
}

/**
 * 修改sysUser,供后台使用
 */
export function update_sys_user(sysUser) {
	return fetch({
		url: '/sys_user',
		method: 'put',
		data: sysUser
	})
}
/**
 * 删除某个sysUser资源,供后台使用
 */
export function delete_sys_user(ids) {
	return fetch({
		url: '/sys_user',
		method: 'delete',
		data: ids
	})
}

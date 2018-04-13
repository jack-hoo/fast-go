import fetch from '../libs/fetch';
//系统权限级别接口
/**
 * 添加sysAuthority,供后台使用
 */
export function add_sys_authority (sysAuthority) {
    return fetch({
        url: '/sys_authority',
        method: 'post',
        data: sysAuthority
    })
}
/**
 * 查询所有sysAuthority纪录,供后台使用
 */
export function get_sys_authority_list(params){
	return fetch({
		url: '/sys_authority',
		method: 'get',
		params: params
	})
}
/**
 * 查询sysAuthority供后台使用
 */
export function get_sys_authority (id) {
    return fetch({
        url: '/sys_authority/'+id,
        method: 'get',
    })
}

/**
 * 修改sysAuthority,供后台使用
 */
export function update_sys_authority(sysAuthority) {
	return fetch({
		url: '/sys_authority',
		method: 'put',
		data: sysAuthority
	})
}
/**
 * 删除某个sysAuthority资源,供后台使用
 */
export function delete_sys_authority(ids) {
	return fetch({
		url: '/sys_authority',
		method: 'delete',
		data: ids
	})
}


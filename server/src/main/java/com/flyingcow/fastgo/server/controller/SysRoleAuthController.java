package com.flyingcow.fastgo.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.flyingcow.fastgo.server.service.SysAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.AccessDeniedException;
import com.flyingcow.fastgo.server.entity.SysRoleAuthEntity;
import com.flyingcow.fastgo.server.service.SysRoleAuthService;
import com.flyingcow.fastgo.server.common.annotation.CurrentUser;
import com.flyingcow.fastgo.server.common.utils.*;
/**
 * 
 *
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:11:45
 */
@RestController
@RequestMapping("role_auth")
public class SysRoleAuthController {
    protected final static Logger log = LoggerFactory.getLogger(SysRoleAuthController.class);
    @Autowired
    private SysAuthorityService sysAuthorityService;
    @Autowired
    private SysRoleAuthService sysRoleAuthService;

    @GetMapping("/get_auth")
    @PreAuthorize("hasAuthority('get_auth_by_role_id')")
    public Result getAuths(@RequestParam(value = "role_id",required = true) Integer roleId) {
        return ResultUtil.success(sysAuthorityService.getAuthesByRoleId(roleId));
    }

    /**
     * 查询所有纪录,供后台使用
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('sys_role_auth_list')")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据,params中可以包含查询条件
        Query query = new Query(params);
        return getResult(params, query);
    }

    /**
     * 查询某个资源,供后台使用
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys_role_auth_info')")
    public Result info(@PathVariable("id") Integer id) {

        SysRoleAuthEntity sysRoleAuth = sysRoleAuthService.queryObject(id);
        return new Result("sysRoleAuth", sysRoleAuth);
    }

    /**
     * 新增某个系统级别资源,供后台使用
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('sys_role_auth_add')")
    public Result save(@RequestBody SysRoleAuthEntity sysRoleAuth) {
        sysRoleAuthService.save(sysRoleAuth);

        return ResultUtil.success();
    }

    /**
     * 修改资源,供后台使用
     */
    @PutMapping("")
    @PreAuthorize("hasAuthority('sys_role_auth_update')")
    public Result update(@RequestBody SysRoleAuthEntity sysRoleAuth) {
        sysRoleAuthService.update(sysRoleAuth);

        return ResultUtil.success();
    }

    /**
     * 删除某个资源,供后台使用
     */
    @DeleteMapping("")
    @PreAuthorize("hasAuthority('sys_role_auth_delete')")
    public Result delete(@RequestBody Integer[] ids) {
        sysRoleAuthService.deleteBatchLogically(ids);

        return ResultUtil.success();
    }
    //批量给角色授权
    @PostMapping({"/batch"})
    @PreAuthorize("hasAuthority('batch_add_auth_for_role')")
    public Result addAuths(@RequestBody Integer[] auths, @RequestParam(value = "role_id",required = true) Integer roleId) {
        List<SysRoleAuthEntity> roleAuthEntities = new ArrayList();

        for(int i = 0; i < auths.length; ++i) {
            SysRoleAuthEntity roleAuthEntity = new SysRoleAuthEntity();
            roleAuthEntity.setRoleId(roleId).setAuthId(auths[i]);
            roleAuthEntities.add(roleAuthEntity);
        }

        return ResultUtil.success(this.sysRoleAuthService.saveBatch(roleAuthEntities));
    }
    /**
       * 是否获取分页,没有page和limit参数则直接获取所有列表
       * 使用方法   /资源名称?page=1&limit=10&param1=?&sidx=[asc||desc]&order=xx
       * param1指查询参数,sidx指升序或降序,order排序字段
       * @param params
       * @param query
       * @return
       */
    private Result getResult(@RequestParam Map<String, Object> params, Query query) {
        if (params.get("page") != null && params.get("limit") != null) {
            List<SysRoleAuthEntity> sysRoleAuthList = sysRoleAuthService.queryList(query);
            Integer total = sysRoleAuthService.queryTotal(query);
            PageUtils pageUtil = new PageUtils(sysRoleAuthList, total, query.getSize(), query.getPage());
            return new Result(pageUtil);
        } else {
            List<SysRoleAuthEntity> sysRoleAuthList = sysRoleAuthService.queryList(query);
            return new Result("list", sysRoleAuthList);
        }
    }
}

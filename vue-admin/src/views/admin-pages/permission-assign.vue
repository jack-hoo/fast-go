<template>
    <div class="role-man-main">
        <Row :gutter="10">
            <Col :lg="12" :md="24">
            <Card :padding="0">
                <p slot="title" class="card-title">
                    <Icon type="android-checkbox-outline"></Icon>
                    权限分配
                </p>
                <div style="max-height: 400px;overflow: auto;">
                    <Table :columns="auths_by_role_id_cols" :data="auths_by_role_id_data"
                           no-data-text="暂无数据,请选择一个角色"
                    >
                    </Table>
                </div>
            </Card>
            </Col>
            <Col :lg="12" :md="24">
            <Card :padding="0">
                <p slot="title" class="card-title">
                    <Icon type="android-checkbox-outline"></Icon>
                    角色管理
                </p>
                <a type="text" slot="extra" @click.prevent="showAddRoleModal">
                    <Icon type="plus-round"></Icon>
                </a>
                <Modal
                        v-model="showAddRole"
                        title="添加新的角色"
                        @on-ok="addRole"
                        @on-cancel="cancelAddRoleModal">
                    <Row type="flex" justify="center">
                        <Form :model="role" :rules="role_rules" ref="role"
                              :inline="false" :label-width="80"
                        >
                            <Form-item label="角色名称" prop="roleName">
                                <Input v-model="role.roleName" placeholder="英文大写简称" style="width: 300px"/>
                            </Form-item>
                            <Form-item label="角色概述" prop="roleDesc">
                                <Input v-model="role.roleDesc" placeholder="该角色的描述信息" style="width: 300px"/>
                            </Form-item>
                        </Form>
                    </Row>
                    <Row slot="footer">
                        <Button type="text" @click="cancelAddRoleModal">取消</Button>
                        <Button type="primary" @click="addRole">确定</Button>
                    </Row>
                </Modal>
                <div class="role-table">
                    <can-edit-table v-model="roles" :hover-show="true" :edit-incell="true" :columns-list="roles_cols"
                                    @on-cell-change="handleCellChange" @select-change="roleSelectChanged"
                    >

                    </can-edit-table>
                </div>
            </Card>
            </Col>
        </Row>
        <Row :gutter="10">
            <Col span="24">
            <Card :padding="0">

                <p slot="title" class="card-title">
                    <Icon type="android-checkbox-outline"></Icon>
                    权限列表
                </p>
                <a type="text" slot="extra" @click.prevent="showAddAuthModal">
                    <Icon type="plus-round"></Icon>
                </a>

                <div class="">
                    <div class="tools">
                        <Input v-model="auth_page.resourceName" clearable placeholder="请输入资源名称"
                               style="width: 300px"/>
                        <Button type="ghost" icon="ios-search" @click="search" style="margin-left: 15px">搜索</Button>
                        <Button type="ghost" icon="refresh" @click="reflesh" style="margin-left: 15px">刷新</Button>
                    </div>
                    <can-edit-table v-model="permissions_data" ref="permissions"
                                    :hover-show="true" :edit-incell="true" :columns-list="permissions_cols"
                                    @on-perm-select-change="permSelectChange"
                    >
                        <div slot="page">
                            <div class="tools">
                                <Button type="primary" style="margin: 7px 10px;"
                                        @click="addPermissionsToRoleSelected">赋权
                                </Button>
                                <Button type="warning" style="margin: 7px 10px;"
                                        @click="deletePermissionsSelected">删除
                                </Button>
                            </div>

                            <Page class="page-right" :total="auth_page.total" :page-size="auth_page.limit"
                                  :current="auth_page.currPage" @on-change="nextPermPage"></Page>
                        </div>
                    </can-edit-table>

                </div>
            </Card>
            </Col>
            <Modal
                    v-model="showAddAuth"
                    title="添加新的权限"
                    @on-ok="addAuth"
                    @on-cancel="cancelAddAuth">
                <Row type="flex" justify="center">
                    <Form :model="authority" :rules="auth_rules" ref="authority"
                          :inline="false" :label-width="100"
                    >
                        <Form-item label="权限代码" prop="authCode">
                            <Input v-model="authority.authCode" placeholder="...eg:user_list||user_delete||user_info"
                                   style="width: 300px"/>
                        </Form-item>
                        <Form-item label="权限名称" prop="authName">
                            <Input v-model="authority.authName" placeholder="...eg:查看用户列表" style="width: 300px"/>
                        </Form-item>
                        <Form-item label="接口相对地址" prop="url">
                            <Input v-model="authority.url" placeholder="...eg:/user||/good/**" style="width: 300px"/>
                        </Form-item>
                        <Form-item label="方法类型" prop="method">
                            <Input v-model="authority.method" placeholder="...eg:get||post||delete"
                                   style="width: 300px"/>
                        </Form-item>
                        <Form-item label="资源名称" prop="resourceName">
                            <Input v-model="authority.resourceName" placeholder="...eg:sys_user||sys_role"
                                   style="width: 300px"/>
                        </Form-item>
                        <Form-item label="资源类型" prop="resourceType">
                            <Input v-model="authority.resourceType" placeholder="...eg:接口||内部服务" style="width: 300px"/>
                        </Form-item>
                    </Form>
                </Row>
                <Row slot="footer">
                    <Button type="text" @click="cancelAddAuth">取消</Button>
                    <Button type="primary" @click="addAuth">确定</Button>
                </Row>
            </Modal>
        </Row>
    </div>

</template>

<script>
    import {get_sys_role_list, update_sys_role, delete_sys_role, add_sys_role} from '../../api/SysRoleApi'
    import {add_sys_authority, get_sys_authority_list} from '../../api/SysAuthorityApi'
    import {delete_sys_role_auth, getAuthsByRoleId, addPermsForRole} from '../../api/SysRoleAuthApi'
    import CanEditTable from '../components/table/canEditTable';

    export default {
        components: {CanEditTable},
        name: 'permission-assign',
        data() {
            return {
                authority: {
                    authCode: '',
                    authName: '',
                    url: '',
                    method: '',
                    resourceType: '',
                    resourceName: ''
                },
                auth_rules: {
                    authCode: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ],
                    authName: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ],
                    method: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ],
                    resourceName: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ],
                    resourceType: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ]
                },
                showAddAuth: false,
                showAddRole: false,
                roles_cols: [
                    {
                        title: '角色ID',
                        key: 'id',
                    },
                    {
                        title: '角色代码',
                        key: 'roleName',
                        editable: true
                    },
                    {
                        title: '角色名称',
                        key: 'roleDesc',
                        editable: true
                    },
                    {
                        title: '操作',
                        width: 150,
                        key: 'id',
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.deleteRole(params.index);
                                        }
                                    }
                                }, '删除')
                            ]);
                        }
                    }
                ],
                roles: [],
                role_selected: {},
                role: {
                    id: '',
                    roleName: '',
                    roleDesc: ''
                },
                role_rules: {
                    roleName: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ],
                    roleDesc: [
                        {required: true, message: '内容不能为空！', trigger: 'blur'}
                    ]
                },
                auths_by_role_id_data: [],
                auths_by_role_id_cols: [
                    {
                        title: '权限代码',
                        key: 'authCode'
                    },
                    {
                        title: '权限名称',
                        key: 'authName'
                    },

                    {
                        title: '接口地址',
                        key: 'url'
                    },
                    {
                        title: '方法类型',
                        key: 'method'
                    },
                    {
                        title: '资源类型',
                        key: 'resourceType'
                    },
                    {
                        title: '操作',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.deleteAuthFromRole(params.index);
                                        }
                                    }
                                }, '删除')
                            ]);
                        }
                    }
                ],
                permissions_data: [],
                permissions_cols: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '权限代码',
                        key: 'authCode'
                    },
                    {
                        title: '权限名称',
                        key: 'authName'
                    },
                    {
                        title: '资源名称',
                        key: 'resourceName'
                    },
                    {
                        title: '接口地址',
                        key: 'url'
                    },
                    {
                        title: '方法类型',
                        key: 'method'
                    },
                    {
                        title: '资源类型',
                        key: 'resourceType'
                    }
                ],
                perm_selected: [],
                auth_page: {
                    limit: 15,
                    page: 1,
                    order: 'auth_code',
                    totalPage: 0,
                    currPage: 0,
                    resourceName: null,
                    total: 0
                }
            };
        },
        methods: {
            reflesh() {
                this.auth_page = {
                    limit: 15,
                    page: 1,
                    order: 'auth_code',
                    totalPage: 0,
                    currPage: 0,
                    resourceName: null,
                    total: 0
                }
                this.getPermissions();
            },
            search() {
                if (this.auth_page.resourceName === '') {
                    return;
                } else {
                    this.getPermissions();
                }
            },
            nextPermPage(page) {
                this.auth_page.page = page
                this.getPermissions()
            },
            showAddAuthModal() {
                this.showAddAuth = true
            },
            addAuth() {
                this.$refs['authority'].validate((valid => {
                    if (valid) {
                        add_sys_authority(this.authority).then(res => {
                            this.getPermissions()
                            this.cancelAddAuth()
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                }))
            },
            cancelAddAuth() {
                this.$refs['authority'].resetFields();
                this.showAddAuth = false
            },
            deletePermissionsSelected() {
                if (this.perm_selected.length === 0) {
                    this.$Modal.error({
                        title: '错误',
                        content: '必须选择一个权限'
                    });
                    return;
                }
                let permsIds = [];
                this.perm_selected.forEach((item) => {
                    permsIds.push(item.id);
                });
                add_sys_authority(permsIds).then(res => {
                    this.getPermissions();
                }).catch(error => {
                    console.log(error);
                });
            },
            permSelectChange(selection) {
                this.perm_selected = selection;
            },
            addPermissionsToRoleSelected() {
                if (this.role_selected.id === undefined) {
                    this.$Modal.error({
                        title: '错误',
                        content: '必须选择一个角色'
                    });
                    return;
                }
                if (this.perm_selected.length === 0) {
                    this.$Modal.error({
                        title: '错误',
                        content: '必须选择一个权限'
                    });
                    return;
                }
                let permsIds = [];
                this.perm_selected.forEach((item) => {
                    permsIds.push(item.id);
                });
                let params = {
                    role_id: this.role_selected.id
                };
                addPermsForRole(params, permsIds).then(res => {
                    this.getAuthsByRoleId();
                }).catch(error => {
                    console.log(error);
                });
                /*addPermForRole(role_perm).then(res =>{
                    this.getAuthsByRoleId();
                }).catch(error => {
                    console.log(error)
                })*/
            },
            deletePermission(index) {
                console.log(this.permissions_data[index]);
                let authIds = [];

            },
            getPermissions() {
                get_sys_authority_list(this.auth_page).then(res => {
                    this.permissions_data = res.list;
                    this.auth_page.totalPage = res.totalPage
                    this.auth_page.currPage = res.currPage
                    this.auth_page.limit = res.pageSize
                    this.auth_page.total = res.totalCount
                });
            },
            deleteAuthFromRole(index) {
                let ids = [];
                ids.push(this.auths_by_role_id_data[index].roleAuthId);
                delete_sys_role_auth(ids).then(res => {
                    this.getAuthsByRoleId();
                });
            },
            getAuthsByRoleId() {
                let params = {
                    role_id: this.role_selected.id
                };
                getAuthsByRoleId(params).then(res => {
                    this.auths_by_role_id_data = res;
                });
            },
            roleSelectChanged(curRow, oldRow) {
                this.role_selected = curRow;
                this.getAuthsByRoleId();
            },
            getRoles() {
                let params = {
                    order: 'id',
                    sidx: 'asc'
                };
                get_sys_role_list(params).then(res => {
                    this.roles = res;
                });
            },
            deleteRole(index) {
                let ids = [];
                ids.push(this.roles[index].id);
                delete_sys_role(ids).then(res => {
                    this.getRoles();
                });
            },
            handleCellChange(vals, index, key) {
                update_sys_role(vals[index]).then(res => {
                    this.getRoles();
                });
            },
            addRole() {
                this.$refs['role'].validate((valid) => {
                    if (valid) {
                        add_sys_role(this.role).then(res => {
                            this.cancelAddRoleModal();
                            this.getRoles();
                        });
                    }
                });
            },
            cancelAddRoleModal() {
                this.$refs['role'].resetFields();
                this.showAddRole = false;

            },
            showAddRoleModal() {
                this.showAddRole = true;
            }

        },
        mounted() {
            this.getRoles();
            this.getPermissions();
        }
    };
</script>

<style scoped>
    .page-right {
        float: right;
        margin-right: 10px;
    }

    .tools {
        margin: 10px;
        display: flex;
    }
</style>

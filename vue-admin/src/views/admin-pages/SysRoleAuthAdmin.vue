<template>
    <Card class="card-head-black">
        <p slot="title">
            <Icon type="android-checkbox-outline"></Icon>

        </p>
        <div slot="extra" style="margin-top: -5px !important;">
            <Button type="primary" shape="circle" icon="plus-round"
                    @click.prevent="show_add_sys_role_auth_modal"></Button>
        </div>
        <div>
            <Table stripe :columns="sysRoleAuthCols" :data="sysRoleAuthList">
                <p slot="footer" class="page-right">
                    <Page :total="params.total" :page-size="params.limit" :current="params.currPage"
                          @on-change="nextPage"></Page>
                </p>
            </Table>
        </div>
        <Modal
                v-model="add_sys_role_auth_modal"
                title="添加新的"
                @on-ok="add_sys_role_auth"
                @on-cancel="hide_add_sys_role_auth_modal">
            <Row type="flex" justify="center">
                <Form :model="sysRoleAuth" :rules="sysRoleAuthRules" ref="sysRoleAuth"
                      :inline="false" :label-width="80"
                >

                    <Form-item label="role_id" prop="roleId">
                        <Input v-model="sysRoleAuth.roleId" placeholder="请填写sysRoleAuth.roleId" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="auth_id" prop="authId">
                        <Input v-model="sysRoleAuth.authId" placeholder="请填写sysRoleAuth.authId" style="width: 300px"/>
                    </Form-item>


                </Form>
            </Row>
            <Row slot="footer">
                <Button type="text" @click="hide_add_sys_role_auth_modal">取消</Button>
                <Button type="primary" @click="add_sys_role_auth">确定</Button>
            </Row>
        </Modal>
        <Modal
                v-model="update_sys_role_auth_modal"
                title="更新"
                @on-ok="update_sys_role_auth"
                @on-cancel="hide_update_sys_role_auth_modal">
            <Row type="flex" justify="center">
                <Form :model="sysRoleAuth" :rules="sysRoleAuthRules" ref="update_sys_role_auth"
                      :inline="false" :label-width="80"
                >
                    <Form-item label="role_id" prop="roleId">
                        <Input v-model="sysRoleAuth.roleId" placeholder="请填写sysRoleAuth.roleId" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="auth_id" prop="authId">
                        <Input v-model="sysRoleAuth.authId" placeholder="请填写sysRoleAuth.authId" style="width: 300px"/>
                    </Form-item>
                </Form>
            </Row>
            <Row slot="footer">
                <Button type="text" @click="hide_update_sys_role_auth_modal">取消</Button>
                <Button type="primary" @click="update_sys_role_auth">确定</Button>
            </Row>
        </Modal>
    </Card>
</template>

<script>
    import {
        add_my_sys_role_auth, get_sys_role_auth_list, delete_sys_role_auth, update_sys_role_auth,
    } from '../../api/SysRoleAuthApi';

    export default {
        name: 'SysRoleAuth',
        computed: {
            role: function () {
                return this.$store.state.user.userInfo.role;
            }
        },
        data() {
            return {
                sysRoleAuth: {
                    id: null,

                    roleId: null,

                    authId: null,


                },
                sysRoleAuthRules: {},
                add_sys_role_auth_modal: false,

                update_sys_role_auth_modal: false,
                sysRoleAuthList: [],
                sysRoleAuthCols: [
                    {
                        title: 'id',
                        key: 'id',
                        //width: 100
                    },

                    {
                        title: 'roleId',
                        key: 'roleId',
                        //width: 100
                    },

                    {
                        title: 'authId',
                        key: 'authId',
                        //width: 100
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
                                        type: 'info',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show_update_sys_role_auth_modal(params.index);
                                        }
                                    }
                                }, '编辑'),
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
                                            this.delete_sys_role_auth(params.index);
                                        }
                                    }
                                }, '删除')
                            ]);
                        }
                    }
                ],
                params: {
                    limit: 15,
                    page: 1,
                    //order:'order',
                    sidx: 'asc',
                    totalPage: 0,
                    currPage: 0,
                    total: 0
                }
            }
        },
        methods: {
            update_sys_role_auth() {
                update_sys_role_auth(this.sysRoleAuth).then(res => {
                    this.hide_update_sys_role_auth_modal();
                    this.get_sys_role_auth_list();
                }).catch(error => {
                    console.log(error);
                });

            },
            add_sys_role_auth() {
                add_my_sys_role_auth(this.sysRoleAuth).then(res => {
                    this.get_sys_role_auth_list();
                    this.hide_add_sys_role_auth_modal();
                }).catch(error => {
                    console.log(error);
                });
            },
            hide_add_sys_role_auth_modal() {
                this.$refs.sysRoleAuth.resetFields();
                this.add_sys_role_auth_modal = false
            },
            hide_update_sys_role_auth_modal() {
                this.$refs.update_sys_role_auth.resetFields
                ();
                this.update_sys_role_auth_modal = false;
            },
            delete_sys_role_auth(index) {
                let ids = [];
                ids.push(this.sysRoleAuthList[index].id);
                delete_sys_role_auth(ids).then(res => {
                    this.get_sys_role_auth_list();
                }).catch(error => {
                    console.log(error);
                });
            },
            show_update_sys_role_auth_modal(index) {
                this.sysRoleAuth = JSON.parse(JSON.stringify(this.sysRoleAuthList[index]));
                this.update_sys_role_auth_modal = true
            },
            show_add_sys_role_auth_modal() {
                this.add_sys_role_auth_modal = true
            },
            nextPage(page) {
                this.params.page = page;
                this.get_sys_role_auth_list();
            },
            get_sys_role_auth_list() {
                //如果角色是管理员则调用管理员的接口
                get_sys_role_auth_list(this.params).then(res => {
                    this.sysRoleAuthList = res.list;
                    this.params.totalPage = res.totalPage;
                    this.params.currPage = res.currPage;
                    this.params.limit = res.pageSize;
                    this.params.total = res.totalCount;
                }).catch(error => {
                    console.log(error);
                });
            },

        },
        mounted() {
            this.get_sys_role_auth_list()
        }
    }
</script>
<style scoped>

</style>

<template>
    <Card class="card-head-black">
        <p slot="title">
            <Icon type="android-checkbox-outline"></Icon>

        </p>
        <div slot="extra" style="margin-top: -5px !important;">
            <Button type="primary" shape="circle" icon="plus-round"
                    @click.prevent="show_add_sys_user_modal"></Button>
        </div>
        <div>
            <Table stripe :columns="sysUserCols" :data="sysUserList">
                <p slot="footer" class="page-right">
                    <Page :total="params.total" :page-size="params.limit" :current="params.currPage"
                          @on-change="nextPage"></Page>
                </p>
            </Table>
        </div>
        <Modal
                v-model="add_sys_user_modal"
                title="添加新的"
                @on-ok="add_sys_user"
                @on-cancel="hide_add_sys_user_modal">
            <Row type="flex" justify="center">
                <Form :model="sysUser" :rules="sysUserRules" ref="sysUser"
                      :inline="false" :label-width="80"
                >

                    <Form-item label="account" prop="account">
                        <Input v-model="sysUser.account" placeholder="请填写sysUser.account" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="password" prop="password">
                        <Input v-model="sysUser.password" placeholder="请填写sysUser.password" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="username" prop="username">
                        <Input v-model="sysUser.username" placeholder="请填写sysUser.username" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="sex" prop="sex">
                        <Input v-model="sysUser.sex" placeholder="请填写sysUser.sex" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="email" prop="email">
                        <Input v-model="sysUser.email" placeholder="请填写sysUser.email" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="phone" prop="phone">
                        <Input v-model="sysUser.phone" placeholder="请填写sysUser.phone" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="qq" prop="qq">
                        <Input v-model="sysUser.qq" placeholder="请填写sysUser.qq" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="wechat_id" prop="wechatId">
                        <Input v-model="sysUser.wechatId" placeholder="请填写sysUser.wechatId" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="role_id" prop="roleId">
                        <Input v-model="sysUser.roleId" placeholder="请填写sysUser.roleId" style="width: 300px"/>
                    </Form-item>

                    <Form-item label="status" prop="status">
                        <Input v-model="sysUser.status" placeholder="请填写sysUser.status" style="width: 300px"/>
                    </Form-item>


                </Form>
            </Row>
            <Row slot="footer">
                <Button type="text" @click="hide_add_sys_user_modal">取消</Button>
                <Button type="primary" @click="add_sys_user">确定</Button>
            </Row>
        </Modal>
        <Modal
                v-model="update_sys_user_modal"
                title="更新"
                @on-ok="update_sys_user"
                @on-cancel="hide_update_sys_user_modal">
            <Row type="flex" justify="center">
                <Form :model="sysUser" :rules="sysUserRules" ref="update_sys_user"
                      :inline="false" :label-width="80"
                >
                    <Form-item label="account" prop="account">
                        <Input v-model="sysUser.account" placeholder="请填写sysUser.account" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="password" prop="password">
                        <Input v-model="sysUser.password" placeholder="请填写sysUser.password" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="username" prop="username">
                        <Input v-model="sysUser.username" placeholder="请填写sysUser.username" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="sex" prop="sex">
                        <Input v-model="sysUser.sex" placeholder="请填写sysUser.sex" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="email" prop="email">
                        <Input v-model="sysUser.email" placeholder="请填写sysUser.email" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="phone" prop="phone">
                        <Input v-model="sysUser.phone" placeholder="请填写sysUser.phone" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="qq" prop="qq">
                        <Input v-model="sysUser.qq" placeholder="请填写sysUser.qq" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="wechat_id" prop="wechatId">
                        <Input v-model="sysUser.wechatId" placeholder="请填写sysUser.wechatId" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="role_id" prop="roleId">
                        <Input v-model="sysUser.roleId" placeholder="请填写sysUser.roleId" style="width: 300px"/>
                    </Form-item>
                    <Form-item label="status" prop="status">
                        <Input v-model="sysUser.status" placeholder="请填写sysUser.status" style="width: 300px"/>
                    </Form-item>
                </Form>
            </Row>
            <Row slot="footer">
                <Button type="text" @click="hide_update_sys_user_modal">取消</Button>
                <Button type="primary" @click="update_sys_user">确定</Button>
            </Row>
        </Modal>
    </Card>
</template>

<script>
    import {
        add_my_sys_user, get_sys_user_list, delete_sys_user, update_sys_user,
    } from '../../api/SysUserApi';

    export default {
        name: 'SysUser',
        computed: {
            role: function () {
                return this.$store.state.user.userInfo.role;
            }
        },
        data() {
            return {
                sysUser: {
                    id: null,

                    account: null,

                    password: null,

                    username: null,

                    sex: null,

                    email: null,

                    phone: null,

                    qq: null,

                    wechatId: null,

                    roleId: null,

                    status: null,


                },
                sysUserRules: {},
                add_sys_user_modal: false,

                update_sys_user_modal: false,
                sysUserList: [],
                sysUserCols: [
                    {
                        title: 'id',
                        key: 'id',
                        //width: 100
                    },

                    {
                        title: 'account',
                        key: 'account',
                        //width: 100
                    },
                    {
                        title: 'username',
                        key: 'username',
                        //width: 100
                    },

                    {
                        title: 'sex',
                        key: 'sex',
                        //width: 100
                    },
                    {
                        title: 'email',
                        key: 'email',
                        //width: 100
                    },
                    {
                        title: 'phone',
                        key: 'phone',
                        //width: 100
                    },

                    {
                        title: 'qq',
                        key: 'qq',
                        //width: 100
                    },

                    {
                        title: 'wechatId',
                        key: 'wechatId',
                        //width: 100
                    },

                    {
                        title: 'roleId',
                        key: 'roleId',
                        //width: 100
                    },

                    {
                        title: 'status',
                        key: 'status',
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
                                            this.show_update_sys_user_modal(params.index);
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
                                            this.delete_sys_user(params.index);
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
            update_sys_user() {
                update_sys_user(this.sysUser).then(res => {
                    this.hide_update_sys_user_modal();
                    this.get_sys_user_list();
                }).catch(error => {
                    console.log(error);
                });

            },
            add_sys_user() {
                add_my_sys_user(this.sysUser).then(res => {
                    this.get_sys_user_list();
                    this.hide_add_sys_user_modal();
                }).catch(error => {
                    console.log(error);
                });
            },
            hide_add_sys_user_modal() {
                this.$refs.sysUser.resetFields();
                this.add_sys_user_modal = false
            },
            hide_update_sys_user_modal() {
                this.$refs.update_sys_user.resetFields
                ();
                this.update_sys_user_modal = false;
            },
            delete_sys_user(index) {
                let ids = [];
                ids.push(this.sysUserList[index].id);
                delete_sys_user(ids).then(res => {
                    this.get_sys_user_list();
                }).catch(error => {
                    console.log(error);
                });
            },
            show_update_sys_user_modal(index) {
                this.sysUser = JSON.parse(JSON.stringify(this.sysUserList[index]));
                this.update_sys_user_modal = true
            },
            show_add_sys_user_modal() {
                this.add_sys_user_modal = true
            },
            nextPage(page) {
                this.params.page = page;
                this.get_sys_user_list();
            },
            get_sys_user_list() {
                //如果角色是管理员则调用管理员的接口
                get_sys_user_list(this.params).then(res => {
                    this.sysUserList = res.list;
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
            this.get_sys_user_list()
        }
    }
</script>
<style scoped>

</style>

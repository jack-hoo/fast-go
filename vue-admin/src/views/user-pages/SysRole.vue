<template>
        <Card class="card-head-black">
            <p slot="title">
                <Icon type="android-checkbox-outline"></Icon>
                
            </p>
            <div slot="extra" style="margin-top: -5px !important;">
                <Button type="primary" shape="circle" icon="plus-round"
                        @click.prevent="show_add_sys_role_modal"></Button>
            </div>
            <div>
                <Table stripe :columns="sysRoleCols" :data="sysRoleList">
                    <p slot="footer" class="page-right">
                        <Page :total="params.total" :page-size="params.limit" :current="params.currPage"
                              @on-change="nextPage"></Page>
                    </p>
                </Table>
            </div>
            <Modal
                    v-model="add_sys_role_modal"
                    title="添加新的"
                    @on-ok="add_sys_role"
                    @on-cancel="hide_add_sys_role_modal">
                <Row type="flex" justify="center">
                    <Form :model="sysRole" :rules="sysRoleRules" ref="sysRole"
                          :inline="false" :label-width="80"
                    >
                                                    
                                                                                    <Form-item label="role_name" prop="roleName">
                                    <Input v-model="sysRole.roleName" placeholder="请填写sysRole.roleName" style="width: 300px"/>
                                </Form-item>
                            
                                                                                    <Form-item label="role_desc" prop="roleDesc">
                                    <Input v-model="sysRole.roleDesc" placeholder="请填写sysRole.roleDesc" style="width: 300px"/>
                                </Form-item>
                            
                                                    
                                                    
                                                    
                                            </Form>
                </Row>
                <Row slot="footer">
                    <Button type="text" @click="hide_add_sys_role_modal">取消</Button>
                    <Button type="primary" @click="add_sys_role">确定</Button>
                </Row>
            </Modal>
            <Modal
                    v-model="update_sys_role_modal"
                    title="更新"
                    @on-ok="update_sys_role"
                    @on-cancel="hide_update_sys_role_modal">
                <Row type="flex" justify="center">
                    <Form :model="sysRole" :rules="sysRoleRules" ref="update_sys_role"
                          :inline="false" :label-width="80"
                    >
                                                                                                                                        <Form-item label="role_name" prop="roleName">
                                    <Input v-model="sysRole.roleName" placeholder="请填写sysRole.roleName" style="width: 300px"/>
                                </Form-item>
                                                                                                                <Form-item label="role_desc" prop="roleDesc">
                                    <Input v-model="sysRole.roleDesc" placeholder="请填写sysRole.roleDesc" style="width: 300px"/>
                                </Form-item>
                                                                                                                                                                                                                                    </Form>
                </Row>
                <Row slot="footer">
                    <Button type="text" @click="hide_update_sys_role_modal">取消</Button>
                    <Button type="primary" @click="update_sys_role">确定</Button>
                </Row>
            </Modal>
        </Card>
</template>

<script>
    import {
        add_my_sys_role, update_my_sys_role, delete_my_sys_role, get_my_sys_role_list
    } from '../../api/SysRoleApi';

    export default {
        name:'SysRole',
        computed: {
            role: function () {
                return this.$store.state.user.userInfo.role;
            }
        },
        data(){
            return{
                sysRole:{
                                                                        id:null,
                        
                                                                        roleName:null,
                        
                                                                        roleDesc:null,
                        
                                            
                                            
                                            
                                    },
                sysRoleRules:{},
                add_sys_role_modal:false,

                update_sys_role_modal:false,
                sysRoleList:[],
                sysRoleCols:[
                                                                        {
                                title: 'id',
                                key: 'id',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'roleName',
                                key: 'roleName',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'roleDesc',
                                key: 'roleDesc',
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
                                       this.show_update_sys_role_modal(params.index);
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
                                       this.delete_sys_role(params.index);
                                   }
                        }
                        }, '删除')
                        ]);
                        }
                    }
                ],
                params:{
                    limit:15,
                    page:1,
                    //order:'order',
                    sidx:'asc',
                    totalPage:0,
                    currPage:0,
                    total:0}
            }
        },
        methods:{
            update_sys_role(){
                update_my_sys_role(this.sysRole).then(res => {
                        this.hide_update_sys_role_modal();
                        this.get_sys_role_list();
               }).catch(error => {
                   console.log(error);
               });
            },
            add_sys_role(){
                add_my_sys_role(this.sysRole).then(res => {
                    this.get_sys_role_list();
                    this.hide_add_sys_role_modal();
                }).catch(error => {
                    console.log(error);
                });
            },
            hide_add_sys_role_modal(){
                this.$refs.sysRole.resetFields();
                this.add_sys_role_modal = false
            },
            hide_update_sys_role_modal(){
                this.$refs.update_sys_role.resetFields
                ();
                this.update_sys_role_modal = false;
            },
            delete_sys_role(index){
                delete_my_sys_role(ids).then(res => {
                      this.get_sys_role_list();
                }).catch(error => {
                    console.log(error);
                });
            },
            show_update_sys_role_modal(index){
                this.sysRole = JSON.parse(JSON.stringify(this.sysRoleList[index]));
                this.update_sys_role_modal = true
            },
            show_add_sys_role_modal(){
                this.add_sys_role_modal = true
            },
            nextPage(page){
                this.params.page = page;
                this.get_sys_role_list();
            },
            get_sys_role_list(){
                get_my_sys_role_list(this.params).then(res => {
                  this.sysRoleList = res.list;
                  this.params.totalPage = res.totalPage;
                  this.params.currPage = res.currPage;
                  this.params.limit = res.pageSize;
                  this.params.total = res.totalCount;
                }).catch(error => {
                          console.log(error);
                });
            },

        },
        mounted(){
            this.get_sys_role_list()
        }
    }
</script>
<style scoped>

</style>
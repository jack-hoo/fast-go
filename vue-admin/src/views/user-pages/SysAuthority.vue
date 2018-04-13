<template>
        <Card class="card-head-black">
            <p slot="title">
                <Icon type="android-checkbox-outline"></Icon>
                
            </p>
            <div slot="extra" style="margin-top: -5px !important;">
                <Button type="primary" shape="circle" icon="plus-round"
                        @click.prevent="show_add_sys_authority_modal"></Button>
            </div>
            <div>
                <Table stripe :columns="sysAuthorityCols" :data="sysAuthorityList">
                    <p slot="footer" class="page-right">
                        <Page :total="params.total" :page-size="params.limit" :current="params.currPage"
                              @on-change="nextPage"></Page>
                    </p>
                </Table>
            </div>
            <Modal
                    v-model="add_sys_authority_modal"
                    title="添加新的"
                    @on-ok="add_sys_authority"
                    @on-cancel="hide_add_sys_authority_modal">
                <Row type="flex" justify="center">
                    <Form :model="sysAuthority" :rules="sysAuthorityRules" ref="sysAuthority"
                          :inline="false" :label-width="80"
                    >
                                                    
                                                                                    <Form-item label="auth_code" prop="authCode">
                                    <Input v-model="sysAuthority.authCode" placeholder="请填写sysAuthority.authCode" style="width: 300px"/>
                                </Form-item>
                            
                                                                                    <Form-item label="auth_name" prop="authName">
                                    <Input v-model="sysAuthority.authName" placeholder="请填写sysAuthority.authName" style="width: 300px"/>
                                </Form-item>
                            
                                                                                    <Form-item label="url" prop="url">
                                    <Input v-model="sysAuthority.url" placeholder="请填写sysAuthority.url" style="width: 300px"/>
                                </Form-item>
                            
                                                                                    <Form-item label="method" prop="method">
                                    <Input v-model="sysAuthority.method" placeholder="请填写sysAuthority.method" style="width: 300px"/>
                                </Form-item>
                            
                                                                                    <Form-item label="resource_name" prop="resourceName">
                                    <Input v-model="sysAuthority.resourceName" placeholder="请填写sysAuthority.resourceName" style="width: 300px"/>
                                </Form-item>
                            
                                                                                    <Form-item label="resource_type" prop="resourceType">
                                    <Input v-model="sysAuthority.resourceType" placeholder="请填写sysAuthority.resourceType" style="width: 300px"/>
                                </Form-item>
                            
                                                    
                                                    
                                                    
                                            </Form>
                </Row>
                <Row slot="footer">
                    <Button type="text" @click="hide_add_sys_authority_modal">取消</Button>
                    <Button type="primary" @click="add_sys_authority">确定</Button>
                </Row>
            </Modal>
            <Modal
                    v-model="update_sys_authority_modal"
                    title="更新"
                    @on-ok="update_sys_authority"
                    @on-cancel="hide_update_sys_authority_modal">
                <Row type="flex" justify="center">
                    <Form :model="sysAuthority" :rules="sysAuthorityRules" ref="update_sys_authority"
                          :inline="false" :label-width="80"
                    >
                                                                                                                                        <Form-item label="auth_code" prop="authCode">
                                    <Input v-model="sysAuthority.authCode" placeholder="请填写sysAuthority.authCode" style="width: 300px"/>
                                </Form-item>
                                                                                                                <Form-item label="auth_name" prop="authName">
                                    <Input v-model="sysAuthority.authName" placeholder="请填写sysAuthority.authName" style="width: 300px"/>
                                </Form-item>
                                                                                                                <Form-item label="url" prop="url">
                                    <Input v-model="sysAuthority.url" placeholder="请填写sysAuthority.url" style="width: 300px"/>
                                </Form-item>
                                                                                                                <Form-item label="method" prop="method">
                                    <Input v-model="sysAuthority.method" placeholder="请填写sysAuthority.method" style="width: 300px"/>
                                </Form-item>
                                                                                                                <Form-item label="resource_name" prop="resourceName">
                                    <Input v-model="sysAuthority.resourceName" placeholder="请填写sysAuthority.resourceName" style="width: 300px"/>
                                </Form-item>
                                                                                                                <Form-item label="resource_type" prop="resourceType">
                                    <Input v-model="sysAuthority.resourceType" placeholder="请填写sysAuthority.resourceType" style="width: 300px"/>
                                </Form-item>
                                                                                                                                                                                                                                    </Form>
                </Row>
                <Row slot="footer">
                    <Button type="text" @click="hide_update_sys_authority_modal">取消</Button>
                    <Button type="primary" @click="update_sys_authority">确定</Button>
                </Row>
            </Modal>
        </Card>
</template>

<script>
    import {
        add_my_sys_authority, update_my_sys_authority, delete_my_sys_authority, get_my_sys_authority_list
    } from '../../api/SysAuthorityApi';

    export default {
        name:'SysAuthority',
        computed: {
            role: function () {
                return this.$store.state.user.userInfo.role;
            }
        },
        data(){
            return{
                sysAuthority:{
                                                                        id:null,
                        
                                                                        authCode:null,
                        
                                                                        authName:null,
                        
                                                                        url:null,
                        
                                                                        method:null,
                        
                                                                        resourceName:null,
                        
                                                                        resourceType:null,
                        
                                            
                                            
                                            
                                    },
                sysAuthorityRules:{},
                add_sys_authority_modal:false,

                update_sys_authority_modal:false,
                sysAuthorityList:[],
                sysAuthorityCols:[
                                                                        {
                                title: 'id',
                                key: 'id',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'authCode',
                                key: 'authCode',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'authName',
                                key: 'authName',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'url',
                                key: 'url',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'method',
                                key: 'method',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'resourceName',
                                key: 'resourceName',
                                //width: 100
                            },
                        
                                                                        {
                                title: 'resourceType',
                                key: 'resourceType',
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
                                       this.show_update_sys_authority_modal(params.index);
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
                                       this.delete_sys_authority(params.index);
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
            update_sys_authority(){
                update_my_sys_authority(this.sysAuthority).then(res => {
                        this.hide_update_sys_authority_modal();
                        this.get_sys_authority_list();
               }).catch(error => {
                   console.log(error);
               });
            },
            add_sys_authority(){
                add_my_sys_authority(this.sysAuthority).then(res => {
                    this.get_sys_authority_list();
                    this.hide_add_sys_authority_modal();
                }).catch(error => {
                    console.log(error);
                });
            },
            hide_add_sys_authority_modal(){
                this.$refs.sysAuthority.resetFields();
                this.add_sys_authority_modal = false
            },
            hide_update_sys_authority_modal(){
                this.$refs.update_sys_authority.resetFields
                ();
                this.update_sys_authority_modal = false;
            },
            delete_sys_authority(index){
                delete_my_sys_authority(ids).then(res => {
                      this.get_sys_authority_list();
                }).catch(error => {
                    console.log(error);
                });
            },
            show_update_sys_authority_modal(index){
                this.sysAuthority = JSON.parse(JSON.stringify(this.sysAuthorityList[index]));
                this.update_sys_authority_modal = true
            },
            show_add_sys_authority_modal(){
                this.add_sys_authority_modal = true
            },
            nextPage(page){
                this.params.page = page;
                this.get_sys_authority_list();
            },
            get_sys_authority_list(){
                get_my_sys_authority_list(this.params).then(res => {
                  this.sysAuthorityList = res.list;
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
            this.get_sys_authority_list()
        }
    }
</script>
<style scoped>

</style>
import {otherRouter, appRouter} from '@/router/router';
import Util from '../../libs/util';
import Main from '@/views/Main.vue';

const app = {
    state: {
        menuList: [],
        /*routers: [
            otherRouter,
            ...appRouter
        ]*/
    },
    getters: {
        menuList: state => state.menuList
    },
    mutations: {
        updateMenulist (state,role) {
            let app_r = [
                {
                    path: '/sys',
                    icon: 'ios-folder',
                    name: 'sys',
                    title: '系统设置',
                    access: [1],
                    component: Main,
                    children: [
                        {
                            path: 'auth',
                            icon: 'ios-paper-outline',
                            name: 'auth',
                            title: '权限控制',
                            // access: [0],
                            component: resolve => { require(['@/views/admin-pages/permission-assign.vue'], resolve); }
                        },
                        {
                            path: 'user',
                            icon: 'ios-paper-outline',
                            name: 'user',
                            title: '用户管理',
                            // access: [0],
                            component: resolve => { require(['@/views/admin-pages/SysUserAdmin.vue'], resolve); }
                        }
                    ]
                }
            ];
            let filteredMenus = Util.filterMenus(role,app_r);
            state.menuList = filteredMenus;
            // state.menuList = appRouter;
        }
    }
};

export default app;

import Vue from 'vue';
import iView from 'iview';
import Util from '../libs/util';
import VueRouter from 'vue-router';
import {appRouter, otherRouter, routers} from './router';
import store from '../store/index';
import {logout} from '../store/cache';

Vue.use(VueRouter);
// 路由配置
const RouterConfig = {
    // mode: 'history',
    routes: routers
};

export const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    Util.title(to.meta.title);
    //logout()
    if (!store.state.user.token && to.name !== 'login') {
        next({
            name: 'login'
        });
    } else if (store.state.user.token && to.name === 'login') { // 判断是否已经登录且前往的是登录页
        Util.title();
        next({
            name: 'home_index'
        });
    } else {
        //获取当前登录用户的角色
        // let role = 1;
        let role = null
        let userInfo = store.state.user.userInfo
        if (userInfo !== undefined && userInfo !== null) {
            role = userInfo.role
        }
        const curRouterObj = Util.getRouterObjByName([otherRouter, ...appRouter], to.name);
        if (curRouterObj && curRouterObj.access !== undefined) { // 需要判断权限的路由
            if (typeof curRouterObj.access === 'number' && curRouterObj.access === role) {
                next();
                // Util.toDefaultPage([otherRouter, ...appRouter], to.name, router, next); // 如果在地址栏输入的是一级菜单则默认打开其第一个二级菜单的页面
            } else if (Array.isArray(curRouterObj.access) && Util.oneOf(role, curRouterObj.access)) {
                next();
                // Util.toDefaultPage([otherRouter, ...appRouter], to.name, router, next); // 如果在地址栏输入的是一级菜单则默认打开其第一个二级菜单的页面
            } else {
                next({
                    replace: true,
                    name: 'error-403'
                });
            }
        } else { // 没有配置权限的路由, 直接通过
            //Util.toDefaultPage([...routers], to.name, router, next);
            next();
        }
        //next();
    }

});

router.afterEach((to) => {
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});

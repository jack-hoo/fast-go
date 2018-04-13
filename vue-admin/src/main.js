import Vue from 'vue';
import iView from 'iview';
import {router} from './router/index';
import store from './store';
import App from './app.vue';
import 'iview/dist/styles/iview.css';

Vue.use(iView);

new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App),
    mounted () {
        if(this.$store.state.app.menuList.length === 0) {
            let userInfo = this.$store.state.user.userInfo
            if (userInfo !== undefined && userInfo !== null) {
                this.$store.commit('updateMenulist',userInfo.role);
            }
        }
    }
});

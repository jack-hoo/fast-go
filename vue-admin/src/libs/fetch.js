import axios from 'axios';
import {Message} from 'iview';
import store from '../store/index';
import {logout} from '../store/cache';

let baseUrl = 'http://localhost:8082';
const service = axios.create({
    baseURL: baseUrl, // api的base_url
    timeout: 10000    // 请求超时时间
});
service.interceptors.request.use(config => {
    if (store.state.user.token) {
        config.headers['Authorization'] = store.state.user.token; // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config;
}, error => {
    // Do something with request error
    Promise.reject(error);
});
// respone拦截器
service.interceptors.response.use(
    response => {
        //code为非0是抛错 可结合自己业务进行修改
        const res = response.data;

        if (res.status !== 0) {
            //其他错误码信息提示
            if (res.status === 400003) {
                Message.error({
                    content: '用户名或密码不正确',
                    duration: 4,
                    closable: true
                });
            }
            else if (res.status === 400002) {
                //token过期重新跳转登录页面
                Message.error({
                    content: '登录失效，请重新登录',
                    duration: 4,
                    closable: true
                });
                //清除cookie
                logout();
                location.reload();
            }
            else {
                Message.error({
                    content: res.msg,
                    duration: 4,
                    closable: true
                });
            }
            //正式环境需要屏蔽错误消息
            return Promise.reject(res.msg);
        } else {
            return res.data;
        }
    },
    error => {
        console.log('err' + error);// for debug
        Message.error({
            content: '服务暂时无法访问，请稍后再试',
            type: 'error',
            duration: 4
        });
        return Promise.reject(error);
    }
)
;

export default service;

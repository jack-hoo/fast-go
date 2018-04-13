import {getToken, setToken, getUserInfo, setUserInfo, removeCache} from '../cache';
import {login} from '../../api/login'
const user = {
    state: {
        userInfo:getUserInfo(),
        token:getToken()
    },
    getters: {
        userInfo: state => state.userInfo,
        token: state => state.token
    },
    mutations: {
        SET_USERINFO(state, userInfo) {
            state.userInfo = userInfo
        },
        SET_TOKEN(state, token) {
            state.token = token
        },
        REMOVEUSERINFO(state) {
            removeCache()
            state.userInfo = {}
        }
    },
    actions: {
        Login({commit},userInfo) {
            return new Promise((resolve, reject) =>{
                login(userInfo).then(res =>{
                    setToken(res.token)
                    setUserInfo(res.userInfo)
                    commit('SET_USERINFO',res.userInfo)
                    commit('SET_TOKEN',res.token)
                    resolve()
                }).catch(error => {
                    //reject(error)
                })
            });
        },
        Logout({commit}){
            return new Promise((resolve,reject) =>{
                commit('REMOVEUSERINFO')
                resolve()
            }).catch(error => {
                reject(error)
            })

        }
    }

};

export default user;

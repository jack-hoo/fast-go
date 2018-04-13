import fetch from '../libs/fetch'


export function login(accountInfo) {
    return fetch({
        url: '/auth/login',
        method: 'post',
        data: accountInfo
    })
}

export function register(accountInfo) {
    return fetch.post({
        url: '/auth/register',
        data: accountInfo
    })

}

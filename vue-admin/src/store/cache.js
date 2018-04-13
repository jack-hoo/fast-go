import Cookies from 'js-cookie';

const TokenKey = 'PC-Token';
const TokenPrefix = 'Bearer ';

export function setToken (token) {
    return Cookies.set(TokenKey, TokenPrefix + token);
}

export function getToken () {
    return Cookies.get(TokenKey);
}

export function removeCache () {
    Cookies.remove(TokenKey);
    Cookies.remove('userInfo');
}

export function setUserInfo (userInfo) {
    return Cookies.set('userInfo', JSON.stringify(userInfo));
}

export function getUserInfo () {
    let userInfo = Cookies.get('userInfo');
    if (userInfo === undefined || userInfo === '') {
        return null;
    }
    return JSON.parse(userInfo);
}

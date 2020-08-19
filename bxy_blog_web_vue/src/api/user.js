import request from '@/utils/request'

/**
 * 第三方登录
 * @param params
 */
export function oauthLogin(params) {
  return request({
    url: '/oauth/render',
    method: 'post',
    params
  })
}

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/auth/info',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function updateInfo(data) {
  return request({
    url: '/user/updateInfo',
    method: 'post',
    data
  })
}
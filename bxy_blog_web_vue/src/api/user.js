import request from '@/utils/request'

/**
 * 第三方登录
 * @param params
 */
export function login(params) {
  return request({
    url: '/oauth/render',
    method: 'post',
    params
  })
}
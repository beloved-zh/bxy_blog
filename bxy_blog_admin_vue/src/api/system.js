import request from '@/utils/request'

export function getQiniuConfig() {
  return request({
    url: `/system/getQiniuConfig`,
    method: 'get'
  })
}

export function getOauthConfig() {
  return request({
    url: `/system/getOauthConfig`,
    method: 'get'
  })
}

export function getJwtConfig() {
  return request({
    url: `/system/getJwtConfig`,
    method: 'get'
  })
}

export function updateJwtConfig(data) {
  return request({
    url: `/system/updateJwtConfig`,
    method: 'put',
    data
  })
}

export function updateQiniuConfig(data) {
  return request({
    url: `/system/updateQiniuConfig`,
    method: 'put',
    data
  })
}

export function updateOauthConfig(data) {
  return request({
    url: `/system/updateOauthConfig`,
    method: 'put',
    data
  })
}

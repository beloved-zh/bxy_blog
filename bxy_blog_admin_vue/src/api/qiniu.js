import request from '@/utils/request'

export function uploade(data) {
  return request({
    url: '/qiniu/uploade',
    method: 'post',
    data
  })
}

export function uploadImage(data) {
  return request({
    url: '/qiniu/uploadImage',
    method: 'post',
    data
  })
}

export function getBuckets() {
  return request({
    url: `/qiniu/getBuckets`,
    method: 'get'
  })
}

export function getList(params) {
  return request({
    url: `/qiniu/getList`,
    method: 'get',
    params
  })
}

export function getBaseURL(params) {
  return request({
    url: `/qiniu/getBaseURL`,
    method: 'get',
    params
  })
}

export function deleteFile(data) {
  return request({
    url: `/qiniu/delete`,
    method: 'delete',
    data
  })
}

export function deleteList(data) {
  return request({
    url: `/qiniu/deleteList`,
    method: 'delete',
    data
  })
}

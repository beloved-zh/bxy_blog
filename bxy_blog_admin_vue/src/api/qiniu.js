import request from '@/utils/request'

export function uploaderImage(data) {
  return request({
    url: '/qiniu/uploaderImage', // 假地址 自行替换
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

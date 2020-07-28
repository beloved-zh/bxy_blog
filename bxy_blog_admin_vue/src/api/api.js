import request from '@/utils/request'

export function getApi(params) {
  return request({
    url: `/api/getApi`,
    method: 'get',
    params
  })
}

export function getFatherApi() {
  return request({
    url: `/api/getFatherApi`,
    method: 'get'
  })
}

export function addApi(data) {
  return request({
    url: `/api/addApi`,
    method: 'post',
    data
  })
}

export function updateApi(data) {
  return request({
    url: '/api/updateApi',
    method: 'put',
    data
  })
}

export function deleteApi(id) {
  return request({
    url: '/api/deleteApi',
    method: 'delete',
    data: id
  })
}

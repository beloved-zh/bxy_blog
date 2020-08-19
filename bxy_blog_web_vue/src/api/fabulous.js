import request from '@/utils/request'

export function addFabulous(data) {
  return request({
    url: `/fabulous/addFabulous`,
    method: 'post',
    data
  })
}

export function judgeClickZan(params) {
  return request({
    url: `/fabulous/judgeClickZan`,
    method: 'get',
    params
  })
}

export function getFabulousByUser(params) {
  return request({
    url: `/fabulous/getFabulousByUser`,
    method: 'get',
    params
  })
}

export function deleteFabulous(data) {
  return request({
    url: '/fabulous/deleteFabulous',
    method: 'delete',
    data
  })
}
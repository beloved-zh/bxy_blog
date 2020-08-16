import request from '@/utils/request'

export function addDiscussFather(data) {
  return request({
    url: `/discuss/addDiscussFather`,
    method: 'post',
    data
  })
}

export function addDiscussSon(data) {
  return request({
    url: `/discuss/addDiscussSon`,
    method: 'post',
    data
  })
}

export function getDiscuss(params) {
  return request({
    url: `/discuss/getDiscuss`,
    method: 'get',
    params
  })
}
import request from '@/utils/request'

export function getOnline(params) {
  return request({
    url: `/online/getOnline`,
    method: 'get',
    params
  })
}

export function deleteOnline(params) {
  return request({
    url: `/online/deleteOnline`,
    method: 'delete',
    params
  })
}


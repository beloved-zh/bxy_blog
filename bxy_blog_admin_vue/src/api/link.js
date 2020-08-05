import request from '@/utils/request'

export function getLinks(params) {
  return request({
    url: `/links/getLinks`,
    method: 'get',
    params
  })
}

export function addLink(data) {
  return request({
    url: '/links/addLink',
    method: 'post',
    data
  })
}

export function deleteLink(id) {
  return request({
    url: `/links/deleteLink`,
    method: 'delete',
    data: id
  })
}

export function updateLink(data) {
  return request({
    url: `/links/updateLink`,
    method: 'put',
    data
  })
}
